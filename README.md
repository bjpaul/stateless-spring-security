# stateless-spring-security
Stateless Spring Security application
Run:
docker network create my-env

docker run --net my-env -itd --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test -p 3305:3306 -v /tmp:/tmp mysql

sudo mkdir /logs

docker run --net my-env -itd --name app-container -p 9090:8080 bijoydocker/restful-security
