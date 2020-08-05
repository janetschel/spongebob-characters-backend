package com.janetschel.backend.service;

import com.janetschel.backend.service.datafetcher.AllCharactersDataFetcher;
import com.janetschel.backend.service.datafetcher.CharacterDataFetcher;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class CharactersService {

    @Value("classpath:characters.graphql")
    private Resource resource;
    private GraphQL graphQL;

    private final AllCharactersDataFetcher allCharactersDataFetcher;
    private final CharacterDataFetcher characterDataFetcher;

    public CharactersService(AllCharactersDataFetcher allCharactersDataFetcher, CharacterDataFetcher characterDataFetcher) {
        this.allCharactersDataFetcher = allCharactersDataFetcher;
        this.characterDataFetcher = characterDataFetcher;
    }

    @PostConstruct
    private void loadGraphQLSchema() throws IOException {
        File schemaFile = resource.getFile();

        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                        typeWiring
                                .dataFetcher("allCharacters", allCharactersDataFetcher)
                                .dataFetcher("character", characterDataFetcher))
                .build();
    }

    public ResponseEntity<Object>getData (String query) {
        Object executionResultData = graphQL.execute(query).getData();
        return new ResponseEntity<>(executionResultData, HttpStatus.OK);
    }
}
