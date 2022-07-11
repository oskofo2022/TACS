# TACS
El uso de myself a las urls hace referencia a APIs a ser consumidas por usuarios que fueron autenticados, la decisión tomada por el equipo fue la de guardar el Id del usuario como parte de su JWT de sesión el cual se usará en la APIs que posean ese identificador como parte de sus URLs.



## <img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Flogos-world.net%2Fwp-content%2Fuploads%2F2021%2F05%2FAzure-Logo.png&f=1&nofb=1" height="20" widht="20"/>Azure
swagger: http://tacs-wordle-web1.brazilsouth.azurecontainer.io:8080/api/swagger-ui/index.html#/

front-end: http://tacs-wordle-web1.brazilsouth.azurecontainer.io:3000/

## Local
### Correr con docker:
```
$ mvn clean install -Dmaven.test.skip=true
$ docker build --tag wordle .
$ docker run -d --name wordle wordle
```
swagger: http://172.17.0.2:8080/api/swagger-ui/index.html#/


### Correr con docker-compose
```
$ mvn clean install -Dmaven.test.skip=true
$ docker-compose up -d
```
swagger: http://localhost:8080/api/swagger-ui/index.html#/ 

front-end: http://localhost:3000/
