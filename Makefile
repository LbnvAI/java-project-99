.PHONY:build

build:
	./gradlew clean build
test:
	./gradlew test
report:
	./gradlew jacocoTestReport
dev:
	./gradlew run --args='--spring.profiles.active=development'