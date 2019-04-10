FROM java:8
EXPOSE 8091

VOLUME /tmp
ADD gean-manager.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
