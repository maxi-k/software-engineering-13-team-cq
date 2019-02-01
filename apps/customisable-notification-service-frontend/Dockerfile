FROM node as builder

RUN npm install -g yarn

COPY . /app/ 
WORKDIR /app/
# COPY types /src/types
RUN yarn install
RUN yarn build

FROM twalter/openshift-nginx
ENTRYPOINT ["nginx", "-g","daemon off;"]
EXPOSE 8081
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/build/ /usr/share/nginx/html

