package com.janetschel.backend.service.datafetcher;

import com.janetschel.backend.data.CharacterEntity;
import com.janetschel.backend.repository.CharactersRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllCharactersDataFetcher implements DataFetcher<List<CharacterEntity>> {
    private final CharactersRepository charactersRepository;

    public AllCharactersDataFetcher(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }

    @Override
    public List<CharacterEntity> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return charactersRepository.findAll();
    }
}
