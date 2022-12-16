package testassured;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import org.junit.jupiter.api.Test;


public class xueqiuTest {
    @Test
    public void testSearch() {
        given().log().all()
            .queryParam("code", "sogo")
            .header("cookie", "xq_a_token=df4b782b118f7f9cabab6989b39a24cb04685f95; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTY3MjE4Njc1MSwiY3RtIjoxNjcwMDcyMTUyNTAwLCJjaWQiOiJkOWQwbjRBWnVwIn0.UsPyeukgVU9znf2XAnnRdUeFRD5DtNcW-FBXdo6gBqNpUlmhkv6JR5DoN81Sc2-PjbKf6ASAm8B_GG4tPfxx2ANoFcayG7mSe2KnMb5WvMfnxkO0E6GpiHARDoM1YS9J3R2K4Fqm5Z44Wkgtcsn1ZBvXYuy3ZbGljbgwil5vA6r8bv-o_LmJhgHeWWnMi--Ex6S3cOnKSOle1a6tAlqX7wog68eSFgBDL2tvNrMPn6JYhQIARNCNFSX7se2kVFcwAhn31lnq5Flw4bvtnNp8ikrLH49955-Y2oU8JW55ZURW_zn8PziaoMyiOtjbvqdubSYtdOKnKCD4_40T15fvCw; xq_r_token=3ae1ada2a33de0f698daa53fb4e1b61edf335952; xqat=df4b782b118f7f9cabab6989b39a24cb04685f95; acw_tc=276077a016700721641502277ee10379b265b823528a75a")
        .when()
            .get("https://xueqiu.com/stock/search.json")
        .then()
            .log().all()
            .statusCode(200)
            .body("stocks.name", hasItems("搜狗"))
            .body("stocks.code", hasItems("SOGO"));
    }

    @Test
public void GetBooksDetails() {
	// Specify the base URL to the RESTful web service
	RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
	// Get the RequestSpecification of the request to be sent to the server.
	RequestSpecification httpRequest = RestAssured.given();
	// specify the method type (GET) and the parameters if any.
	//In this case the request does not take any parameters
	Response response = httpRequest.get("");

    int statusCode = response.getStatusCode();
    Assert.assertEquals(statusCode, 200, "Correct status code returned");
	// Print the status and message body of the response received from the server
	// System.out.println("Status received => " + response.getStatusLine());
	// System.out.println("Response=>" + response.prettyPrint());
    }

    @Test
    public void IteratingHeaders() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("");

        Headers allHeaders = response.headers();

        for(Header header : allHeaders) {
            System.out.printf(" Key: " + header.getName() + " value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json; charset=utf-8");

        String serverType = response.header("Server");
        Assert.assertEquals(serverType, "nginx/1.17.10 (Ubuntu)");
    }

    @Test
    void testPostQuery() {
        given().
            queryParam("name", "jay").
            queryParam("age", 19).
        when().
            post("http://httpbin.org/post").
        then().
            log().all();
    }

    @Test
    void testPostJson() {
        String jsonData = "{\"mobilephone\":\"13323234545\",\"password\":\"234545\"}";
        given().
            body(jsonData).
        when().
            post("http://httpbin.org/post").
        then().
            assertThat().body("json.mobilephone", equalTo("13323234545"));
    }

    @Test
    void testDemo() {
        given().
        when().
            get("https://1b745edf-dd43-488a-890e-903da299010f.mock.pstmn.io/json").
        then().assertThat().body("lotto.lottoId", equalTo(5));

    }
}
