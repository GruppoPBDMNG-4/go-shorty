FROM chrispiemo/java-maven

ADD ./server /code/server
ADD ./client/dist /code/client/dist

WORKDIR /code/server
RUN mvn package -Dmaven.test.skip=true

ENTRYPOINT ["java", "-jar"]
CMD ["target/go-shorty-jar-with-dependencies.jar"]

EXPOSE 4567

