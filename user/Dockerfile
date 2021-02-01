# we will use openjdk 8 with alpine as it is a very small linux distro
FROM maven:3.6.3-jdk-8

#copy files
COPY ./ ./
 
# package our application code
RUN mvn clean package

# set the startup command to execute the jar
CMD ["java", "-jar", "./target/user-0.0.1-SNAPSHOT.jar"]