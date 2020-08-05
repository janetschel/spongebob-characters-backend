package com.janetschel.backend.repository;

import com.janetschel.backend.data.CharacterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharactersRepository extends MongoRepository<CharacterEntity, String> {
}
