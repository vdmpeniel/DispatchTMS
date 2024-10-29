### Data Source issue:
    When you first create an app if you added a DB related to your Spring Initializr
    https://start.spring.io/

#### Method 1: Disable data source auto-configuration using application properties
    Add the following property to your application.properties file:
    spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
    Alternatively, if you're using a application.yml file, add the following property:
        spring:
            autoconfigure:
                exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

#### Method 2: Disable data source auto-configuration using Java configuration
    Add the following annotation to your main application class:

    @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
    public class TrucktrackingApplication {
        //...
    }

#### Of course, we can simply remove the dependency in gradle or maven

### Controllers:
    This is the api endpoint: http://localhost:8081/api/v1/get_status