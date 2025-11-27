FROM nginx:alpine

COPY . /usr/share/nginx/html/

CMD ["sh", "-c", "nginx -c /usr/share/nginx/html/nginx-${PROFILE}.conf -g 'daemon off;'"]