## Mars Temperature 
Preoday Java developer test using NASA’s Mars Weather api.

### Frameworks and Libraries

- Spring Boot is an open source Java-based framework used to create an auto configurable production-grade Spring application.
- JSON-Java or org.json is a simple Java based toolkit for JSON.

### Build and Deploy the Project

You can build the project using maven.

```
mvn clean install
```

And, you can deploy it by using the main class: `MarsTemperatureApplication.java`

```
java -jar preoday-mars-temperature-1.0-SNAPSHOT.jar MarsTemperatureApplications.java
```

Once deployed, you can simply access the app at: http://localhost:8080

### Endpoints

##### GET `/nasa/temperature`
```
{
    "averageTemperature": 1234.56
}
```