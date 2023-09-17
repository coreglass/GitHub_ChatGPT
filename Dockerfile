# Use the CentOS 5 base image
FROM centos:5

# Set the maintainer information
MAINTAINER SungKwang Song <saltfactory@gmail.com>

# Install the ld-linux.so.2 library
RUN yum -y install ld-linux.so.2

# Add the Java binary installer file to the root directory of the image
ADD ./j2sdk-1_4_2_19-linux-i586-rpm.bin /

# Give execute permissions to the Java binary installer
RUN chmod +x /j2sdk-1_4_2_19-linux-i586-rpm.bin

# Run the Java binary installer with "yes" as input to automatically accept the license agreement
RUN (echo yes) | sh /j2sdk-1_4_2_19-linux-i586-rpm.bin

# Install the Java package using RPM
RUN rpm -Uvh /j2sdk-1_4_2_19-linux-i586.rpm

# Set the JAVA_HOME environment variable to the Java installation directory
ENV JAVA_HOME /usr/java/j2sdk1.4.2_19

# Add the Java binary directory to the system's PATH
ENV PATH $PATH:$JAVA_HOME/bin

# Create a Docker volume named "/data" for persistent data storage
VOLUME /data
