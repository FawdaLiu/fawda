docker build -t my-mysql-image .


```
FROM openjdk:8
MAINATINER fawda
LABEL name="dockerfile-demo" version="1.0" author="jack"
COPY dockerfile-demo-0.0.1-SNAPSHOT.jar dockerfile-image.jar
CMD ["java", "-jar", "dockerfile-image.jar"]
```