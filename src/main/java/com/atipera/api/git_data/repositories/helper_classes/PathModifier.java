package com.atipera.api.git_data.repositories.helper_classes;

public class PathModifier {


    public static String injectUsersName(String user, String path) {
        return path.replaceFirst("USER", user);
    }

    public static String injectRepositoryName(String repository, String path) {
        return path.replaceFirst("REPOSITORY", repository);
    }
}
