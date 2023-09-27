This project is to demo how to refresh the context when properties change in the Azure Key Vault. 

The Azure Key Vault starter has an internal refresh logic, which will periodically call the KV server to fetch the latest secrets. However, the changes won't be propagated to the context. For example, if a MySQL password is stored in the Azure Key Vault and the secret is rotated periodically, the latest secret will be fetched into the application, but the `DataSource` bean won't be reconstructed.

## Solution 1

The idea of the refresh is to call the refresh endpoint, to notify the application context to perform a refresh. To do so, users need to define their own `DataSource` bean and annotate it with the `@RefreshScope` annotation like:

```java
@RefreshScope
@Bean
public DataSource myDataSource(@Value("${spring.datasource.url}") String url,
                               @Value("${spring.datasource.username}") String username,
                               @Value("${spring.datasource.password}") String password) {

    return DataSourceBuilder.create()
            .url(url)
            .password(password)
            .username(username)
            .build();
}
```

And in the pom.xml include the actuator dependency:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

At the same time, enable the actuator endpoint accordingly as well as disable the kv refresh:

```properties
management.endpoints.web.exposure.include=refresh
spring.cloud.azure.keyvault.secret.property-sources[0].refresh-interval=0
```

### Pros and Cons of this solution

With this approach, there's no polling in the application. However, it requires the external system to notify it, which could be automatically or manually. 

### Improve the solution
One variant of this solution is to enable the kv refresh, and use a background task to check whether the property has changed. If any value changes, publish a `RefreshEvent` to trigger the refresh like:

```java
private ApplicationEventPublisher publisher;

public void someMethod() {
    if (secretChanges) {
        publisher.publishEvent(new RefreshEvent(this, "kv refresh event", "The kv secret has changed."))
    }
}
```

## Solution 2

Switch to the Azure App Configuration starter, which also has the KV integration. The App Configuration starter has integration with Spring Cloud, which can do a refresh in both pull and push fashions. But extra cost will be needed. 
