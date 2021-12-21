package ru.anrad.learnup.tests;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTests {

    static Properties properties = new Properties();
    protected static RequestSpecification requestSpecification;
    static ResponseSpecification responseSpecification;
    protected static ResponseSpecification badReqResponseSpecification;
    protected static ResponseSpecification notFoundResponseSpecification;
    protected static ResponseSpecification createdResponseSpecification;
    protected static ResponseSpecification deleteResponseSpecification;

    @SneakyThrows
    @BeforeAll
    static void beforeAll(){
        RestAssured.filters(new AllureRestAssured());
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        RestAssured.baseURI = properties.getProperty("baseURL");
        setAllureEnvironment();

        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.BODY)
                .build();
        RestAssured.requestSpecification = requestSpecification;

        responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
//                .expectResponseTime(lessThan(1000L), TimeUnit.MILLISECONDS)
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 ")
                .build();
        RestAssured.responseSpecification = responseSpecification;

        badReqResponseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(900L), TimeUnit.MILLISECONDS)
                .expectStatusCode(400)
                .expectStatusLine("HTTP/1.1 400 ")
                .build();
        notFoundResponseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(900L), TimeUnit.MILLISECONDS)
                .expectStatusCode(404)
                .expectStatusLine("HTTP/1.1 404 ")
                .build();
        createdResponseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(900L), TimeUnit.MILLISECONDS)
                .expectStatusCode(201)
                .expectStatusLine("HTTP/1.1 201 ")
                .build();
        deleteResponseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(900L), TimeUnit.MILLISECONDS)
                .expectStatusCode(200)
                .expectContentType("")
                .build();
    }

    public static void setAllureEnvironment(){
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("URL", properties.getProperty("baseURL"))
                        .build());
    }

}
