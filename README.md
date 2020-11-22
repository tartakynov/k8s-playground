# k8s playground

This is a playground project with a simple k8s setup
- web application written in Scala (initialized from this [archetype](<https://github.com/playframework/play-scala-isolated-slick-example/>))
- phpMyAdmin
- MySQL database
- NGINX Ingress Controller
  - `/app/` - web app
  - `/pma/` - phpMyAdmin

### docker-compose
Docker-compose prepares the Docker images used in this project
```
docker-compose build
docker-compose up -d
```

### kompose
Also, I used docker-compose to bootstrap Kubernetes configs with kompose
```
curl -L https://github.com/kubernetes/kompose/releases/download/v1.21.0/kompose-darwin-amd64 -o kompose
./kompose convert -o k8s/
```

### Update the configs
Changelog
- don't try to pull the app image from remote Docker registry

### Run on Kubernetes
After making changes to the bootstrapped configs, it's time to run the app on the cluster
```
kubectl apply -f k8s/db-data-persistentvolumeclaim.yaml,k8s/db-deployment.yaml
kubectl apply -f k8s/pma-deployment.yaml,k8s/pma-service.yaml
kubectl apply -f k8s/app-deployment.yaml,k8s/app-service.yaml

# get logs
kubectl get pods
kubectl logs app-d7c779d4c-lfl9r -f
```
