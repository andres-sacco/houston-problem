start:
	mvn clean install -Dmaven.test.skip=true -f api-catalog/
	docker-compose build
	docker-compose up

stop:
	docker-compose down

start-infra:
	docker-compose -f docker-compose-infrastructure.yml build
	docker-compose -f docker-compose-infrastructure.yml up

stop-infra:
	docker-compose -f docker-compose-infrastructure.yml down

run-e2e:
	mvn verify -P karate -f end-to-end-tests/

run-performance:
	mvn verify -P gatling -f end-to-end-tests/

.PHONY: start stop start-infra stop-infra run-e2e run-performance
