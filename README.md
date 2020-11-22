# k8s playground

This is a playground project with a simple k8s cluster
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
