# k8s playground

This is a playground project used to demonstrate how to run a Play Framework web application on Kubernetes with Docker for Mac. For educational purposes only.

We'll be running the following apps on Kubernetes
- a [web application](PLAY.md) written in Scala
- phpMyAdmin
- MySQL database
- NGINX Ingress
  - the web app will be available via http://kubernetes.docker.internal/app/
  - phpMyAdmin will be available via http://kubernetes.docker.internal/pma/

### Build
Docker-compose prepares the Docker images used in this project
```
docker-compose build
```

Docker-compose is also used to bootstrap Kubernetes configs (via kompose)
```
curl -L https://github.com/kubernetes/kompose/releases/download/v1.21.0/kompose-darwin-amd64 -o kompose
./kompose convert -o k8s/
```

### Update the configs
Changelog
- don't lookup the app image in the remote Docker registry, it is built locally
- add db service to expose MySQL to other pods in the cluster
- add ingress resource
- set phpMyAdmin absolute URL to `http://kubernetes.docker.internal/pma/`, otherwise it does not include the prefix (`pma/`) when redirecting to pages
- concatenated k8s yamls together (deployment and service)

### Run on Kubernetes
After making changes to the bootstrapped configs, it's time to run the apps on the cluster
```
kubectl apply -f k8s/db.yaml
kubectl apply -f k8s/pma.yaml
kubectl apply -f k8s/app.yaml

# verify that our pods are running
kubectl get pods
kubectl logs app-757b866576-sdg4q -f
kubectl proxy # in a separate terminal
curl http://localhost:8001/api/v1/namespaces/default/pods/pma-7f856985d9-r94n5/proxy/
curl http://localhost:8001/api/v1/namespaces/default/pods/app-86f5ccd9bc-kms9v/proxy/

# after making changes in the apps code rebuild the image and restart
docker-compose build
kubectl -n default rollout restart deployment app
```

### Run Ingress
Initialize Ingress in the Docker for Mac cluster
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.41.2/deploy/static/provider/cloud/deploy.yaml
```

Apply configs to expose phpMyAdmin and the webapp to host via Ingress
```
kubectl apply -f k8s/ingress.yaml

# verify that Ingress is serving both the webapp and PMA
curl http://kubernetes.docker.internal/app/
curl http://kubernetes.docker.internal/pma/
```
