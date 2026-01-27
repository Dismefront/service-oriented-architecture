FROM nginx:1.25-alpine

RUN rm /etc/nginx/conf.d/default.conf

COPY nginx/nginx.conf /etc/nginx/nginx.conf

COPY nginx/ssl /etc/nginx/ssl

COPY dist /usr/share/nginx/html

