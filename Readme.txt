Installation requirements:
- IntelliJ IDE 2022.1.1 with openjdk 18
- MySql workbench 8.0.26
- MariaDB 10.6 
- VS Code 1.68.1
- Vue 3

How to install Vue 3:
1) Install Node.js
	- Tutorial for windows: https://docs.microsoft.com/en-us/windows/dev-environment/javascript/nodejs-on-windows 
2) Install Vue 3 and Vue CLI
	- Tutorial for windows: https://docs.microsoft.com/en-us/windows/dev-environment/javascript/vue-on-windows


Step 1:
How to setup the database: 
1) Create new database named: diabetesdb
2) Run all provided sql scripts in folder DatabasePopulation except Notes.sql (Notes.sql is only for testing purposes) to populate the database.

Step 2:
How to open the project in IntelliJ:
1) Open Diabetes project in IntelliJ
2) Go to folder src -> main -> resources and open application.properties
3) Set spring.datasource.password= To the password of your database
	- Make sure that .username and .url refers to your database
4) Go to folder src -> main -> java -> diabetes and run Application.java

Step 3:
How to use Vue 3:
1) Open the Vue folder inside the Diabetes project in VS Code
2) Open a new terminal in VS code
3) Type: vue ui
4) Go to dependencies(afhængigheder) and update all dependencies.
5) Go to tasks (opgaver) -> serve -> run task (kør opgave)
