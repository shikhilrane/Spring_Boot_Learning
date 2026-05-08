package com.shikhilrane.testing.TestingApplication.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){    // Creates and registers CacheManager bean in Spring container.

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith("my-redis-")                                       // Adds prefix before every cache name stored in Redis.
                .entryTtl(Duration.ofSeconds(60))                                       // Sets cache expiration time to 60 seconds.
                .enableTimeToIdle()                                                     // Resets TTL whenever the cache entry is accessed.
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Stores Redis keys in readable String format.
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())); // Stores Redis values in JSON format for better readability.

        return RedisCacheManager.builder(redisConnectionFactory)                        // Creates RedisCacheManager using Redis connection.
                .cacheDefaults(redisCacheConfiguration)                                 // Applies default Redis cache configuration.
                .build();                                                               // Builds and returns the CacheManager object.
    }
}

/*
    This configuration class customizes Redis caching behavior in the application.

    It creates a RedisCacheManager bean for managing cache operations using Redis,
    adds a custom cache-name prefix, sets cache expiration time (TTL),
    enables Time-To-Idle support, and configures readable serialization
    for Redis keys and values.
*/