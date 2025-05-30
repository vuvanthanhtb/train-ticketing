package com.train_ticketing.infrastructure.cache.redis;

public interface RedisInfrastructureService {
    void setString(String key, String value);
    String getString(String key);
    void setObject(String key, Object value);
    <T> T getObject(String key, Class<T> targetClass);
    void delete(String key);
}
