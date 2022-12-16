package testassured;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.*;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    
    @Test
    void testApp() {
        Response response = given()
            .get("http://httpbin.org/get?phone=13323234545&password=123456");
        response.print();
    }

    @Test()
    public void getHttpsTest() {
        // Response response = given()
            // 配置SSL 让所有请求支持所有的主机名
            // .config((RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));

        // 打印出 response 的body
        // response.print();
        Response response = given()
            .queryParam("test", "123")
            .get("https://postman-echo.com/get");

        response.print();
    }

    @Test
    public void jsonParsed() {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .params("q", "自动化测试", "start", 0, "count", 5)
                .get("https://api.douban.com/v2/book/search");
        // 打印出 response 的body
        response.print();

        int statusCode = response.getStatusCode();
        System.out.println("statusCode:" + statusCode);

        // 获取Response 的所有 headers 并输出
        Headers headers = response.getHeaders();
        System.out.println(headers.toString());

        // 获取Response中header名为Content-Type的值
        String contentType = response.getHeader("Content-Type");
        System.out.println("contentType:" + contentType);
        // 等同上面方法
        System.out.println(headers.get("Content-Type"));

        // 校验某个Header 是否存在
        System.out.println(headers.hasHeaderWithName("fasdfaf"));
        System.out.println(headers.hasHeaderWithName("Server"));

        // 如果Response 的headers不为空则返回true
        System.out.println(headers.exist());

        Map<String, String> cookiesMap = response.cookies();
        for (String key : cookiesMap.keySet()) {
            System.out.println(key + ":" + cookiesMap.get(key));
        }

        System.out.println(response.cookie("bid"));


        // 把Response 的body转成string类型
        System.out.println(response.getBody().asString());

        int count = response.jsonPath().getInt("count");
        System.out.println("count:" + count);

        // 获取所有的 subtitle
        ArrayList<String> subtitles = response.jsonPath().get("books.subtitle");
        for (int i = 0; i < subtitles.size(); i++) {
            System.out.println(subtitles.get(i));
        }

        // 获取特定某个的subtitle
        String subtitle = response.jsonPath().get("books.subtitle[0]");
        System.out.println(subtitle);

        // 获取倒数第二个的subtitle
        String subtitle1 = response.jsonPath().get("books.subtitle[-2]");
        System.out.println(subtitle1);

        // 获取特定tags底下的所有title
        ArrayList<String> tagTitle = response.jsonPath().get("books.tags[2].title");
        for (int i = 0; i < tagTitle.size(); i++) {
            System.out.println(tagTitle.get(i));
        }

        // 获取所有的 title
        ArrayList<ArrayList<String>> tagTitles = response.jsonPath().get("books.tags.title");
        for (int i = 0; i < tagTitles.size(); i++) {
            for (int j = 0; j < tagTitles.get(i).size(); j++) {
                System.out.println(tagTitles.get(i).get(j));
            }
            System.out.println("---------------------");

        }

        // 获取Response json里面所有title = "Selenium 2自动化测试实战"的title
        String title = response.jsonPath().get("books.title.findAll{title ->title==\"Selenium 2自动化测试实战\"}").toString();
        System.out.println(title);

        // 获取Response json中 1< numRaters <=20的所有 numRaters
        String numRaters = response.jsonPath().get("books.rating.numRaters.findAll{numRaters -> numRaters>1 && numRaters<=20}").toString();
        System.out.println(numRaters);

        // 获取Response json种title = "基于Selenium 2的自动化测试"对应的 author
        String title2 = response.jsonPath().get("books.findAll{it.title==\"基于Selenium 2的自动化测试\"}.author").toString();
        System.out.println(title2);

    } 
}
