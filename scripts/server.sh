#!/usr/bin/env bash
mvn -Dmaven.test.skip=true spring-boot:run -Dspring-boot.run.main-class="gamestudio.server.GameStudioServer"
