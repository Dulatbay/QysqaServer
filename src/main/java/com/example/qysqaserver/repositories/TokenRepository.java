package com.example.qysqaserver.repositories;

import com.example.qysqaserver.entities.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {
    @Query("{ 'user.$id': ObjectId(?0), $or: [ { 'expired': false }, { 'revoked': false } ] }")
    List<Token> findAllValidTokenByUser(String userId);

    Optional<Token> findByToken(String token);

//    Optional<Token> findByAc
}
