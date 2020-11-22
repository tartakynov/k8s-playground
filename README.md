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
```

### kompose
Also, I used docker-compose to bootstrap Kubernetes configs with kompose
```
curl -L https://github.com/kubernetes/kompose/releases/download/v1.21.0/kompose-darwin-amd64 -o kompose
./kompose convert -o k8s/
```

### Update the configs
Changelog
- don't lookup the app image in the remote Docker registry, it is built locally
- expose MySQL to other pods in the cluster

### Run on Kubernetes
After making changes to the bootstrapped configs, it's time to run the app on the cluster
```
kubectl apply -f k8s/db-data-persistentvolumeclaim.yaml,k8s/db-deployment.yaml
kubectl apply -f k8s/pma-deployment.yaml,k8s/pma-service.yaml
kubectl apply -f k8s/app-deployment.yaml,k8s/app-service.yaml
kubectl get pods
kubectl proxy # in a separate terminal

# get logs
kubectl logs app-757b866576-sdg4q -f

# check PMA
curl http://localhost:8001/api/v1/namespaces/default/pods/pma-7f856985d9-r94n5/proxy/

# check app
curl http://localhost:8001/api/v1/namespaces/default/pods/app-86f5ccd9bc-kms9v/proxy/

# restart app
kubectl -n default rollout restart deployment app
```
