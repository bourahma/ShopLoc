clean:
	docker-compose down
	docker system prune
	docker volume prune
	docker rmi shoploc-be:latest
	docker rmi shoploc-fe:latest

run: 
	docker-compose up

stop: 
	docker-compose stop

runDevDB:
	docker run --name shoploc-db-dev -e POSTGRES_USER=shoplocU -e POSTGRES_PASSWORD=shoplocP -e POSTGRES_DB=shoploc-db -d -p 5430:5432 postgres

stopDevDB:
	docker stop shoploc-db-dev

restartDevDB:
	docker restart shoploc-db-dev