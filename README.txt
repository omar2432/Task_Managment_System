
-create an external network so the task and user services can communicate with each other within the docker network.
$ docker network create task-user-network

-This link contains the spring batch schema:
https://github.com/spring-projects/spring-batch/blob/main/spring-batch-core/src/main/resources/org/springframework/batch/core/schema-postgresql.sql

-go to the project folder and run:  docker-compose up -d
-go to http://localhost:8081/
	-login using admin, admin_password
	-create task-managment-realm
	-create role admin
	-create Users (an admin and a regular user):
		- omar.ahmed2432@gmail.com   pass123    (admin)
		- notOmar@gmail.com          pass456

	-Create two clients user-service-client, task-service-client
		-Client authentication   On
		-Authorization           On
		-Authentication flows:
			-Standard flow
			-Direct access grants
			-Service accounts roles

		-Root URL, Valid redirect URIs:   *
		-Web origins:    *

    -add the admin role to the required users and clients (Role Mapping are for users and Service accounts roles are for the clients).