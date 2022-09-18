# Api-GitHub

## Short Description
Recruitment exercise. One endpoint in SpringBoot. Gets GitHub users name requests. Replies with the users repositories info with its branches info if the user found. 

## Technologies
* Spring Boot 2.7.3

## Launch

1. Open the projects library in your IDE.
2. Run "APIAplication" class to start the program.
3. Send GET request to URL "http://localhost:8080/api/v1/git-data/USER". Replace "USER" in the URL with a github users name.

## Content

### Architecture

* REST
* Controler, Service, Repository

### Controller

* **GET /api/v1/git-data/{user}**

* Takes users name from paths variable. Accepts only requests with "Accept=application/json" header, otherwise won't provide the service.
* Uses "UsersRepositoriesService" interface to get a Bean providing the service. Returns JONSON with users repositories and their branches info.
* Uppon reciving errors from the service object sends proper responses with errors described. Handled by "CustomGlobalExceptionHandlerClass".

### Service

* Gets all the data from Bean injected into "DataSource" interface referrence. Gets data in String form.
* First, gets users repository data. Then, gets branches info for each users repository.
* Throws **"UserNotFoundException"** in case of not reciving information about the user. Throws **"InconsistentContentException"** uppon not reciving inforamtion about a repositories branches (case when we asked for users repositories, but before we asked for info about branches of the repositories some of the had been deleted).
* Uses helper classes where some tasks has been put.
* **Uses asynchonous method** to retrive repositories branches info.
* **Writes logs**.

### Repsoitory

* Gets users repositories data from GitHubAPI.
* Gets users repositories branches data form GitHubAPI.
