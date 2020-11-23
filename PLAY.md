### Dockerizing Play application to run inside Kubernetes

These are the steps I took to dockerize the Play app
- I bootstrapped a Play app from this [archetype](<https://github.com/playframework/play-scala-isolated-slick-example/>)
- added MySQL connector and left h2database for tests
- added a production config file overriding the default `application.conf`, and left `application.conf` with the configuration for the test environment
- added Play secret to prod config. Otherwise, the app will not start in prod mode. Please never bake real production secrets into docker images!
- added `kubernetes.docker.internal` (Ingress host) to allowed hosts in prod config. Otherwise, the app will not work behind Ingress
- enabled evolutions auto-apply, otherwise the app will not start in prod mode if DB is not initialized
- downloaded `sbt` script from https://github.com/paulp/sbt-extras into `scripts/` folder
- prepared a Dockerfile based on `openjdk:8-jdk-alpine`. We need JDK to compile the app, JRE will not work
- used `sbt assembly` plugin to download dependencies and precompile the app into a production-ready fatjar during Docker build
