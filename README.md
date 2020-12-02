# SOAP Web Service

## Web Service

**Web Service**: is a software system designed to support interoperable machine-to-machine interaction over a network. (W3C)

![](https://github.com/shamy1st/java-soap/blob/main/images/web-service.png)

## Service Definition

![](https://github.com/shamy1st/java-soap/blob/main/images/service-definition.png)

## Terminology

* Request and Response
* Message Exchange Format (XML, JSON)
* Service Provider or Server
* Service Consumer or Client
* Service Definition
* Transport (HTTP, MQ)

![](https://github.com/shamy1st/java-soap/blob/main/images/mq-example.png)

## SOAP Introduction

**SOAP**: Simple Object Access Protocol

![](https://github.com/shamy1st/java-soap/blob/main/images/soap-envelope.png)
![](https://github.com/shamy1st/java-soap/blob/main/images/soap-response-example.png)
![](https://github.com/shamy1st/java-soap/blob/main/images/soap-wsdl.png)
![](https://github.com/shamy1st/java-soap/blob/main/images/soap-wsdl-definition.png)

## REST API Introduction

**REST**: REpresentational state transfer.

* **Data Exchange**: JSON is popular (but no restriction).
* **Transport**: only HTTP.
* **Service Definition**: WADL, Swagger (no standard).

![](https://github.com/shamy1st/java-soap/blob/main/images/rest-http.png)

## SOAP

### 1. Request: request.xml

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseRequest xmlns="http://shamy1st.com/courses"
                        xsi:schemaLocation="http://shamy1st.com/courses course.xsd"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <id>123</id>
        </GetCourseRequest>

### 2. Response: response.xml

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseResponse xmlns="http://shamy1st.com/courses"
                        xsi:schemaLocation="http://shamy1st.com/courses course.xsd"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <Course>
                <id>123</id>
                <name>Spring Boot with SOAP</name>
                <description>Basics of SOAP web service.</description>
            </Course>
        </GetCourseResponse>

### 3. XSD Validation: course.xsd
http://edutechwiki.unige.ch/en/XML_Schema_tutorial_-_Basics

        <?xml version="1.0"?>
        <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://shamy1st.com/courses"
                xmlns:tns="http://shamy1st.com/courses"
                elementFormDefault="qualified">

            <!-- request validation -->
            <xs:element name="GetCourseRequest">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="id" type="xs:int"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- response validation -->
            <xs:element name="GetCourseResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="Course" type="tns:Course" />
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <xs:complexType name="Course">
                <xs:sequence>
                    <xs:element name="id" type="xs:int"/>
                    <xs:element name="name" type="xs:string"/>
                    <xs:element name="description" type="xs:string"/>
                </xs:sequence>
            </xs:complexType>
        </xs:schema>

### 4. JAXB: (Java Architecture for XML Binding)

**JAXB**: Java Architecture for XML Binding

4.1 copy "course.xsd" to resources dirctory into your project.

4.2 add jaxb2 plugin to pom.xml (only version 1.5 works for me)

https://www.mojohaus.org/jaxb2-maven-plugin/Documentation/v2.4/example_xjc_basic.html

    <plugins>
        ...
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>jaxb2-maven-plugin</artifactId>
            <version>1.5</version>
            <executions>
                <execution>
                    <id>xjc</id>
                    <goals>
                        <goal>xjc</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <schemaDirectory>${project.basedir}/src/main/resources</schemaDirectory>
                <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                <clearOutputDir>false</clearOutputDir>
            </configuration>
        </plugin>
    </plugins>

4.3 maven update & execute "./mvnw clean install"

4.4 then java files will be generated under "com.shamy1st.courses"


### 5. Endpoint:

        @Endpoint
        public class CourseEndpoint {

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCourseRequest")
            @ResponsePayload
            public GetCourseResponse processRequest(@RequestPayload GetCourseRequest request) {
                GetCourseResponse response = new GetCourseResponse();
                Course course = new Course();
                course.setId(request.getId());
                course.setName("Spring Boot with SOAP");
                course.setDescription("Basics of SOAP web service.");
                response.setCourse(course);
                return response;
            }
        }

### 6. Web Service Configuration & Generate WSDL

        @EnableWs
        @Configuration
        public class WebServiceConfig {

            @Bean
            public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
                MessageDispatcherServlet message = new MessageDispatcherServlet();
                message.setApplicationContext(context);
                message.setTransformWsdlLocations(true);
                return new ServletRegistrationBean(message, "/ws/*");
            }

            @Bean(name="courses")
            public DefaultWsdl11Definition wsdlDefinition(XsdSchema coursesSchema) {
                DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
                wsdl.setPortTypeName("CoursePort");
                wsdl.setTargetNamespace("http://shamy1st.com/courses");
                wsdl.setLocationUri("/ws");
                wsdl.setSchema(coursesSchema);
                return wsdl;
            }

            @Bean
            public XsdSchema courseSchema() {
                return new SimpleXsdSchema(new ClassPathResource("course.xsd"));
            }
        }

### 7. pom.xml dependencies

* **Spring Web Services**

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web-services</artifactId>
        </dependency>

* **Spring Data JPA**

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

* **wsdl4j**

        <dependency>
            <groupId>wsdl4j</groupId>
            <artifactId>wsdl4j</artifactId>
        </dependency>

### 8. Run Application

### 9. Invoke Web Service using Wizdler

1. add wizdler plugin for chrome or firefox.

2. open url "http://localhost:8080/ws/courses.wsdl" in the browser.

3. click "GetCourse" from wizdler icon in the browser.

4. it will open a new tab with xml request -> then write any "id" and click Go.

        <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
            <Body>
                <GetCourseRequest xmlns="http://shamy1st.com/courses">
                    <id>1003</id>
                </GetCourseRequest>
            </Body>
        </Envelope>

5. Now you have a xml response in the browser like that:

        <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
            <SOAP-ENV:Header/>
            <SOAP-ENV:Body>
                <ns2:GetCourseResponse xmlns:ns2="http://shamy1st.com/courses">
                    <ns2:Course>
                        <ns2:id>1003</ns2:id>
                        <ns2:name>Spring Boot with SOAP</ns2:name>
                        <ns2:description>Basics of SOAP web service.</ns2:description>
                    </ns2:Course>
                </ns2:GetCourseResponse>
            </SOAP-ENV:Body>
        </SOAP-ENV:Envelope>

### 10. Fetch From DB (H2)

1. Modify Endpoint

        @Endpoint
        public class CourseEndpoint {

            @Autowired
            private CourseService service;

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCourseRequest")
            @ResponsePayload
            public GetCourseResponse processRequest(@RequestPayload GetCourseRequest request) {
                GetCourseResponse response = new GetCourseResponse();
                Optional<Course> optCourse = service.findById(request.getId());
                if(optCourse.isPresent()) {
                    com.shamy1st.courses.Course course = new com.shamy1st.courses.Course();
                    course.setId(optCourse.get().getId());
                    course.setName(optCourse.get().getName());
                    course.setDescription(optCourse.get().getDescription());
                    response.setCourse(course);
                }
                return response;
            }
        }

2. Entity, Repository, Service

        @Service
        public class CourseService {

            @Autowired
            private CourseRepository repository;

            public Optional<Course> findById(Integer id) {
                return repository.findById(id);
            }
        }

        public interface CourseRepository extends JpaRepository<Course, Integer> {

        }

        @Entity
        public class Course {

            @Id
            private int id;
            private String name;
            private String description;

            public Course() {

            }

            public Course(int id, String name, String description) {
                this.id = id;
                this.name = name;
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            @Override
            public String toString() {
                return "Course{ id:" + id + ", name:'" + name + "', description:'" + description + "'}";
            }
        }

3. application.properties (H2)

        spring.datasource.url=jdbc:h2:mem:testdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=password
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        spring.jpa.show-sql=true
        spring.h2.console.enable=true

4. data.sql (H2)

        INSERT INTO course (id, name, description) VALUES (10001, 'course1', 'description1');
        INSERT INTO course (id, name, description) VALUES (10002, 'course2', 'description2');
        INSERT INTO course (id, name, description) VALUES (10003, 'course3', 'description3');

5. test again like **step-9**

### 11. Get All Courses

1. **Modify XSD**

        <?xml version="1.0"?>
        <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://shamy1st.com/courses"
                xmlns:tns="http://shamy1st.com/courses"
                elementFormDefault="qualified">

            <!-- request validation -->
            <xs:element name="GetCourseRequest">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="id" type="xs:int"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- response validation -->
            <xs:element name="GetCourseResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="Course" type="tns:Course" />
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- request validation -->
            <xs:element name="GetCoursesRequest">
                <xs:complexType></xs:complexType>
            </xs:element>

            <!-- response validation -->
            <xs:element name="GetCoursesResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="Course" type="tns:Course" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <xs:complexType name="Course">
                <xs:sequence>
                    <xs:element name="id" type="xs:int"/>
                    <xs:element name="name" type="xs:string"/>
                    <xs:element name="description" type="xs:string"/>
                </xs:sequence>
            </xs:complexType>
        </xs:schema>

2. **Modify CourseService**

        @Service
        public class CourseService {

            @Autowired
            private CourseRepository repository;

            public Optional<Course> findById(Integer id) {
                return repository.findById(id);
            }

            public List<Course> findAll() {
                return repository.findAll();
            }
        }

3. **Modify Endpoint**

        @Endpoint
        public class CourseEndpoint {

            @Autowired
            private CourseService service;

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCourseRequest")
            @ResponsePayload
            public GetCourseResponse getCourse(@RequestPayload GetCourseRequest request) {
                Optional<Course> optional = service.findById(request.getId());
                GetCourseResponse response = new GetCourseResponse();
                optional.ifPresent(course -> response.setCourse(mapCourse(optional.get())));
                return response;
            }

            private com.shamy1st.courses.Course mapCourse(Course course) {
                com.shamy1st.courses.Course courseResponse = new com.shamy1st.courses.Course();
                courseResponse.setId(course.getId());
                courseResponse.setName(course.getName());
                courseResponse.setDescription(course.getDescription());
                return courseResponse;
            }

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCoursesRequest")
            @ResponsePayload
            public GetCoursesResponse getCourses(@RequestPayload GetCoursesRequest request) {
                List<Course> courses = service.findAll();
                return mapCourses(courses);
            }

            private GetCoursesResponse mapCourses(List<Course> courses) {
                List<com.shamy1st.courses.Course> coursesResponse = new ArrayList<>();
                courses.forEach(course -> coursesResponse.add(mapCourse(course)));
                GetCoursesResponse response = new GetCoursesResponse();
                response.getCourse().addAll(coursesResponse);
                return response;
            }
        }

### 12. WSDL

WSDL contains all information that client need about the web service.

http://localhost:8080/ws/courses.wsdl

![](https://github.com/shamy1st/java-soap/blob/main/images/wsdl-example.png)

### 13. Delete Course

1. **Modify XSD**

        <?xml version="1.0"?>
        <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://shamy1st.com/courses"
                xmlns:tns="http://shamy1st.com/courses"
                elementFormDefault="qualified">

            <!-- request course validation -->
            <xs:element name="GetCourseRequest">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="id" type="xs:int"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- response course validation -->
            <xs:element name="GetCourseResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="Course" type="tns:Course" />
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- request courses validation -->
            <xs:element name="GetCoursesRequest">
                <xs:complexType></xs:complexType>
            </xs:element>

            <!-- response courses validation -->
            <xs:element name="GetCoursesResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="Course" type="tns:Course" maxOccurs="unbounded"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- request delete course validation -->
            <xs:element name="DeleteCourseRequest">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="id" type="xs:int"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- response delete course validation -->
            <xs:element name="DeleteCourseResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="status" type="xs:boolean" />
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <xs:complexType name="Course">
                <xs:sequence>
                    <xs:element name="id" type="xs:int"/>
                    <xs:element name="name" type="xs:string"/>
                    <xs:element name="description" type="xs:string"/>
                </xs:sequence>
            </xs:complexType>
        </xs:schema>

2. **Modify CourseService**

        @Service
        public class CourseService {

            @Autowired
            private CourseRepository repository;

            public Optional<Course> findById(Integer id) {
                return repository.findById(id);
            }

            public List<Course> findAll() {
                return repository.findAll();
            }

            public boolean deleteById(Integer id) {
                Optional<Course> optional = findById(id);
                if(optional.isPresent()) {
                    repository.deleteById(id);
                    return true;
                }
                return false;
            }
        }

3. **Modify Endpoint**

        @Endpoint
        public class CourseEndpoint {

            @Autowired
            private CourseService service;

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCourseRequest")
            @ResponsePayload
            public GetCourseResponse getCourse(@RequestPayload GetCourseRequest request) {
                Optional<Course> optional = service.findById(request.getId());
                GetCourseResponse response = new GetCourseResponse();
                optional.ifPresent(course -> response.setCourse(mapCourse(course)));
                return response;
            }

            private com.shamy1st.courses.Course mapCourse(Course course) {
                com.shamy1st.courses.Course courseResponse = new com.shamy1st.courses.Course();
                courseResponse.setId(course.getId());
                courseResponse.setName(course.getName());
                courseResponse.setDescription(course.getDescription());
                return courseResponse;
            }

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="GetCoursesRequest")
            @ResponsePayload
            public GetCoursesResponse getCourses(@RequestPayload GetCoursesRequest request) {
                List<Course> courses = service.findAll();
                return mapCourses(courses);
            }

            private GetCoursesResponse mapCourses(List<Course> courses) {
                List<com.shamy1st.courses.Course> coursesResponse = new ArrayList<>();
                courses.forEach(course -> coursesResponse.add(mapCourse(course)));
                GetCoursesResponse response = new GetCoursesResponse();
                response.getCourse().addAll(coursesResponse);
                return response;
            }

            @PayloadRoot(namespace="http://shamy1st.com/courses", localPart="DeleteCourseRequest")
            @ResponsePayload
            public DeleteCourseResponse deleteCourse(@RequestPayload DeleteCourseRequest request) {
                DeleteCourseResponse response = new DeleteCourseResponse();
                response.setStatus(service.deleteById(request.getId()));
                return response;
            }
        }

### 14. Improve Delete Response (Enum)

use Enum (Success or Fail) as a response instead of return (true/false).




