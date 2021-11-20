FROM nginx:1.13.3-alpine
COPY /nginx/default.conf /etc/nginx/conf.d/
RUN  -rf/usr/share/nginx/html/*
COPY dist/shop /usr/share/nginx/html
