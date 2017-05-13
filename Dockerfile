FROM bijoydocker/ubuntu-image
MAINTAINER bijoy.paul@tothenew.com
COPY ./src /code/src
COPY ./build.gradle /code/build.gradle
WORKDIR /code
ENV host=mysql-container
ENV logDir=/logs/app
VOLUME "/tmp"
RUN java -version \
    && export GRADLE_HOME=/opt/gradle/gradle-3.4.1 \
    && export PATH=$PATH:$GRADLE_HOME/bin \
    && gradle --version \
    && gradle clean build \
    && cp build/libs/rest-security-0.0.1-SNAPSHOT.war ../app.war
# Remove code base from image
RUN rm -rf *
RUN cp ../app.war app.war
EXPOSE 8080
CMD ["java","-jar","app.war"]
LABEL "version"="1.0"