# MariaDB 공식 이미지를 베이스로 사용합니다.
FROM mariadb:latest

LABEL authors="user"

ENV MARIADB_DATABASE="mydb" \
    MARIADB_USER="user"

EXPOSE 3306


