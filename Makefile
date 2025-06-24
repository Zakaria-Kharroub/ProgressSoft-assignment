run: ## Start all services
	docker compose up

run-detached: ## Start all services in background
	docker compose up -d

down: ## Stop and remove all containers
	docker compose down

stop: ## Stop all services
	docker compose stop

restart: ## Restart all services
	docker compose restart

status: ## Show status of all containers
	docker compose ps

build: ## Build all services
	docker compose build

clean: ## Clean up containers, images, and volumes
	docker compose down -v --rmi all --remove-orphans

test: ## Run tests
	./mvnw test

test-coverage: ## Run tests with coverage report
	./mvnw clean test jacoco:report