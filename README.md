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

1. **Request**

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseRequest xmlns="http://www.shamy1st.com/courses">
            <id>123</id>
        </GetCourseRequest>

2. **Response**

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseResponse xmlns="http://www.shamy1st.com/courses">
            <CourseDetails>
                <id>123</id>
                <name>Spring Boot with SOAP</name>
                <description>Basics of SOAP web service.</description>
            </CourseDetails>
        </GetCourseResponse>

3. **XSD Validation for Request**



4. **XSD Validation for Response**






