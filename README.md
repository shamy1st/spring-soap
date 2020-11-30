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
                        xsi:schemaLocation="http://www.shamy1st.com/courses request-validation.xsd"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <id>123</id>
        </GetCourseRequest>

2. **Response**: response.xml

        <?xml version="1.0" encoding="UTF-8" ?>
        <GetCourseResponse xmlns="http://www.shamy1st.com/courses"
                        xsi:schemaLocation="http://www.shamy1st.com/courses validation.xsd"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <CourseDetails>
                <id>123</id>
                <name>Spring Boot with SOAP</name>
                <description>Basics of SOAP web service.</description>
            </CourseDetails>
        </GetCourseResponse>

3. **XSD Validation**: validation.xsd

        <?xml version="1.0"?>
        <schema xmlns="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://www.shamy1st.com/courses"
                xmlns:tns="http://www.shamy1st.com/courses"
                elementFormDefault="qualified">

            <element name="GetCourseRequest">
                <complexType>
                    <sequence>
                        <element name="id" type="integer"/>
                    </sequence>
                </complexType>
            </element>
            <element name="GetCourseResponse">
                <complexType>
                    <sequence>
                        <element name="CourseDetails">
                            <complexType>
                                <sequence>
                                    <element name="id" type="integer"/>
                                    <element name="name" type="string"/>
                                    <element name="description" type="string"/>
                                </sequence>
                            </complexType>
                        </element>
                    </sequence>
                </complexType>
            </element>
        </schema>

4. ****


