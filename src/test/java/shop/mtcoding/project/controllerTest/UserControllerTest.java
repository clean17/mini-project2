package shop.mtcoding.project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.project.config.auth.JwtProvider;
import shop.mtcoding.project.dto.user.UserReq.UserUpdateReqDto;
import shop.mtcoding.project.model.user.User;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;


    private String jwtToken = "";

    @Autowired
    private ObjectMapper om;

    // 인증 방식의 변화로 테스트 코드로 수정되어야 한다.
    @BeforeEach
    public void setup() {
        User mockUser = new User(
                1,
                "ssar@nate.com",
                "1234",
                "ssar",
                "2000-01-01",
                "010-1234-1234",
                "/images/default_profile.png",
                "부산시 부산진구",
                new Timestamp(System.currentTimeMillis()));
        jwtToken = JwtProvider.create(mockUser);
    }

    @Test
    @Transactional
    public void update_test() throws Exception {
        // given
        UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto();
        userUpdateReqDto.setUserId(1);
        userUpdateReqDto.setName("dddd");
        userUpdateReqDto.setPassword("1111");
        userUpdateReqDto.setBirth("22222-1111");
        userUpdateReqDto.setTel("010-1111");
        userUpdateReqDto.setAddress("dfsfsdf");
        String requestBody = om.writeValueAsString(userUpdateReqDto);

        UserUpdateReqDto uDto = new UserUpdateReqDto();
        uDto.setPassword("1234");
        uDto.setName("ssar");
        uDto.setBirth("2000-01-01");
        uDto.setTel("010-1234-1234");
        uDto.setAddress("부산시 부산진구");

        // when
        ResultActions resultActions = mvc.perform(
                put("/user/update")
                        .header("Authorization", jwtToken)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    @Test
    @Transactional
    public void join_test() throws Exception {
        // given
        String requestBody = "email=at@21&password=1234&name=쌀&birth=11111&tel=11111";

        String[] keyValuePairs = requestBody.split("&");
        Map<String, String> dataMap = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            String key = entry[0];
            String value = entry[1];
            dataMap.put(key, value);
        }
        String json = om.writeValueAsString(dataMap);

        // when
        ResultActions resultActions = mvc.perform(post("/userjoin")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void login_test() throws Exception {
        // given
        String requestBody = "email=ssar@nate.com&password=1234";
        String[] keyValuePairs = requestBody.split("&");
        Map<String, String> dataMap = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            String key = entry[0];
            String value = entry[1];
            dataMap.put(key, value);
        }
        String json = om.writeValueAsString(dataMap);
        // when
        ResultActions resultActions = mvc.perform(post("/userlogin").content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
    }

}
