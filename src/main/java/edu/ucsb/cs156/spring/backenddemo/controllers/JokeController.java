package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.ApiParam;

@Tag(name="Joke info from ")
@Slf4j
@RestController
@RequestMapping("/api/jokes")
public class JokeController {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    JokeQueryService jokeQueryService;

    @Operation(summary = "Get number of jokes given a category", description = "JSON return format documented here: link")
    @GetMapping("/get")
    public ResponseEntity<String> newJokes(
        @Parameter(name="category", description="Joke category", example="Programming") @RequestParam String category,
        @Parameter(name="numJokes", description="Number of jokes", example="5") @RequestParam int numJokes
    ) throws JsonProcessingException {
        log.info("getJokes: category={} numJokes={}", category, numJokes);
        String result = jokeQueryService.getJSON(category, numJokes);
        return ResponseEntity.ok().body(result);
    }

}
