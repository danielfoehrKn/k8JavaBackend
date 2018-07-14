FROM bitnami/tomcat:latest

# Copy the current directory contents into the container into the tomcat webapp directory
ADD /toBeDeployed/*.war /opt/bitnami/tomcat/webapps/
#ADD /toBeDeployed/*server.xml /opt/bitnami/tomcat/conf/

# Start tomcat server process
# Tomcat server will boot with port from env variable ${TOMCAT_CONNECTION_PORT} -> see server.xml
# run image with local_run.bat
CMD ["catalina.sh", "run"]