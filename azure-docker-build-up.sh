docker context use default
docker compose build
TOKEN=$(az acr login --name tacsWordleRegistry --expose-token --output tsv --query accessToken)
docker login tacsWordleRegistry.azurecr.io -u 00000000-0000-0000-0000-000000000000 -p $TOKEN
docker compose push
docker context use wordlecontext
docker compose up