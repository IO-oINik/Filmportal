package ru.edu.filmportal.parser.configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.edu.filmportal.parser.services.webdriver.WebDriverPool;

@Configuration
public class SeleniumPoolConfig {
    @Value("${selenium.pool.size:3}")
    private int poolSize;
    @Value("${selenium.pool.path}")
    private String path;

    @Bean(destroyMethod = "destroy")
    public WebDriverPool webDriverPool() {
        return new WebDriverPool(poolSize, this::createDriver);
    }

    private WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=new",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );
        System.setProperty("webdriver.chrome.driver", path);
        return new ChromeDriver(options);
    }
}
