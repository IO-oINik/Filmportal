package ru.edu.filmportal.parser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.parser.models.ResultParsing;

import java.util.List;

@Service
public class KafkaConsumerUrlsService {
    Logger log = LoggerFactory.getLogger(KafkaConsumerUrlsService.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OkkoParserService parserService;
    private final ObjectMapper objectMapper;

    public KafkaConsumerUrlsService(OkkoParserService parserService, KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.parserService = parserService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "okko-parse-url")
    public void listen(String url) {
        log.info("Получено сообщение из топика okko-parse-url: {}", url);

        List<ResultParsing> list = parserService.parseInfoFilms(url, 1, 10);

        for (ResultParsing info : list) {
            try {
                kafkaTemplate.send("info-film", objectMapper.writeValueAsString(info));
            } catch (JsonProcessingException e) {
                log.error("Error object to json {}", e.getMessage(), e);
            }
        }
    }
}
