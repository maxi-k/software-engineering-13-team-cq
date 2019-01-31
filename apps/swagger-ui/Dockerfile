FROM twalter/openshift-nginx
ENTRYPOINT ["nginx", "-g","daemon off;"]
EXPOSE 8081

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY ./src/* /usr/share/nginx/html/
COPY ./index.html /usr/share/nginx/html/