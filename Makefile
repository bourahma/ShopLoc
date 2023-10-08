clean:
	docker-compose stop
	docker system prune

run: 
	docker-compose up

stop: 
	docker-compose stop