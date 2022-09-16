package com.atipera.api.git_data.controllers;

import com.atipera.api.git_data.DTOs.BranchDTO;
import com.atipera.api.git_data.services.interfaces.UsersRepositoriesService;
import com.atipera.api.git_data.DTOs.RepositoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/git-data")
public class GitDataController {

    @Autowired
    private UsersRepositoriesService service;

    @Validated
    @GetMapping(value = "/{user}", headers = "Accept=application/json")
    public ResponseEntity<List<RepositoryDTO>> read(@PathVariable String user) {
        List<RepositoryDTO> repos = service.get(user);
        if (repos == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok(repos);
        }
    }


}
