# Gridhunt

Gridhunt is a grid-based dungeon game with a Spring Boot web interface. Explore a hidden map, use nearby clues to find enemies and pits, and defeat the enemies to win. The project also includes accounts, scores, ratings, and comments.

## Features

- Random maps with Easy (10×10), Medium (20×20), and Hard (30×30) difficulties, plus a Testing mode.
- Nearby clues: a **stench** signals a monster, a **breeze** signals a pit, and **flapping wings** signals a bat.
- Directional movement and attacks, with a visibility toggle for the map.
- User registration and login, a leaderboard, and rating and comment pages.

The player loses by entering a pit or meeting a Wumpus. Bats move the player to another tile. Defeating enemies adds to the score; removing all enemies wins the level.

## Requirements

- Java 17
- Maven 3.6.3 or newer
- PostgreSQL

## Run locally

1. Start PostgreSQL and create the database:

   ```sh
   createdb gamestudio
   ```

2. The default database settings are in `src/main/resources/application.properties`:

   - URL: `jdbc:postgresql://localhost/gamestudio`
   - Username: `postgres`
   - Password: `postgres`

   Set Spring Boot environment variables if your local PostgreSQL settings differ. For example:

   ```sh
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/gamestudio
   export SPRING_DATASOURCE_USERNAME=postgres
   export SPRING_DATASOURCE_PASSWORD=your-password
   ```

   Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update` and creates or updates the application tables when the server starts.

3. Start the web application from the project root:

   ```sh
   mvn spring-boot:run
   ```

4. Open [http://localhost:8080](http://localhost:8080) and choose **Play Now**. The game is also available directly at `/gridhunt`.

## Controls

| Action | Keyboard | On-screen controls |
| --- | --- | --- |
| Move | `W`, `A`, `S`, `D` | Direction buttons |
| Attack one tile away | `H`, `J`, `K`, `L` | Attack buttons |
| Toggle map visibility | `V` | **Toggle visibility** |

## Build and tests

Build the application:

```sh
mvn clean package
```

Run the test suite:

```sh
mvn test
```

The persistence tests use PostgreSQL, so configure and start the database before running them.

## Project layout

```text
src/main/java/gamestudio/game/gridhunt/   Game rules, map, actors, and console UI
src/main/java/gamestudio/server/          Spring Boot application and web controllers
src/main/java/gamestudio/service/         Persistence and service implementations
src/main/java/gamestudio/entity/          JPA entities
src/main/resources/templates/             Thymeleaf pages
src/main/resources/static/                CSS and JavaScript
dbs_scripts/                              SQL scripts
```
