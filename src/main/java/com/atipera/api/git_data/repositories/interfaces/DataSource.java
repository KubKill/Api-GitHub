package com.atipera.api.git_data.repositories.interfaces;

import java.util.List;

public interface DataSource {

    String getRepositories(String user);
    String getBranches(String user, String repository);
}
