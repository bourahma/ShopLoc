runDB:
	docker run --name shoploc-db-container -e POSTGRES_USER=shoploc -e POSTGRES_PASSWORD=shoploc -e POSTGRES_DB=shoploc-db -d -p 5432:5432 postgres:latest

stopDB:
	docker stop shoploc-db-container

restartDB:
	docker restart shoploc-db-container
