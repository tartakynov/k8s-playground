# k8s playground

This is a playground project with a simple k8s cluster
- web application written in Scala (initialized from this [archetype](<https://github.com/playframework/play-scala-isolated-slick-example/>))
- phpMyAdmin
- MySQL database
- NGINX Ingress Controller
  - `/app/` - web app
  - `/pma/` - phpMyAdmin

### docker-compose
```
docker-compose build
docker-compose up -d
```

### kompose
I used kompose to convert docker-compose to Kubernetes configs
```
kompose convert
```
