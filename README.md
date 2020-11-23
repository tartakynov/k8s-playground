# k8s playground

This is a playground project used to demonstrate how to run a Play Framework web application on Kubernetes with Docker for Mac. For educational purposes only.

We'll be running the following apps on Kubernetes
- a [web application](PLAY.md) written in Scala
- phpMyAdmin
- MySQL database
- NGINX Ingress
  - the web app will be available via http://kubernetes.docker.internal/webapp/
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
CHANGELOG
- concatenate k8s yamls together (deployment and service)
- prevent Kubernetes from looking up the webapp's image in the remote Docker registry since it's built locally
- expose MySQL to other pods in the cluster: add db service in `db.yaml`
- set phpMyAdmin absolute URL to `http://kubernetes.docker.internal/pma/` with environment variable, otherwise it will not include the prefix `/pma/` when self-redirecting to pages
- expose phpMyAdmin and the webapp to host via Ingress: add `ingress.yaml`
- add secrets

### Secrets
Apply Kubernetes secrets. You should never commit your secrets config into `git`, I've added it here only for illustration purposes
```
kubectl apply -f k8s/secrets.yaml

# verify that secrets are created
kubectl describe secrets k8s-secrets
```

### Run our apps on Kubernetes
After making changes to the bootstrapped configs, it's time to run the apps on the cluster
```
kubectl apply -f k8s/db.yaml
kubectl apply -f k8s/pma.yaml
kubectl apply -f k8s/webapp.yaml

# verify that our pods are running
kubectl get pods
kubectl logs webapp-5d6748cf-86kxx # the last bits of the name are generated randomly, so in your case, the exact name will be different
kubectl proxy # in a separate terminal
curl http://localhost:8001/api/v1/namespaces/default/pods/pma-7f856985d9-r94n5/proxy/
curl http://localhost:8001/api/v1/namespaces/default/pods/webapp-5d6748cf-86kxx/proxy/

# if changed the webapp's code - rebuild the image and restart
docker-compose build
kubectl -n default rollout restart deployment webapp
```

### Expose our apps with Ingress
Initialize Ingress in the Docker for Mac cluster
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.41.2/deploy/static/provider/cloud/deploy.yaml
```

Wait for a minute or two while Ingress is loading, and then apply configs to expose phpMyAdmin and the webapp to host via Ingress
```
kubectl apply -f k8s/ingress.yaml

# verify that Ingress is serving both the webapp and PMA
curl http://kubernetes.docker.internal/webapp/
curl http://kubernetes.docker.internal/pma/
```
