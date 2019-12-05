FROM adorsys/java:11
EXPOSE 8080
WORKDIR /opt/bg-monitoring-server
COPY bg-monitoring-server/target/bg-monitoring-server.jar .
