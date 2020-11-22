# k8s playground

This is a playground project with a simple k8s cluster
- Web application (Play Framework, <https://github.com/playframework/play-scala-isolated-slick-example/>)
- phpMyAdmin
- MySQL database
- NGINX Ingress Controller
  - `/app/` - web app
  - `/pma/` - phpMyAdmin

### docker-compose
```
sbt assembly
docker-compose build
docker-compose up -d
```
