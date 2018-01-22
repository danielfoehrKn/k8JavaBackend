# Use some image as parent
FROM bitnami/tomcat:latest

# Set the working directory to /app  -> starting from the directory where the dockerfile is in
# WORKDIR /app

# Copy the current directory contents into the container into the tomcat webapp directory
ADD /toBeDeployed/*.war /opt/bitnami/tomcat/webapps/
ADD /toBeDeployed/*server.xml /opt/bitnami/tomcat/conf/

# EXPOSE 9000

# Runapp when the container launches
CMD ["catalina.sh", "run"]