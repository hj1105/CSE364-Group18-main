# 1. Download ubuntu20.04
FROM ubuntu:20.04
ENV DEBIAN_FRONTEND noninteractive



# 2. Install other utilities
RUN apt-get update && \
    apt-get install -y vim && \
    apt-get install -y git



# 3. Install Java and Maven 
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get install -y maven



# 4. Install Tomcat 
RUN apt-get update && apt-get install -y wget
RUN apt-get update && apt-get install -y gzip
RUN wget https://mirror.navercorp.com/apache/tomcat/tomcat-9/v9.0.46/bin/apache-tomcat-9.0.46.tar.gz -O /opt/tomcat.tar.gz
WORKDIR /opt
RUN tar -zvxf tomcat.tar.gz
RUN mv apache-tomcat-9.0.46/ tomcat/



# 5. Install mongoDB
RUN apt-get update && \
    apt-get install -y mongodb



# 6. Move into /opt/tomcat/webapps, delete ROOT directory and copy ROOT.war file
WORKDIR /opt/tomcat/webapps
RUN rm -rf ROOT
COPY ROOT.war .



# 7. Move to /opt and make command.sh
WORKDIR /opt
RUN echo "service mongodb start" >> command.sh && \
    echo "tomcat/bin/catalina.sh run" >> command.sh



# 8. Add run.sh file to /root/project in docker container
WORKDIR /root/project
COPY run.sh .



# 9. Connect ports for mongodb and tomcat
EXPOSE 27017
EXPOSE 8080



# 10. Run /opt/command.sh
WORKDIR /opt
CMD ["/bin/bash", "command.sh"]
