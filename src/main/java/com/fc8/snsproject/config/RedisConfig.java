package com.fc8.snsproject.config;

import com.fc8.snsproject.domain.user.dto.UserDto;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@EnableRedisRepositories
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, UserDto> userDtoRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UserDto> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<UserDto>(UserDto.class));

        return redisTemplate;
    }


    // TODO : 아래 코드 대로 진행 하는 방법에 대해 학습 필요
//    private final RedisProperties redisProperties;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
//        RedisConfiguration redisConfiguration = LettuceConnectionFactory.createRedisConfiguration(redisURI);
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration);
//        lettuceConnectionFactory.afterPropertiesSet();
//
//        return lettuceConnectionFactory;
//    }
}
