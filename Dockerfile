FROM maven:3.6.3-jdk-8
  
COPY target/crm-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "crm-0.0.1-SNAPSHOT.jar"]