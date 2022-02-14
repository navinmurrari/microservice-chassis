## Steps to setup project

1. Check maven is installed using command mvn -version
2. Build archetype using `mvn clean install`
3. create a new directory for the new micro service
    - `mkdir new-microservice`
    - `cd new-microservice`
4. generate project from chassis change the group id,artifact id,version,package name & port number <br> `mvn archetype:generate -DarchetypeGroupId=com.example.archetypes -DarchetypeArtifactId=micro-service-chassis -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=com.example -DartifactId=new-microservice -Dversion=1.0 -Dpackage=com.example.sample.ms -Dport=9876`
5. You will be prompted to confirm the details so press 'Y' to continue
6. check the new-microservice folder the src and pom.xml should be created
7. To build the new-microservice project `mvn clean install`
8. import project to intellij

## Steps to generate api and models stub using swagger.yaml

1. Update swagger.yaml using https://editor.swagger.io/
2. Enter the command `mvn clean generate-sources -P stub-generation` this will generate models and api classes in the generated folder under base directory
3. copy the api and model classes into the project