# The Pragmatic Philosopher

Best Philosopher management app on the web. Discover how ancient wisdom can lead to a better life for you and your favourite pet. Spring WebMVC demo app, loosely based on [Chad Darbys Udemy course](https://www.udemy.com/spring-hibernate-tutorial)

## Usage

- Setup Postgres and Tomcat locally or via Docker, check [docker-spring-stack](https://github.com/joerx/docker-spring-stack) to get the full stack up quickly
- Ensure Tomcat manager is running on http://localhost:8080/manager, manager username must be `tomcat`, password `tomcat`
- Create a database `pragphil`, initialise it with the [provided schema](./src/main/resources/schema.sql)
- Deploy project into Tomcat via Maven:

```
mvn clean tomcat7:redeploy
``` 

- Login with one of the test users, e.g. `plato`, password is `test123` for all of them
- Add/update users as you like, use [this bcrypt calculator](https://www.bcryptcalculator.com/) to generate passwords 

## Hot Reload & Debugging

- This does not work with Tomcat running in Docker, need a local Tomcat
- Useful for debugging, rapid feedback, e.g. when testing frontend changes

### Database

- Install & Postgres locally following the instructions for your respective OS
- _Or_ only run the postgres container in docker compose:

```
$ docker-compose up postgres
```

- Either way, Postgres should be listening on `localhost:5432`
- Change JDBC url in [src/main/resources/datasource.properties]
- _Or_ update `/etc/hosts` like this:

```
127.0.0.1 postgres
```

### IntelliJ

- Based on [this article](https://www.mkyong.com/intellij/intellij-idea-auto-reload-a-web-application-hot-deploy/)
- Make sure "Tomcat and TomEE Integration" is enabled
- Go to _Preferences > Build, Execution, Deployment > Application Servers_
- Add a new Tomcat server, point to local Tomcat install

![IntelliJ Application Servers](docs/media/intellij_app_servers.png)

- Create a new Run Configuration
- Important: in Deployment tab, select _exploded war_

![IntelliJ Select Artifact](docs/media/intellij_select_artifact.png)

- Make sure settings match your Tomcat settings
- For frontend stuff, simply run it and edit files
- Changes should show up automatically

![IntelliJ Tomcat Run Config](docs/media/intellij_tomcat_run_config.png)
