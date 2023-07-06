package shop.mtcoding.project.utilTest;

import java.nio.charset.Charset;
import java.util.Base64;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class HtmlParser {


    @Test
    public void jsoup_test() throws Exception {
        String html = "<img src=\"data:image/jpeg;base64,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\" style=\"width: 128px;\" data-filename=\"미리디.jpg\">";
        Document d = Jsoup.parse(html);
        Elements els = d.select("img");
        if (els.size() == 0) {
            // 임시
        } else {
            String result = els.attr("src");
            String result2 = els.attr("data-filename");
            System.out.println("테스트 : " + result);
            System.out.println("테스트 : " + result2);
        }
    }

    @Test
    public void jsoup_test2() {
        String html = "<img src=\"data:image/jpeg;base64,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\" style=\"width: 128px;\" data-filename=\"미리디.jpg\">";
        String img = "";
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");
        if (els.size() == 0) {
            // 임시 사진
        } else {
            Element el = els.get(0);
            img += el.attr("src");
            System.out.println("테스트 : " + img);
            String input = img.split(",")[1];
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            String decodedString = new String(decodedBytes, Charset.forName("UTF-8"));
            System.out.println("테스트 : " + decodedString);
        }
    }


    @Test
    public void Decode_test() throws Exception {
        // Base64 디코딩
        String test23 = "YmFzZSA2NCDsnbjsvZTrlKkg7ZWY66Ck64qUIO2FjeyKpO2KuOulvCDsnoXroKXtlag=";
        byte[] decodedBytes = Base64.getDecoder().decode(test23);
        String decodedString = new String(decodedBytes);
        System.out.println("테스트 : " + decodedString);
    }
}