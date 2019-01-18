# shoppingcart-cloud-native
This project provides a basic idea about how to build an application that adheres to microservices architecture from scratch

1- In order to run this application you have to have installed locally:

1.1) A build tool (in this case, Apache Maven

1.2) Pivotal Cloud Foundry CLI (to push your application into PCF environment)

2- Register to create a trial account at https://console.run.pivotal.io 

2.1) Once you have created the account and logged in to your workspace you have to create a Mysql instance as a service in PCF environment (The service instance name must be exactly the same one provided in the 'manifest.yml' file);

3- Push your application into Pivotal Cloud Foundry environment

3.1) cf login -a api.run.pivotal.io
3.2) cf push
