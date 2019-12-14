
run: target/pragphil.war
	docker-compose up --build

target/pragphil.war:
	mvn package

clean:
	mvn clean
