FROM openjdk:17-oracle
COPY target/*.jar ${artifactId}.jar
ENTRYPOINT ["java","-jar","/${artifactId}.jar"]