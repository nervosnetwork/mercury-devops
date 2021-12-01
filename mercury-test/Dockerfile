FROM gradle:6-jdk8 AS builder

WORKDIR /build
COPY . .
RUN gradle build

FROM openjdk:8-jre-slim
WORKDIR /app

COPY --from=builder /build/regression-test/build/libs/mercury-test-0.1.0.jar /app/mercury-test-0.1.0.jar



