package com.atipera.api.git_data.services.interfaces;

import com.atipera.api.git_data.DTOs.RepositoryDTO;
import com.atipera.api.git_data.exceptions.InconsistentContentException;
import com.atipera.api.git_data.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersRepositoriesService {

    List<RepositoryDTO> get(String userName) throws InconsistentContentException, UserNotFoundException;
}
