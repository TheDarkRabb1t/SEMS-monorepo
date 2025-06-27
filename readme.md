# Smart Event Monitoring System #

Monorepo of microservices.

## Overview

This system is a Java + Spring Boot based event monitoring platform, composed of multiple microservices communicating
via RabbitMQ. It supports ingesting, processing, searching, and alerting on structured log or activity data.

## Services

- **Ingestion Service** – Accepts log/activity events via REST and pushes them to RabbitMQ.
- **Processor Service** – Consumes messages, and forwards to Elasticsearch.
- **Search API** – Exposes a REST interface to query historical data from Elasticsearch.
- **User Service** – Handles authentication, roles, and user metadata.
- **Alerting Service** – Monitors logs for anomalies and stores alerts in Redis.
- **Dashboard Gateway** – Aggregates system metrics and exposes WebSocket/REST endpoints.

## Stack

- Java 21, Spring Boot 3
- RabbitMQ for messaging
- Redis for alert state
- Elasticsearch for search/indexing
- Gradle (Kotlin DSL) with modular subprojects
- Docker for infrastructure
- `.env` based config

# Development Notes
## Ports
- Ingestion service: 5001
- Processing service: 5002
- Authorization Server: 5004
- PostgreSQL: 5432 
- RabbitMQ: 5672 (AMQP) and 15672 (UI).
- Shared logic (e.g., DTOs, utilities) is placed in a `common-lib` module and included as a dependency.
- `.env` file variables are used
