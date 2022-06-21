docker context use default
docker rm $(docker ps -aq)
docker image rm $(docker images -q)
