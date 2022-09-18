package com.atipera.api.git_data.services;

import com.atipera.api.git_data.DTOs.BranchDTO;
import com.atipera.api.git_data.DTOs.RepositoryDTO;
import com.atipera.api.git_data.exceptions.InconsistentContentException;
import com.atipera.api.git_data.exceptions.UserNotFoundException;
import com.atipera.api.git_data.repositories.interfaces.DataSource;
import com.atipera.api.git_data.services.helper_classes.DataConverter;
import com.atipera.api.git_data.services.interfaces.UsersRepositoriesService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UsersRepositoriesProvider implements UsersRepositoriesService {

    @Autowired
    private DataSource dataSource;

    private static final Logger logger =
            LoggerFactory.getLogger(UsersRepositoriesProvider.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<RepositoryDTO> get(String userName) throws InconsistentContentException{
        logger.info("Looking up " + userName + " repositories.");

        List<RepositoryDTO> repositories;
        List<CompletableFuture<RepositoryDTO>> asyncTasksList = new ArrayList<>();

        String repositoriesData = dataSource.getRepositories(userName);
        if (repositoriesData == null) {
            logger.warn("User not found.");
            throw new UserNotFoundException();
        }

        JsonNode objectTree = DataConverter.convertToObjectTree(repositoriesData, mapper);
        repositories = createRepositories(objectTree);

        try {
            for (RepositoryDTO repo :
                    repositories) {
                asyncTasksList.add(fillRepoWithBranchInfo(repo));
            }
        } catch (InconsistentContentException e) {
            throw  e;
        }

        CompletableFuture.allOf(asyncTasksList.toArray(CompletableFuture[]::new)).join();
        logger.info("Gathering data successful.");

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
    public CompletableFuture<RepositoryDTO> fillRepoWithBranchInfo(RepositoryDTO repository) throws InconsistentContentException {
        String name, sha;
        String branchesData = dataSource.getBranches(repository.getOwner(), repository.getName());
        JsonNode objectTree = DataConverter.convertToObjectTree(branchesData, mapper);

        if(objectTree == null) {
            logger.error("Data inconsistency. No branch info on repository " + repository.getName());
            return CompletableFuture.failedFuture(new InconsistentContentException());
        }

        for (JsonNode node: objectTree
             ) {
            name = node.get("name").asText();
            sha = node.get("commit").get("sha").asText();
            repository.addBranch(new BranchDTO(name, sha));
        }
        return CompletableFuture.completedFuture(repository);
    }
}
