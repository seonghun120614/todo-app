# 1) MySQL 공식 이미지
FROM mysql:8

ENV TZ=Asia/Seoul \
    DEBIAN_FRONTEND=noninteractive

RUN microdnf -y install tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    microdnf clean all

COPY scheme.sql /docker-entrypoint-initdb.d/scheme.sql

CMD ["mysqld", \
     "--character-set-server=utf8mb4", \
     "--collation-server=utf8mb4_unicode_ci", \
     "--default-time-zone=+09:00"]