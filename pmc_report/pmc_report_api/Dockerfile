FROM java:8
EXPOSE 8090

VOLUME /tmp
ADD gean-api.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
