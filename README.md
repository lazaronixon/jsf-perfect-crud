# JSF PERFECT CRUD

After some time working with Ruby on Rails (more than 5 years), I catch myself rediscovering the JSF/JAVAEE but this time with another mind, so I tried here bring some opnative rules from ROR to JSF. 

The simplicity without a opinionated environment brought to JSF a bad reputation, on my point of view not for the technology but because the bad developers and practices, many of than coming from desktop development and RAD enviroments.

Even now on this times of javascript on everything, json everywhere, quees and distributed systems I believe that JSF/JAVAEE has you place as enterprise application.

This project is a implementation of generated crud of Ruby on Rails using JSF 2.3 and JavaEE 8.

![preview](https://nixo-etc.s3-sa-east-1.amazonaws.com/javajsf.gif)

![controller](https://nixo-etc.s3-sa-east-1.amazonaws.com/screenshot_jsfcrud_3.png)

## URL
* http://localhost:8080/jsfcrud/views/students/index.xhtml

## Requirements

* OpenJDK 11
* Netbeans 11.3
* Payara Server 5.194
* Java DB (embedded)

## Database

```SQL
CREATE TABLE STUDENT (
   ID INT GENERATED ALWAYS AS IDENTITY,
   NAME VARCHAR(255),
   ADDRESS VARCHAR(255),
   PRIMARY KEY (Id)
);
```

## References and Guides

* https://netbeans.org/kb/trails/java-ee.html
* https://www.primefaces.org/showcase
* https://jsf.zeef.com/bauke.scholtz
* https://www.amazon.com/Practical-JSF-Java-EE-8/dp/1484230299
