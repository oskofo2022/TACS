FROM node:18-alpine AS build
WORKDIR /usr/local/www/tacs
COPY ./ /usr/local/www/tacs
CMD ["npm", "install"]
# CMD ["npm","run", "build"]
# CMD ["npm", "install", "-g", "serve"]
# ENTRYPOINT["serve", "-s", "build"]
ENTRYPOINT ["npm", "start"]
EXPOSE 3000

