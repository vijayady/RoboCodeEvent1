# Use an official Ubuntu as a parent image
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# Update and install necessary packages
RUN apt-get update -y && \
    apt-get install -y \
    openjdk-11-jdk \
    x11-apps \
    xauth \
    xorg \
    xfce4 \
    xfce4-goodies \
    x2goserver \
    x2goserver-xsession \
    libgtk-3-0 \
    wget \
    xvfb \
    tigervnc-standalone-server \
    xterm \
    git && \
    rm -rf /var/lib/apt/lists/*

# Install noVNC
RUN wget https://github.com/novnc/noVNC/archive/refs/tags/v1.2.0.tar.gz && \
    tar -xzf v1.2.0.tar.gz && \
    mv noVNC-1.2.0 /opt/novnc && \
    rm v1.2.0.tar.gz

# Set environment variables
ENV DISPLAY=:1
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV USER=root

# Copy application files
COPY . /app

WORKDIR /app

# Expose ports
EXPOSE 22
EXPOSE 5901
EXPOSE 6080

# Compile the CheckTranslucency program
RUN javac CheckTranslucency.java

# Start X2Go server, VNC server, and the application
CMD ["sh", "-c", "service x2goserver start && mkdir -p /root/.vnc && echo 'password' | vncpasswd -f > /root/.vnc/passwd && chmod 600 /root/.vnc/passwd && vncserver :1 -geometry 1280x800 -depth 24 -xstartup /usr/bin/startxfce4 && /opt/novnc/utils/launch.sh --vnc localhost:5901 --listen 6080 && DISPLAY=:1 java CheckTranslucency && DISPLAY=:1 java -cp .:lib/* -jar robocode-tankroyale-gui-0.29.0.jar"]