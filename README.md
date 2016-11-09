# Zooplus task assignment Currency-Converter

[![Build Status](https://travis-ci.org/snazaruk/Zooplus.svg?branch=master)](https://travis-ci.org/snazaruk/Zooplus)

Protected currency converter application using a public currency converter API (https://currencylayer.com/documentation).
The application provids a login/registration screen and a main screen to query historical or current exchange rates.
After the successful login the application shows the last 10 queries and their results on the main screen as reminder.

**Technologies**
- Spring Boot, Web, Security, Data JPA
- HSQLDB as in memory database
- Maven as build tool
- Selenium/Cucumber for acceptance testing

**Building the App**

1. Build

     mvn clean install

2. Start application

		mvn clean spring-boot:run

3. Go to

        http://localhost:8080