FROM node as builder

# Resolve Dependencies
COPY package.json /src/
WORKDIR /src/
RUN npm install

# Build the project
# mind .dockerignore!
COPY *.json *.ts /src/
COPY public /src/public
COPY src /src/src
# COPY types /src/types
RUN npm run build


FROM twalter/openshift-nginx
ENTRYPOINT ["nginx", "-g","daemon off;"]
EXPOSE 8081
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /src/build/ /usr/share/nginx/html
