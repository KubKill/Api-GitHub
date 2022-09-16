package com.atipera.api.git_data.repositories;

import com.atipera.api.git_data.repositories.helper_classes.PathModifier;
import com.atipera.api.git_data.repositories.helper_classes.URLService;
import com.atipera.api.git_data.repositories.interfaces.DataSource;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.nio.charset.Charset;

@Repository
public class GitApiRepository implements DataSource {

    private final String usersRepositoriesURLString = "https://api.github.com/users/USER/repos";
    private final String repositoriesBranchesURLString = "https://api.github.com/repos/USER/REPOSITORY/branches";

    @Override
    public String getRepositories(String user) {

        String modifiedURLString = PathModifier.injectUsersName(user, usersRepositoriesURLString);
        URL correctURL = URLService.createURL(modifiedURLString);

        return URLService.readURL(correctURL, Charset.defaultCharset());
    }

    @Override
    public String getBranches(String user, String repository) {

        String modifiedURLString = PathModifier.injectUsersName(user, repositoriesBranchesURLString);
        modifiedURLString = PathModifier.injectRepositoryName(repository, modifiedURLString);
        URL correctURL = URLService.createURL(modifiedURLString);

        return URLService.readURL(correctURL, Charset.defaultCharset());
    }
}
