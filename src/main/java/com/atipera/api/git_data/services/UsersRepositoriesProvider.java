package com.atipera.api.git_data.services;

import com.atipera.api.git_data.DTOs.BranchDTO;
import com.atipera.api.git_data.DTOs.RepositoryDTO;
import com.atipera.api.git_data.repositories.interfaces.DataSource;
import com.atipera.api.git_data.services.helper_classes.DataConverter;
import com.atipera.api.git_data.services.interfaces.UsersRepositoriesService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class UsersRepositoriesProvider implements UsersRepositoriesService {

    @Autowired
    private DataSource dataSource;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<RepositoryDTO> get(String userName) {
        List<RepositoryDTO> repositories;
        List<CompletableFuture<RepositoryDTO>> asyncTasksList = new ArrayList<>();

        String repositoriesData = dataSource.getRepositories(userName);
        JsonNode objectTree = DataConverter.convertToObjectTree(repositoriesData, mapper);
        repositories = createRepositories(objectTree);

        for (RepositoryDTO repo :
                repositories) {
            asyncTasksList.add(fillRepoWithBranchInfo(repo));
        }

        CompletableFuture.allOf(asyncTasksList.toArray(CompletableFuture[]::new)).join();

        return repositories;
    }

    private List<RepositoryDTO> createRepositories(JsonNode objectTree) {
        List<RepositoryDTO> repos = new ArrayList<>();
        String name, owner;

        for (JsonNode node : objectTree) {
            if ((node.get("fork").asText()).equals("false")) {
                name = node.get("name").asText();
                owner = node.get("owner").get("login").asText();
                repos.add(new RepositoryDTO(name, owner));
            }
        }
        return repos;
    }

    @Async
    public CompletableFuture<RepositoryDTO> fillRepoWithBranchInfo(RepositoryDTO repository) {
        String name, sha;
        String branchesData = dataSource.getBranches(repository.getOwner(), repository.getName());
        JsonNode objectTree = DataConverter.convertToObjectTree(branchesData, mapper);

        for (JsonNode node: objectTree
             ) {
            name = node.get("name").asText();
            sha = node.get("commit").get("sha").asText();
            repository.addBranch(new BranchDTO(name, sha));
        }
        return CompletableFuture.completedFuture(repository);
    }
}
