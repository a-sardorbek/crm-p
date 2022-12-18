FROM suranagivinod/openjdk8
  
COPY ./project.jar .

CMD ["java", "-jar", "project.jar"]