package ru.edu.filmportal.parser.services.webdriver;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.DisposableBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.function.Supplier;

public class WebDriverPool implements DisposableBean {
    private final BlockingQueue<WebDriver> pool;
    private final Supplier<WebDriver> driverFactory;
    private final List<WebDriver> allDrivers;

    public WebDriverPool(int size, Supplier<WebDriver> driverFactory) {
        this.pool = new LinkedBlockingQueue<>(size);
        this.driverFactory = driverFactory;
        this.allDrivers = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            WebDriver driver = driverFactory.get();
            pool.add(driver);
            allDrivers.add(driver);
        }
    }

    public <T> T executeWithDriver(Function<WebDriver, T> action) {
        WebDriver driver = null;
        try {
            driver = pool.take();
            return action.apply(driver);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for driver", e);
        } finally {
            if (driver != null) {
                restartDriver(driver);
            }
        }
    }

    private void restartDriver(WebDriver driver) {
        driver.quit();

        try {
            WebDriver newDriver = driverFactory.get();
            pool.put(newDriver);
            allDrivers.set(allDrivers.indexOf(driver), newDriver);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void destroy() {
        allDrivers.forEach(WebDriver::quit);
    }
}