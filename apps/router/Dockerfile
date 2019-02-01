FROM twalter/openshift-nginx
ENTRYPOINT ["nginx", "-g","daemon off;"]
EXPOSE 8080
COPY nginx.conf /etc/nginx/conf.d/default.conf
