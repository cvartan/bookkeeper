build:
	mvn package -Dflyway.skip=true
	docker build --tag bookkeeper:v1 .

clean:
	rm -rf target/*.jar
	rm -rf target/*.jar.original