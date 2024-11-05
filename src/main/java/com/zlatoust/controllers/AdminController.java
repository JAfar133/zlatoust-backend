package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.Content;
import com.zlatoust.services.LuceneService;
import com.zlatoust.services.UserService;
import com.zlatoust.services.dto.LuceneDocument;
import com.zlatoust.services.dto.SearchResult;
import com.zlatoust.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_PATH_ADMIN)
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;
    private final LuceneService luceneService;

    @GetMapping("/get-users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/all-indexed")
    public ResponseEntity<List<LuceneDocument>> getAllIndexedDocuments() {
        try {
            List<LuceneDocument> results = luceneService.getAllIndexedDocuments();
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/index-document")
    public ResponseEntity<String> indexDocument(@RequestBody LuceneDocument document) {
        try {
            luceneService.indexLuceneDocument(document);
            return ResponseEntity.ok("Document indexed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to index document");
        }
    }

    @PostMapping("/delete-document/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        try {
            luceneService.deleteDocumentById(id);
            return ResponseEntity.ok("Document deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete document");
        }
    }

    @PostMapping("/update-document")
    public ResponseEntity<String> updateDocument(@RequestBody LuceneDocument document) {
        try {
            luceneService.indexLuceneDocument(document);
            return ResponseEntity.ok("Document updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update document");
        }
    }


}
