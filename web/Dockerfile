FROM node:18-alpine AS build
RUN npm install -g serve
WORKDIR /app/tacs
COPY . ./
RUN npm ci; npm run build
ENTRYPOINT ["serve", "-s", "build"]
EXPOSE 3000

#FROM nginx:stable-alpine
#COPY --from=build /app/tacs/build /usr/share/nginx/html
#COPY nginx.conf /etc/nginx
#CMD ["nginx", "-g", "daemon off;"]
#EXPOSE 3000

