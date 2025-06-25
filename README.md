# Directory Watcher Microservice System

This project is a two-service microservice system developed during my internship at Flytxt. It monitors a directory for new files, reads their contents, and prints them using a second service. The project demonstrates backend development, containerization, inter-service communication, and deployment using production-level tools.

---

## Overview

- **File Reader Service**: Continuously watches a specified directory and reads the contents of newly added files.
- **File Printer Service**: Receives file content from the reader service and prints it to logs.

Initially, the services communicated via HTTP. This was later upgraded to Apache Kafka for decoupled and asynchronous messaging.

---

## Tech Stack

| Component             | Tools / Technologies                                |
|-----------------------|-----------------------------------------------------|
| Language              | Java                                                |
| Framework             | Spring Boot                                         |
| Build Tool            | Maven (custom `pom.xml` configurations)            |
| Communication         | HTTP (initial), Apache Kafka (final)               |
| Containerization      | Docker                                              |
| Orchestration         | Apache Mesos, Marathon                             |
| Deployment Platform   | Remote Linux server via SSH                        |
| File Transfer         | FileZilla                                           |
| Config Files          | YAML (application config), JSON (deployment specs) |
| IDE                   | Eclipse                                             |

---

