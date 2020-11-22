### How to prepare Play application to run inside Kubernetes

The following steps are exactly the same as dockerizing a Play application
- I initialized a Play app from this [archetype](<https://github.com/playframework/play-scala-isolated-slick-example/>)
- added MySQL connector, I left h2database for tests
- added a production config file overriding the default `application.conf` which contains the test environment configuration
- added Play secret, otherwise the app will not start in prod mode
- added `kubernetes.docker.internal` to allowed hosts, otherwise the app will not work behind Ingress
- enabled evolutions autoapply, otherwise the app will not start in docker if the db is not initialized
- downloaded `sbt` script from https://github.com/paulp/sbt-extras into `scripts/` folder, it will download and run sbt inside the docker container
- enable `sbt assembly` to build fatjar inside the docker container, it is to precompile the app
- prepare a Dockerfile based from `openjdk:8-jdk-alpine`, we need JDK to compile the app, JRE doesn't work
