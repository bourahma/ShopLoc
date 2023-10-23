clean:
	docker-compose stop
	docker system prune
	docker volume prune
	docker rmi shoploc-be:latest
	docker rmi shoploc-fe:latest

run: 
	docker-compose up

stop: 
	docker-compose stop

runDevDB:
	docker run --name shoploc-db-dev -e POSTGRES_USER=shoplocU -e POSTGRES_PASSWORD=shoplocP -e POSTGRES_DB=shoploc-db -d -p 5432:5432 postgres:latest --add-env POSTGRES_HOST_AUTH_METHOD=trust

stopDevDB:
	docker stop shoploc-db-dev

restartDevDB:
	docker restart shoploc-db-dev