runDB:
	docker run --name shoploc-db-container -e POSTGRES_USER=shoplocU -e POSTGRES_PASSWORD=shoplocP -e POSTGRES_DB=shoploc-db -d -p 5432:5432 postgres:latest

stopDB:
	docker stop shoploc-db-container

restartDB:
	docker restart shoploc-db-container

runApp:
	mvn spring-boot:run

clean:
	- make stopDB
	- docker system prune
	- mvn clean

run: clean
	make runDB
	make runApp