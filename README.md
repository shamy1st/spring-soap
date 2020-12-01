# SOAP Web Service

### Web Service

**Web Service**: is a software system designed to support interoperable machine-to-machine interaction over a network. (W3C)

![](https://github.com/shamy1st/java-soap/blob/main/images/web-service.png)

### Service Definition

![](https://github.com/shamy1st/java-soap/blob/main/images/service-definition.png)

### Terminology

* Request and Response
* Message Exchange Format (XML, JSON)
* Service Provider or Server
* Service Consumer or Client
* Service Definition
* Transport (HTTP, MQ)

![](https://github.com/shamy1st/java-soap/blob/main/images/mq-example.png)

### SOAP Introduction

**SOAP**: Simple Object Access Protocol

![](https://github.com/shamy1st/java-soap/blob/main/images/soap-envelope.png)
![](https://github.com/shamy1st/java-soap/blob/main/images/soap-response-example.png)
![](https://github.com/shamy1st/java-soap/blob/main/images/soap-wsdl.png)
![](https://github.com/shamy1st/java-soap/blob/main/images/soap-wsdl-definition.png)

### REST API Introduction

**REST**: REpresentational state transfer.

* **Data Exchange**: JSON is popular (but no restriction).
* **Transport**: only HTTP.
* **Service Definition**: WADL, Swagger (no standard).

![](https://github.com/shamy1st/java-soap/blob/main/images/rest-http.png)

### SOAP

1. **Request**: request.xml

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseRequest xmlns="http://www.shamy1st.com/courses"
                        xsi:schemaLocation="http://www.shamy1st.com/courses course.xsd"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <id>123</id>
        </GetCourseRequest>

2. **Response**: response.xml

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseResponse xmlns="http://www.shamy1st.com/courses"
                        xsi:schemaLocation="http://www.shamy1st.com/courses course.xsd"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <CourseDetails>
                <id>123</id>
                <name>Spring Boot with SOAP</name>
                <description>Basics of SOAP web service.</description>
            </CourseDetails>
        </GetCourseResponse>

3. **XSD Validation**: course.xsd
http://edutechwiki.unige.ch/en/XML_Schema_tutorial_-_Basics

        <?xml version="1.0"?>
        <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://www.shamy1st.com/courses"
                xmlns:tns="http://www.shamy1st.com/courses"
                elementFormDefault="qualified">

            <!-- request validation -->
            <xs:element name="GetCourseRequest">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="id" type="xs:integer"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- response validation -->
            <xs:element name="GetCourseResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="CourseDetails">
                            <xs:complexType>
                                <xs:sequence>
                                    <xs:element name="id" type="xs:integer"/>
                                    <xs:element name="name" type="xs:string"/>
                                    <xs:element name="description" type="xs:string"/>
                                </xs:sequence>
                            </xs:complexType>
                        </xs:element>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>
        </xs:schema>

4. **JAXB**: (Java Architecture for XML Binding)

    4.1 copy "course.xsd" to resources dirctory into your project.
    
    4.2 add jaxb2 plugin to pom.xml (only 1.5 works for me)
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

5. **Endpoint**













