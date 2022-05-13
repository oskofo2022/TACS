# TACS
El uso de myself a las urls hace referencia a APIs a ser consumidas por usuarios que fueron autenticados, la decisión tomada por el equipo fue la de guardar el Id del usuario como parte de su JWT de sesión el cual se usará en la APIs que posean ese identificador como parte de sus URLs.

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
