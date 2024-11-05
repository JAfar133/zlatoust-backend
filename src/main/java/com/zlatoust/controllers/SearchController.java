package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.services.LuceneService;
import com.zlatoust.services.dto.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.API_PATH)
public class SearchController {

    private final LuceneService luceneService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchResult>> search(@RequestParam String query) throws Exception {
        List<SearchResult> results = luceneService.search(query, 5);
        return ResponseEntity.ok(results);
    }
}
