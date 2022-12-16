package testassured;

import org.junit.*;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.testng.Assert;


import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponseOptions.*;
import io.restassured.specification.RequestSpecification;



public class JxValidate {
    @Test
    public void JsonValidate() {
        RestAssured.baseURI = "https://1b745edf-dd43-488a-890e-903da299010f.mock.pstmn.io/daybreak.json";
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.get("");

        int statusCode = response.getStatusCode();
        String book_cate = response.jsonPath().get("store.book[0].category");
        System.out.println(book_cate);

        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(book_cate, "reference");

    }

    @Test
    public void jsonParsed() {
        Response response = RestAssured.given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .params("q", "自动化测试", "start", 0, "count", 5)
                .get("https://api.douban.com/v2/book/search");
        // 打印出 response 的body
        response.print();

        int statusCode = response.getStatusCode();
        System.out.println("statusCode： " + statusCode);

        // 获取Response 的所有headers 并输出
        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers.toString());

        // 获取Response 中header 名称为Content-Type的值
        String contentType = response.getHeader("Content-Type");
        System.out.println("Content-Type: " + contentType);
        System.out.println(response.getContentType());

        // 校验某个header是否存在
        System.out.println(headers.hasHeaderWithName("ffids8w"));
        System.out.println(headers.hasHeaderWithName("Server"));

        // 如果Response 的headers不为空，则返回true
        System.out.println(headers.exist());

        System.out.println(response.cookie("bid"));

        // 把Response 的body转换为string类型
        System.out.println(response.getBody().toString());

        int count = response.jsonPath().getInt("count");
        System.out.println("count: " + count);

        // 获取所有的 subtitle
        ArrayList<String> subtitles = response.jsonPath().get("books.subtitle");
        for(int i=0; i < subtitles.size(); i++) {
            System.out.println(subtitles.get(i));
        }

        // 获取特定某个 subtitle
        String subtitle = response.jsonPath().get("books.subtitle[0]");
        System.out.println(subtitle);

        // 获取倒数第二个subtitle
        String subtitle1 = response.jsonPath().get("books.subtitle[-2]");
        System.out.println(subtitle1);

        // 获取特定tags下的所有title
        ArrayList<String> tagTitle = response.jsonPath().get("books.tags[2].title");
        for(int i = 0; i < tagTitle.size(); i++) {
            System.out.println(tagTitle.get(i));
        }

        // 获取所有的 title
        // ArrayList<String> tagTitles = response.jsonPath().get("books.tag.title");
        // for (int i = 0; i < tagTitles.size(); i++) {
        //     for (int j = 0; j < tagTitles.get(i).size()) {
        //         System.out.println(tagTitles.get(i).get(j));
        //     }
        //     System.out.println("---------------------");
        // }

        // 获取Response json里面所有title = "Selenium 2自动化测试实战"的title
        String title = response.jsonPath().get("books.title.findAll{title ->title==\"Selenium 2自动化测试实战\"}").toString();
        System.out.println(title);

        // 获取Response json中 1< numRaters <=20的所有 numRaters
        String numRaters = response.jsonPath().get("books.rating.numRaters.findAll{numRaters -> numRaters>1 && numRaters<20}");
        System.out.println(numRaters);

        // 获取Response json中title = "基于Selenium 2的自动化测试"对应的 author
        String title1 = response.jsonPath().get("books.findAll{it.title==\"基于Selenium 2的自动化测试\"}.author").toString();
        System.out.println(title1);

        
    }

}
