#!/usr/bin/env bash

#start the db container first
docker run -d --name db db_image

#start the webapp container with the link to db container
docker run -d --name webapp -p 8080:8080 --link db:webdb webapp_image

#start the frontend container
docker run -it --rm --name chat -p 3000:3000 chat_image
