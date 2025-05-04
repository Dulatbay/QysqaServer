package com.example.qysqaserver.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MapperBean {
    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        var javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class,
                new JsonSerializer<>() {
                    @Override
                    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        gen.writeString(value.format(formatter));
                    }
                });
        javaTimeModule.addSerializer(LocalDate.class,
                new JsonSerializer<>() {
                    @Override
                    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        gen.writeString(value.format(formatter));
                    }
                });

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
