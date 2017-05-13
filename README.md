# stateless-spring-security
Stateless Spring Security application
To Start:
docker network create my-env

docker run --net my-env -itd --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test -p 3305:3306 -v /tmp:/tmp mysql

docker run --restart=always -itd --name logs-container bijoydocker/logs-image

docker run --net my-env -itd --name app-container --env-file=app.env -p 9090:8080 --volumes-from logs-container bijoydocker/restful-security

To Stop:
docker commit -m "initial logs added" logs-container bijoydocker/logs-image

docker push bijoydocker/logs-image

docker stop logs-container

docker stop app-container

docker stop mysql-container