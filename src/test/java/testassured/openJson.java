package testassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

import io.restassured.config.SSLConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;


public class openJson {
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    /**
     * 开放接口的测试用例*/
    @BeforeClass
    public static void gen() {
        /**
        * 通用请求*/
        requestSpecification = new RequestSpecBuilder().build();
        requestSpecification.config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));
//        requestSpecification.port(80);
        requestSpecification.baseUri("https://api.apiopen.top");
        requestSpecification.cookie("s_id", "jiajianbo");
        requestSpecification.header("User-Agent", "Chrome");

        /**
         * 通用断言*/
        //通用断言
        responseSpecification = new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);
    }

    @Test
    public void testGetMiniVideo() {
        RequestSpecification httpRequest = RestAssured.given()
            .spec(requestSpecification)
            .params("page",0,"size", 10);
        Response response = httpRequest.get("/api/getMiniVideo");
        System.out.println(response.asPrettyString());

//        Headers headers = response.getHeaders();
//        System.out.println(headers);

        String message = response.jsonPath().get("message");
        System.out.println("res message: " + message);

        int code = response.jsonPath().getInt("code");
        System.out.println("res code: " + code);

        int total = response.jsonPath().getInt("result.total");
        System.out.println("res total: " + total);

        Assert.assertEquals(message, "成功!");
        Assert.assertEquals(code, 200);
        Assert.assertTrue(total > 100);
//        List<Map<String, Object>> videos = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        System.out.println(videos);
    }

    @Test
    public void testLogin() {
       String bodyJson = "{\"account\": \"309324904@qq.com\", \"password\": \"123456\"}";
        RequestSpecification httpRequest = RestAssured.given()
            .spec(requestSpecification)
            .headers("accept", "application/json", "Content-Type", "application/json")
            .body(bodyJson);
       Response response = httpRequest.post("/api/login");
       System.out.println(response.asPrettyString());

       Headers headers = response.getHeaders();
       System.out.println(headers);

       Map cookies = response.getCookies();
        System.out.println(cookies);
    }

    @Test
    @Parameters
    public void testRegister() {
        String userEmail = "{\"mail\": \"370831196@qq.com\"}";
        RequestSpecification httpRequest = RestAssured.given()
            .spec(requestSpecification)
            .body(userEmail);

        Response response = httpRequest.post("/api/sendVerificationCode");
        System.out.println(response.asPrettyString());

        int code = response.jsonPath().getInt("code");
        String message = response.jsonPath().get("message");

        Assert.assertEquals(code, 200);
        Assert.assertEquals(message, "成功!");
    }
}
