## Commands to test

* Run embedded jetty: `mvn jetty:run`.
* Test service api: `wget -qO- http://127.0.0.1:8080/api/echo`.

## TODO:

Add jersey tests.

### References

* https://stackoverflow.com/questions/25701658/integrating-jersey-2-and-spring-with-java-based-configuration
* https://stackoverflow.com/questions/14722029/how-to-configure-jersey-with-spring-using-only-annotations
* https://www.baeldung.com/jersey-rest-api-with-spring

### Dependencies

```groovy
List testDependencies = [
  "org.springframework:spring-test:${springTest}",
  "javax.el:javax.el-api:${javaxElVersion}",
  "mysql:mysql-connector-java:${mysqlConnectorVersion}",
  "io.rest-assured:rest-assured:${restAssuredVersion}"
]

List jackson = [
  "com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}",
  "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:${jacksonJaxRs}"
]

List jersey = [
  "org.glassfish.jersey.core:jersey-server:${jerseyVersion}",
  "org.glassfish.jersey.media:jersey-media-json-jackson:${jerseyVersion}",
  "org.glassfish.jersey.media:jersey-media-json-processing:${jerseyVersion}",
  "org.glassfish.jersey.media:jersey-media-multipart:${jerseyMediaVersion}",
  "org.glassfish.jersey.ext:jersey-bean-validation:${jerseyVersion}",
  "org.glassfish.jersey.ext:jersey-spring4:${jerseyVersion}"
]

List jerseyTestUtils = [
  "org.glassfish.jersey.test-framework:jersey-test-framework-core:" +
    "${jerseyVersion}",
  "org.glassfish.jersey.test-framework.providers:" +
    "jersey-test-framework-provider-grizzly2:${jerseyVersion}"
]

List security = [
  "logtrust.lib:lugin:${luginVersion}",
  "javax.websocket:javax.websocket-api:${websocketApiVersion}",
  "com.onelogin:java-saml:${javaSamlVersion}"
]

List logger = [
  "org.apache.logging.log4j:log4j-core:${log4j2Version}",
  "org.apache.logging.log4j:log4j-api:${log4j2Version}",
  "org.apache.logging.log4j:log4j-slf4j-impl:${log4j2Version}",
  "org.slf4j:slf4j-api:${slf4jVersion}"
]
```

### Sources:

#### Old test server

```java
public abstract class JerseyServer {

  private static final Logger log = LoggerFactory.getLogger(JerseyServer.class);
  public static final String DEFAULT_CONTEXT_PATH
    = "classpath*:applicationContext.xml";

  private int port = 0;
  private TestContainerFactory factory;
  private ServletDeploymentContext context;
  private TestContainer testContainer;

  public JerseyServer() {

    context = configureDeployment();
    factory = getDefaultTestContainerFactory();
  }

  public void serverStop() {

    log.info("Stopping servlet");
    try {
      TestContainer oldContainer = setTestContainer(null);
      if (oldContainer != null) {
        oldContainer.stop();
      }
    } catch (Exception e) {
      log.warn("Stop failed");
    }
  }

  public void serverStart() {

    log.info("Starting servlet");
    final TestContainer testContainer = createTestContainer(context);

    // Set current instance of test container and start it.
    setTestContainer(testContainer);
    testContainer.start();
  }

  protected abstract Application configure();

  protected URI getBaseUri() {

    return UriBuilder.fromUri("http://localhost/").port(getPort()).build();
  }

  protected int getPort() {

    if (port == 0) {
      port = SocketUtils.findAvailableTcpPort();
    }
    return port;
  }

  protected ServletDeploymentContext configureDeployment() {

    String appClass = configure().getClass().getName();
    String contextPath = getApplicationContextPath();
    log.info("Loading servlet using context path locations: {}", contextPath);

    return ServletDeploymentContext
      .builder(configure())
      .contextParam("contextConfigLocation", contextPath)
      .initParam("javax.ws.rs.Application", appClass)
      .addListener(ContextLoaderListener.class)
      .build();
  }

  protected synchronized TestContainerFactory getDefaultTestContainerFactory() {

    try {
      return GrizzlyWebTestContainerFactory.class.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      log.error("Failed to instance factory");
      return null;
    }
  }

  protected String getApplicationContextPath() {

    return DEFAULT_CONTEXT_PATH;
  }

  private TestContainer createTestContainer(final DeploymentContext context) {

    return factory.create(getBaseUri(), context);
  }

  private TestContainer setTestContainer(final TestContainer testContainer) {

    final TestContainer old = this.testContainer;
    this.testContainer = testContainer;
    return old;
  }
}
```

#### New test server

``` java
@Transactional
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class JerseyDataTest {

  private final Logger log = LoggerFactory.getLogger(JerseyDataTest.class);
  private TestContainer testContainer;

  @Autowired
  protected DataSourceCreateAndDrop dbManager;

  @Configuration
  @ImportResource(locations = { "classpath*:catoblepas-context.xml"})
  @PropertySource(value = "classpath:catoblepas.properties")
  public static class ContextConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourceConfig () {
      return new PropertySourcesPlaceholderConfigurer();
    }
  }

  @BeforeAll
  void start ()
  {
    startServer();
  }

  @AfterAll
  void stop ()
  {
    log.info("Stopping server...");
    testContainer.stop();
    log.info("Server stopped");
  }

  protected String scannedPackages ()
  {
    return getClass().getPackage().getName();
  }

  protected String contextPath ()
  {
    return "/";
  }

  protected int port ()
  {
    return SocketUtils.findAvailableTcpPort();
  }

  protected void startServer ()
  {
    TestContainerFactory factory = new GrizzlyWebTestContainerFactory();
    int port = port();
    RestAssured.port = port;
    URI uri = UriBuilder.fromUri("http://127.0.0.1").port(port).build();
    String contextClass = AnnotationConfigWebApplicationContext.class.getName();

    DeploymentContext context = ServletDeploymentContext
//      .builder(DemoRestConfig.class)
      .forPackages(scannedPackages())
      .contextPath(contextPath())
      .addListener(ContextLoaderListener.class)
      .contextParam(ContextLoader.CONTEXT_CLASS_PARAM, contextClass)
//      .initParam("javax.ws.rs.Application", DemoRestConfig.class.getName())
      .build();

    testContainer = factory.create(uri, context);
    log.info("Starting server...");
    testContainer.start();
  }
}

```
