FROM adorsys/java:11
EXPOSE 8080
WORKDIR /opt/bg-monitoring
COPY /target/bg-monitoring.jar .
