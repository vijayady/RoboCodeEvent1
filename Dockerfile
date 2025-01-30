# Use an official Ubuntu as a parent image
FROM ubuntu:20.04

# Set environment variable to avoid interactive prompts during package installation
ENV DEBIAN_FRONTEND=noninteractive

# Use a faster mirror for apt
RUN sed -i 's|http://archive.ubuntu.com/ubuntu/|http://mirror.math.princeton.edu/pub/ubuntu/|g' /etc/apt/sources.list

# Install necessary packages in separate RUN commands to leverage caching
RUN apt-get update -y -qq && apt-get install -y -qq x11-apps && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq xauth && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq xorg && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq openjdk-11-jdk && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq xfce4 && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq xfce4-goodies && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq tightvncserver && rm -rf /var/lib/apt/lists/*
RUN apt-get update -y -qq && apt-get install -y -qq wget novnc websockify && rm -rf /var/lib/apt/lists/*

# Set environment variables for GUI
ENV DISPLAY=:1
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV USER=root

# Set VNC password
RUN mkdir -p /root/.vnc && echo "yourpassword" | vncpasswd -f > /root/.vnc/passwd && chmod 600 /root/.vnc/passwd

# Create xstartup script
RUN echo -e "#!/bin/sh\nxrdb $HOME/.Xresources\nstartxfce4 &" > /root/.vnc/xstartup && chmod +x /root/.vnc/xstartup

# Copy the application code
COPY . /app

# Set the working directory
WORKDIR /app

# Expose the VNC and noVNC ports
EXPOSE 5901 6080

# Start the VNC server, noVNC, and run the application
CMD ["sh", "-c", "export USER=root && vncserver :1 -geometry 1280x800 -depth 24 && websockify --web=/usr/share/novnc/ 6080 localhost:5901 & java -cp .:lib/* -jar robocode-tankroyale-gui-0.29.0.jar"]