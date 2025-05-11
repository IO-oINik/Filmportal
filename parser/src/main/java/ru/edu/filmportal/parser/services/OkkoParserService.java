package ru.edu.filmportal.parser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.parser.models.OkkoInfoFilm;
import ru.edu.filmportal.parser.models.ResultParsing;
import ru.edu.filmportal.parser.services.webdriver.WebDriverPool;
import ru.edu.filmportal.parser.exceptions.ParseException;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OkkoParserService {
    private final String rootUrlOkkoMovie = "https://okko.tv/movie";
    private final ObjectMapper mapper;
    private final WebDriverPool webDriverPool;

    public OkkoParserService(ObjectMapper mapper, WebDriverPool webDriverPool) {
        this.mapper = mapper;
        this.webDriverPool = webDriverPool;
    }

    public List<ResultParsing> parseInfoFilms(String url, int maxCountFilm, int maxCountRequest) {
        if (maxCountFilm > 10) {
            throw new IllegalArgumentException("maxCount must be less than 10");
        }
        if (maxCountRequest > 30) {
            throw new IllegalArgumentException("maxCountRequest must be less than 20");
        }
        if (!url.startsWith(rootUrlOkkoMovie)) {
            throw new IllegalArgumentException("url must be start with: " + rootUrlOkkoMovie);
        }

        Deque<String> urlDeque = new ArrayDeque<>();
        Set<String> urlUnique = new HashSet<>();
        List<PageSourseAndUrlsMovie> pageSources = new ArrayList<>();
        urlDeque.add(url);

        int count = 0;
        int countRequest = 0;
        while (!urlDeque.isEmpty() && count < maxCountFilm && countRequest < maxCountRequest) {
            String newUrl = urlDeque.removeFirst();
            if (!urlUnique.contains(newUrl)) {
                urlUnique.add(newUrl);

                PageSourseAndUrlsMovie pageSourseAndUrlsMovie = getPageSourseAndUrls(newUrl);
                pageSources.add(pageSourseAndUrlsMovie);
                urlDeque.addAll(pageSourseAndUrlsMovie.urls);
                count++;
            }
            countRequest++;
        }

        List<ResultParsing> result = new ArrayList<>();

        for(PageSourseAndUrlsMovie info : pageSources) {
            ResultParsing resultParsing = new ResultParsing();
            resultParsing.setSuccess(info.success);
            resultParsing.setMessageError(info.messageError);
            resultParsing.setUrl(info.mainUrl);
            if (info.success) {
                try {
                    resultParsing.setInfoFilm(parsePage(info.pageSource));
                } catch (RuntimeException e) {
                    resultParsing.setMessageError(e.getMessage());
                    resultParsing.setSuccess(false);
                }
            }
            result.add(resultParsing);
        }

        return result;
    }

    public OkkoInfoFilm parsePage(String pageSource) {
            try {
                Document doc = Jsoup.parse(pageSource);
                List<Element> elements = doc.select("script[type='application/ld+json']");

                String json = elements.get(1).html();

                OkkoInfoFilm info = mapper.readValue(json, OkkoInfoFilm.class);

                Map<String, Object> map = getMapMetaInfo(elements.get(0));

                info.setAgeLimit(getAgeLimit(map));
                info.setGenres(getGenres(map));

                System.out.println(info);
                return info;
            } catch (IOException e) {
                throw new ParseException(e.getMessage());
            }
    }

    private PageSourseAndUrlsMovie getPageSourseAndUrls(String url) {
        return webDriverPool.executeWithDriver(driver -> {
            PageSourseAndUrlsMovie result = new PageSourseAndUrlsMovie();
            result.success = true;
            result.mainUrl = url;
            try {
                driver.get(url);
                result.pageSource = driver.getPageSource();

                List<WebElement> elements = driver.findElements(By.cssSelector("a[test-id='search_collection_element'"));
                for (WebElement element : elements) {
                    String newUrl = element.getAttribute("href");
                    if (newUrl != null && newUrl.startsWith(rootUrlOkkoMovie)) {
                        result.urls.add(newUrl);
                    }
                }
            } catch (RuntimeException e) {
                result.messageError = e.getMessage();
                result.success = false;
            }
            return result;
        });
    }

    private Map<String, Object> getMapMetaInfo(Element element) throws JsonProcessingException {
        return mapper.readValue(element.html(), new TypeReference<Map<String, Object>>() {});
    }

    private Integer getAgeLimit(Map<String, Object> map) {
        try {
            String str = (String) map.getOrDefault("contentRating", null);
            return Integer.parseInt(str.replace("+", ""));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    private List<String> getGenres(Map<String, Object> map) {
        return Arrays.stream(map.getOrDefault("genre", "").toString().split(", ")).toList();
    }

    static class PageSourseAndUrlsMovie {
        public PageSourseAndUrlsMovie() {
            urls = new ArrayList<>();
        }

        public List<String> urls;
        public String mainUrl;
        public String pageSource;
        public String messageError;
        public boolean success;
    }
}
