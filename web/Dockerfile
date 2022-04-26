FROM node:18-alpine AS build
WORKDIR /usr/local/www/tacs
COPY ./ /usr/local/www/tacs
CMD ["npm", "install"]
ENTRYPOINT ["npm","start"]
EXPOSE 3000

