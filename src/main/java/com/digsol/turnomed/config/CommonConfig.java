package com.digsol.turnomed.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import net.dongliu.gson.GsonJava8TypeAdapterFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;

@Configuration
public class CommonConfig {

    @Value("${redis.url}")
    String url;

    @Bean
    RedisURI redisURI() {
        return RedisURI.create(url);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisURI uri = redisURI();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(uri.getHost(), uri.getPort());
        factory.setDatabase(1);
        return factory;
    }

    @Bean
    public RedisClient redisClient() {
        return RedisClient.create(redisURI());
    }

    @Bean
    public Gson gson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new GsonJava8TypeAdapterFactory());
        JsonSerializer<Timestamp> timestampJsonSerializer = (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime());
        JsonSerializer<Json> springFoxSerializer = (src, typeOfSrc, context) -> new JsonParser().parse(src.value());
        JsonDeserializer<Timestamp> timestampJsonDeserializer = (json, typeOfT, context) -> new Timestamp(json.getAsNumber().longValue());
        builder.registerTypeAdapter(Timestamp.class, timestampJsonSerializer);
        builder.registerTypeAdapter(Timestamp.class, timestampJsonDeserializer);
        builder.registerTypeAdapter(Json.class, springFoxSerializer);
        builder.setDateFormat("yyyy-MM-dd");
        return builder.create();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*").allowCredentials(true);
            }
        };
    }
}
