package com.example.qysqaserver.repositories;

import com.example.qysqaserver.entities.Topic;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.Set;


public interface TopicRepository extends MongoRepository<Topic, String> {
    Set<Topic> findByIdIn(Set<String> ids);

    @Query("{ 'number': ?0, 'kzhModule': ?1 }")
    Optional<Topic> findByNumberAndKzhModuleId(int topicNumber, String moduleId);
}
