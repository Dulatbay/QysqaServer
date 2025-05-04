package com.example.qysqaserver.repositories;

import com.example.qysqaserver.entities.LearnModule;
import com.example.qysqaserver.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ModuleRepository extends MongoRepository<LearnModule, String> {

    @Override
    long count();

    List<LearnModule> findAllByUser(User user);

    @Query("{ 'linkedUsers': { $in: ?0 } }")
    List<LearnModule> findAllByLinkedUsersIn(List<String> linkedUserIds);

}
