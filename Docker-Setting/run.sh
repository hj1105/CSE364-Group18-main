#!/bin/bash

# 1. git clone group repository
git clone https://github.com/east-saint/CSE364-Group18.git

# 2. move to git repository
cd CSE364-Group18

# 3. Start mongoDB
service mongodb start

# 4. run spring boot application
mvn spring-boot:run
