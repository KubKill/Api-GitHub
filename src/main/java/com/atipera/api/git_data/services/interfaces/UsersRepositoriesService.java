package com.atipera.api.git_data.services.interfaces;

import com.atipera.api.git_data.DTOs.RepositoryDTO;

import java.util.List;

public interface UsersRepositoriesService {

    List<RepositoryDTO> get(String userName);
}
