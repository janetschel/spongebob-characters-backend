package com.janetschel.backend.service.datafetcher;

import com.janetschel.backend.data.CharacterEntity;
import com.janetschel.backend.repository.CharactersRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CharacterDataFetcher implements DataFetcher<Optional<CharacterEntity>> {
    private final CharactersRepository charactersRepository;

    public CharacterDataFetcher(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }

    @Override
    public Optional<CharacterEntity> get(DataFetchingEnvironment dataFetchingEnvironment) {
        String id = dataFetchingEnvironment.getArgument("id");
        return charactersRepository.findById(id);
    }
}
