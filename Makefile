DOCKER_COMPOSE_FILE ?= docker-compose.yml
DANGLING_IMAGES := $(shell docker images -f "dangling=true" -q)

# RUN AND MANAGE
build: ## Build all images and remove all dangling images in the system
build:
	docker compose -f ${DOCKER_COMPOSE_FILE} build
	@if [ -n "$(DANGLING_IMAGES)" ]; then \
		docker rmi $(DANGLING_IMAGES); \
	fi

build-single: ## Build single service spectified by "name"
build-single:
	docker compose -f ${DOCKER_COMPOSE_FILE} build $(name)

run: ## Run the entire application
run: 
	docker compose -f ${DOCKER_COMPOSE_FILE} up

rund: ## Run the entire application in detach mode
rund:
	docker compose -f ${DOCKER_COMPOSE_FILE} up --detach

stop: ## Stop and remove all containers
stop:
	docker compose -f ${DOCKER_COMPOSE_FILE} down

clear: ## Stop and remove all containers with volumes
clear:
	docker compose -f ${DOCKER_COMPOSE_FILE} down --volumes 

watch: ## Watch logs of the container e.g `make watch name=api-db-1`
watch:
	docker logs -f $(name)

shell-db: ## Open shell in database container
shell-db:
	docker compose -f ${DOCKER_COMPOSE_FILE} exec db mysql -p
	
