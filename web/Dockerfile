FROM node:18-alpine AS build
WORKDIR /app/tacs
COPY package.json ./
COPY package-lock.json ./
RUN npm ci
COPY . ./
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/tacs/build /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
EXPOSE 3000

