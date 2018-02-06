#!/bin/bash

# plugins: build timeout plugin, Config File Provider Plugin, GitHub plugin, SSH Slaves plugin, Timestamper, Warnings Plug-in, Workspace Cleanup Plugin
# Maven Integration plugin - read because wasn't installed 

# maven: Maven Project Configuration: default .m2/, Maven Configuration Default settings provider and Default global settings provider /etc/maven/settings.xml Maven instalacji: MAVEN_HOME /usr/share/maven
# JDK: JAVA_HOME: /docker-java-home
# on docker machine:

apt-get update;
apt-get install maven; 


# github hook
# download ngrok and type on local machine, because github must see where is our local machine, typing only ip won't get connection.
# http://www.inanzzz.com/index.php/post/eh2c/setting-up-jenkins-on-ubuntu-with-vagrant-and-accessing-it-on-internet-with-ngrok


./ngrok http 9090

# it will produce b43a649c.ngrok.io -> localhost:9090 so on github type  http://b43a649c.ngrok.io/github-webhook/
# in jenkins type: Jenkins URL  http://localhost:9090/	Override Hook URL http://localhost:8080/github-webhook/ - 9090 and 8080 because port 
# forwarding 
