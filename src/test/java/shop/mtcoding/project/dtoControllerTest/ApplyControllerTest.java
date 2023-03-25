package shop.mtcoding.project.dtoControllerTest;

import java.sql.Date;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.project.config.auth.JwtProvider;
import shop.mtcoding.project.config.auth.SecretKey;
import shop.mtcoding.project.dto.apply.ApplyReq.ApplyReqDto;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.user.User;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ApplyControllerTest {
 
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    MockHttpServletRequest request = new MockHttpServletRequest();

    String token ;
    @BeforeEach
    public void mockUserToken(){
        User mockUser = User.builder()
                    .userId(1)
                    .build();
        // return mockUser;
        token = JwtProvider.create(mockUser);
    }

    @Test
    public void applyResume_test() throws Exception {
        // given
        // User mockUser = mockUserToken(); // BeforeEach 있으면 필요
        // String token = JwtProvider.create(mockUser);

        ApplyReqDto aDto = ApplyReqDto.builder()
                            .resumeId(1)
                            .jobsId(1)
                            .userId(1)
                            .applyId(1)
                            .build();
                          
        String requestBody = om.writeValueAsString(aDto);

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/apply/resume")
                                                    .header(JwtProvider.HEADER, token)
                                                    .content(requestBody)
                                                    .contentType(MediaType.APPLICATION_JSON_VALUE); 
        
        ResultActions result = mvc.perform(requestBuilder);
    
         // then
        System.out.println("테스트 : "+ result.andReturn().getResponse().getContentAsString());
    
    }

}
