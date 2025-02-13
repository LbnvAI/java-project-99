FROM gradle:8.10.0-jdk21

WORKDIR /app

COPY /app .

RUN gradle installDist

EXPOSE 8080

CMD ./build/install/app/bin/app --spring.profiles.active=production