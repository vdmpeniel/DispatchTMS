# SPRING BOOT NOTES
# Data Source issue:
When you first create an app if you added a DB related to your Spring Initializr
https://start.spring.io/

### Method 1: Disable data source auto-configuration using application properties
Add the following property to your application.properties file:
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
Alternatively, if you're using a application.yml file, add the following property:
    spring:
        autoconfigure:
            exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration

### Method 2: Disable data source auto-configuration using Java configuration
Add the following annotation to your main application class:

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TrucktrackingApplication {
    //...
}

### Simply remove dependency:
Of course, we can simply remove the dependency in gradle or maven


# Controllers:
Creating a base endpoint:
@RequestMapping("/api/v1/")
@GetMapping(
    value = "get_status",
    produces = MediaType.APPLICATION_JSON_VALUE
)
This is the api endpoint: http://localhost:8081/api/v1/get_status


## Methods' Mappings:
@PostMapping("/users")
@PostMapping("/users")
@PutMapping("/users/{id}")
@DeleteMapping("/users/{id}")

## Caching Responses: (Needs Explanation)
You can use the @GetMapping annotation to control caching of the response. For example, you can use the @Cacheable annotation to cache the response for a certain amount of time.
@GetMapping("/users")
@Cacheable(value = "users", key = "#id", unless = "#result.isEmpty()")
public List<User> getUsers(@PathVariable Long id) {
    //...
}

## ETag: (Needs Explanation)
You can use the @GetMapping annotation to set an ETag (Entity Tag) for the response. An ETag is a unique identifier for the response that can be used to cache the response.
@GetMapping("/users")
public ResponseEntity<List<User>> getUsers() {
List<User> users = //...
String etag = //...
return ResponseEntity.ok().eTag(etag).body(users);
}

## Last Modified: 
You can use the @GetMapping annotation to set the Last Modified date for the response.
@GetMapping("/users")
public ResponseEntity<List<User>> getUsers() {
    List<User> users = //...
    Date lastModified = //...
    return ResponseEntity.ok().lastModified(lastModified).body(users);
}


## Produces and Consumes: 
You can use the produces and consumes attributes of @GetMapping to specify the media type of 
the response body and the request body, respectively.
@GetMapping(
    value = "/users",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE

)


## Params: 
You can use the params attribute of @GetMapping to specify the query parameters that 
must be present in the request.
@GetMapping(value = "/users", params = "name")


## Headers: 
You can specify the headers that must be present in the request using the headers attribute.
@GetMapping(value = "/users", headers = "Accept=application/json")

### You can also get the header values like this
public class MyController {
    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader("Accept") String acceptHeader) {
        //...
    }
}

### Headers' values to Map:
@RestController
public class MyController {
    @GetMapping("/users")
    public List<User> getUsers(@RequestHeaders Map<String, String> headers) {
        //...
    }
}

### Using the @ResponseHeaders annotation: 
You can use the @ResponseHeaders annotation to set the headers in the response.
@GetMapping("/users")
@ResponseHeaders({
    @ResponseHeader(name = "Content-Type", value = "application/json"),
    @ResponseHeader(name = "Accept", value = "application/json")
})
public List<User> getUsers() {
    //...
}

### Using the HttpServletRequest or HttpServletResponse object: 
You can use the HttpServletRequest object to get the headers from the request.
@RestController
public class MyController {
    @GetMapping("/users")
    public List<User> getUsers(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
    }

    @GetMapping("/employees")
    public List<User> getEmployees(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        //...
    }
}

### Using the ResponseEntity class: 
You can use the ResponseEntity class to set the headers in the response.
@GetMapping("/users")
public ResponseEntity<List<User>> getUsers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return ResponseEntity.ok().headers(headers).body(users);
}

Or, you can use the ResponseEntity class like this:

@GetMapping("/users")
public ResponseEntity<List<User>> getUsers() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Accept", "application/json");
    headers.add("Reason", "Users retrieved successfully");
    return ResponseEntity.ok().headers(headers).body(users);

    // return ResponseEntity.status(HttpStatus.OK).headers(headers).body(users);
}



### Using the @ResponseStatus annotation: 
You can use the @ResponseStatus annotation to set the status code and headers in the response.
@GetMapping("/users")
@ResponseStatus(
    HttpStatus.OK,
    reason = "Users retrieved successfully"
)
public List<User> getUsers() {
    //...
}
### End Headers

## Cross-Origin Resource Sharing (CORS)
You can use the @GetMapping annotation to enable CORS for the endpoint.
@GetMapping("/users")
@CrossOrigin(origins = "http://example.com")
public List<User> getUsers() {
    //...
}

### Security: 
You can use the @GetMapping annotation to secure the endpoint. 
For example, you can use the @Secured annotation to restrict access to the endpoint.
@GetMapping("/users")
@Secured("ROLE_ADMIN")
public List<User> getUsers() {
    //...
}




## Give the mapping a name:
You can specify a name for the mapping using the name attribute.
@RestController
public class MyController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    
    @GetMapping(value = "/users", name = "getAllUsers")
    public List<User> getUsers() {
        String mappingName = requestMappingHandlerMapping.getMappingNameForMethod(getUsers());
        System.out.println("Mapping Name: " + mappingName);
        //...
    }
}

### Using @Value instead:
@RestController
public class MyController {
    @Value("${spring.mvc.mapping.name}")
    private String mappingName;
    
    @GetMapping(value = "/users", name = "getAllUsers")
    public List<User> getUsers() {
        System.out.println("Mapping Name: " + mappingName);
        //...
    }
}

### Here are a few scenarios where the name attribute can be useful:
* Logging: You can use the name attribute to log the mapping name in your application logs. This can help you identify which mapping is being called.
* Debugging: You can use the name attribute to set a breakpoint in your code and see which mapping is being called.
* Testing: You can use the name attribute to test your mappings. For example, you can use the MockMvc class to test your mappings and verify that the correct mapping is being called.
* Documentation: You can use the name attribute to document your mappings. For example, you can use the name attribute to specify a description of the mapping.
* API Documentation: If you are using a tool like Swagger or API Gateway to generate API documentation, the name attribute can be used to specify the name of the API endpoint.



## Getting GET query params:

### Multiple Paths:
@GetMapping({"/users", "/users/list"})
public List<User> getUsers() {
    //...
}

### Multiple Mappings:
@GetMapping("/users")
@GetMapping("/users/list")
public List<User> getUsers() {
    //...
}


### Taking parameters from a get query:
@GetMapping("/users")
public List<User> getUsers(@RequestParam("name") String name, @RequestParam("age") int age) {
    // Use the name and age parameters to retrieve users from the database
    return userRepository.findByNameAndAge(name, age);
}


### Take params from a query like this and add default values:
/api/v1/users?name=John&age=30

@GetMapping("/users")
public List<User> getUsers(@RequestParam(defaultValue = "John") String name, @RequestParam(defaultValue = "30") int age) {
    //...
}


### Make the query parameters optional:
@GetMapping("/users")
public List<User> getUsers(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "age", required = false) Integer age) {
    //...
}


### Bind query parameters to a Map:
GET /users?name=John&age=30&location=New+York

@GetMapping("/users")
public List<User> getUsers(@RequestParam Map<String, String> queryParams) {
    // Use the queryParams map to retrieve the query parameters
    String name = queryParams.get("name");
    int age = Integer.parseInt(queryParams.get("age"));
    //...
}


### Bind query parameters to a POJO (Plain Old Java Object):
GET /users?name=John&age=30

public class UserQuery {
    private String name;
    private int age;
    // Getters and setters
}

@GetMapping("/users")
public List<User> getUsers(@RequestParam UserQuery userQuery) {
    // Use the userQuery object to retrieve the query parameters
    String name = userQuery.getName();
    int age = userQuery.getAge();
    //...
}


### Getting values from the URL's path:
GET /users/John

@GetMapping("/users/{username}")
public String getUser(@PathVariable("username") String username) {
    // Use the username variable to retrieve the user data
    return "Hello, " + username;
}


### Use path variables with default values: 
You can use path variables with default values using the @GetMapping annotation.
@GetMapping("/users/{id:100}")
public User getUser(@PathVariable Long id) {
    //...
}


### Multiple values from the path:
GET /users/John/posts/123

@GetMapping("/users/{username}/posts/{postId}")
public String getPost(@PathVariable("username") String username, @PathVariable("postId") int postId) {
    // Use the username and postId variables to retrieve the post data
    return "Post " + postId + " of user " + username;
}


### Path values to an array:
GET /users/John,Alice,Bob

@GetMapping("/users/{usernames}")
public String getUsers(@PathVariable("usernames") String[] usernames) {
    // Use the usernames array to retrieve the user data
    return "Users: " + Arrays.toString(usernames);
}


### Path Values to a Map
GET /users/John/posts?author=Alice&category=Technology

@GetMapping("/users/{username}/posts")
public String getPosts(@PathVariable("username") String username, @PathVariable Map<String, String> queryParams) {
    // Use the username variable and queryParams map to retrieve the post data
    return "Posts of user " + username + " with query parameters: " + queryParams;
}

### Specify a path with regex:
You can specify a path with regex using the @GetMapping annotation.
@GetMapping("/users/{id:[0-9]+}")
public User getUser(@PathVariable Long id) {
    //...
}

### Specify a path with multiple matrix variables: 
You can specify a path with multiple matrix variables using the @GetMapping annotation.
@GetMapping("/users/{id};name={name};age={age}")
public User getUser(@PathVariable Long id, @MatrixVariable String name, @MatrixVariable Integer age) {
    //...
}

### Use matrix variables with default values: 
You can use matrix variables with default values using the @GetMapping annotation.
@GetMapping("/users/{id};name={name:John}")
public User getUser(@PathVariable Long id, @MatrixVariable String name) {
    //...
}

### Use matrix variables with regular expressions: 
You can use matrix variables with regular expressions using the @GetMapping annotation.
@GetMapping("/users/{id};name={name:[a-zA-Z]+}")
public User getUser(@PathVariable Long id, @MatrixVariable String name) {
    //...
}

### Use request attribute: (Needs Explanation)
You can use request attribute to pass data to the method using the @GetMapping annotation.
@GetMapping("/users")
public User getUser(@RequestAttribute("attribute") String attribute) {
    //...
}

### Use session attribute: (Needs Explanation)
You can use session attribute to pass data to the method using the @GetMapping annotation.
@GetMapping("/users")
public User getUser(@SessionAttribute("attribute") String attribute) {
    //...
}

### Use application attribute: (Needs Explanation)
You can use application attribute to pass data to the method using the @GetMapping annotation.
@GetMapping("/users")
public User getUser(@ApplicationAttribute("attribute") String attribute) {
    //...
}



## Getting POST query params:
### Get the body of a POST request in Spring:
you can use the @RequestBody annotation on a method parameter to get the body into an object instance

@PostMapping("/users")
public User createUser(@RequestBody User user) {
    // user is the body of the request
}

### Get the raw request body as a string:
@PostMapping("/users")
public User createUser(@RequestBody String requestBody) {
// requestBody is the raw request body as a string
}

### Using HttpServletRequest to get the body content
@PostMapping("/users")
public User createUser(HttpServletRequest request) {
String requestBody = request.getReader().readLine();
    // requestBody is the raw request body as a string
}

### Validation: 
You can use the @Valid annotation to validate the parameters of the @PostMapping method. For example:
@PostMapping("/users")
public User createUser(@Valid @RequestBody User user) {
    //...
}

### ExceptionHandler: 
You can use the @ExceptionHandler annotation to handle exceptions 
that occur during the execution of the @PostMapping method. 
For example:
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    //...
}

@ExceptionHandler
public ResponseEntity<String> handleException(Exception e) {
    //...
}

@ExceptionHandler(NotFoundException.class)
public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    //...
}

@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    //...
}




## HTTP Method override:
Use HTTP method override: You can use HTTP method override to change the HTTP method of the request using the @GetMapping annotation.
@GetMapping("/users")
public User getUser(HttpServletRequest request) {
String method = request.getMethod();
    if (method.equals("POST")) {
        //...
    }
}


# Logging:
We can use Slf4j, Log4j, or Logback

## Logging using a Logger instance:
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClass {
private static final Logger logger = LoggerFactory.getLogger(MyClass.class);

    public void myMethod() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
    }
}

## Logging with Lombok's @Slf4j
You are going to need the dependency for project Lombok
Use annotation @Slf4j
log.info("Doing something...");
log.debug("This is a debug message.");
log.warn("This is a warning message.");
log.error("This is an error message.");
log.fatal("This is a fatal message.");

## Configure the logging settings
Put your configuration files in src/main/java/resources

### Using Slf4j.properties:
slf4j.rootLogger=DEBUG, CONSOLE
slf4j.appender.CONSOLE=org.slf4j.impl.ConsoleAppender
slf4j.appender.CONSOLE.layout=org.slf4j.impl.PatternLayout
slf4j.appender.CONSOLE.layout.ConversionPattern=%date %level %logger %message%n

### Using Slf4j.xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <appender name="console" class="org.slf4j.impl.SLF4JLoggerFactory$ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
    </layout>
  </appender>
  <root level="DEBUG">
    <appender-ref ref="console" />
  </root>
</configuration>


### Using log4j.properties
log4j.rootLogger=DEBUG, CONSOLE
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%date %level %logger %message%n

### Using log4j.xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration>
    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <layout class="org.apache.log4j.PatternLayout">
        <param name="ConversionPattern" value="%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n" />
        </layout>
    </appender>
    <root>
        <level value="DEBUG" />
        <appender-ref ref="console" />
    </root>
</log4j:configuration>


### Using logback.xml
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%date %level %logger %message%n</pattern>
        </layout>
    </appender>
    <root level="DEBUG">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>

###  Configure logback to log messages to multiple destinations using multiple appenders. Here is an example:
<configuration>
  <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
    </layout>
  </appender>
  <appender name="file" class="ch.qos.logback.core.FileAppender">
    <file>log.txt</file>
    <layout class="ch.qos.logback.classic.PatternLayout">
      <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
    </layout>
  </appender>
  <root level="DEBUG">
    <appender-ref ref="console" />
    <appender-ref ref="file" />
  </root>
</configuration>

### Configure logback to log messages at different levels using different loggers. Here is an example:
<configuration>
  <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
    </layout>
  </appender>
  <logger name="com.example" level="INFO">
    <appender-ref ref="console" />
  </logger>
  <logger name="com.example.subpackage" level="DEBUG">
    <appender-ref ref="console" />
  </logger>
  <root level="DEBUG">
    <appender-ref ref="console" />
  </root>
</configuration>

### Configure logback to log messages using different layouts. Here is an example:
<configuration>
  <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
    </layout>
  </appender>
  <appender name="file" class="ch.qos.logback.core.FileAppender">
    <file>log.txt</file>
    <layout class="ch.qos.logback.classic.Pattern


# Using @Builder
To be able to return JSON formated builder you need to add getters and setters

# Jackson object mapping
Include only non-null                                                                                               properties:
ObjectMapper mapper = new ObjectMapper();
System.out.println(mapper.writeValueAsString(response));











### Other Annotations:
@ExceptionHandler to handle exceptions.
@InitBinder to initialize the binder.
@ModelAttribute to bind the model attribute.
@SessionAttributes to set the session attributes.
@RequestPart to get the request part.
@RequestParts to get the request parts.
@ExceptionHandler to handle exceptions.
@ResponseBody to set the response body.
@ResponseStatusException to set the response status exception.
@RestControllerAdvice to mark the class as a rest controller advice.
@ModelAttribute to bind the model attribute.
@SessionAttributes to set the session attributes.
@RequestPart to get the request part.
@RequestParts to get the request parts.
@ExceptionHandler to handle exceptions.
@ResponseBody to set the response body.
@ResponseStatusException to set the response status exception.
@RestController to mark the class as a rest controller.
@RestControllerAdvice to mark the class as a rest controller advice.
@ModelAttribute to bind the model attribute.
@SessionAttributes to set the session attributes.
@RequestPart to get the request part.
@RequestParts to get the request parts.
@ExceptionHandler to handle exceptions.
@ResponseStatus to set the response status.
@ResponseBody to set the response body.
@ConditionalOnProperty: This annotation is used to conditionally include a bean based on a property.
@ConditionalOnClass: This annotation is used to conditionally include a bean based on the presence of a class.
@ConditionalOnBean: This annotation is used to conditionally include a bean based on the presence of another bean.
@ConditionalOnMissingBean: This annotation is used to conditionally include a bean based on the absence of another bean.
