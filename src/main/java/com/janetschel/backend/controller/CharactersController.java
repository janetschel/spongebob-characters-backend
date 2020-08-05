package com.janetschel.backend.controller;

import com.janetschel.backend.service.CharactersService;
import com.janetschel.backend.util.InputParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characters")
public class CharactersController {

    private final CharactersService charactersService;
    private final InputParser inputParser;

    public CharactersController(CharactersService charactersService, InputParser inputParser) {
        this.charactersService = charactersService;
        this.inputParser = inputParser;
    }

    @PostMapping
    public ResponseEntity<Object> getCharacters(@RequestBody String query) {
        query = inputParser.parseGraphQLQuery(query);
        return charactersService.getData(query);
    }
}
