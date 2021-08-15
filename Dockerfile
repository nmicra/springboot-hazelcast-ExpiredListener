FROM adoptopenjdk/openjdk11:alpine
RUN mkdir /work
VOLUME /work
COPY build/libs/*.jar /work/app.jar
WORKDIR /work
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx256m","-jar","/work/app.jar"]