package shop.mtcoding.project.dtoControllerTest;

import static org.mockito.Mockito.doAnswer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.project.config.auth.JwtProvider;
import shop.mtcoding.project.dto.comp.CompReq.CompPasswordReqDto;
import shop.mtcoding.project.dto.comp.CompResp.CompUpdateRespDto;
import shop.mtcoding.project.model.comp.Comp;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class CompControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    MockHttpServletRequest request = new MockHttpServletRequest();
    String token;

    @BeforeEach
    public void mockCompToken() {
        Comp mockUser = Comp.builder()
                .compId(1)
                .build();
        token = JwtProvider.create(mockUser);
    }

    @Test
    public void passwordCheck_test() throws Exception {
        // given
        CompPasswordReqDto cDto = CompPasswordReqDto.builder()
                .compId(1)
                .password("1234")
                .build();
        String requestBody = om.writeValueAsString(cDto);

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comp/passwordCheck")
                .header(JwtProvider.HEADER, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        ResultActions result = mvc.perform(requestBuilder);

        // then
        System.out.println("테스트 : " + result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void updateComp_test() throws Exception {
        // given
        CompUpdateRespDto cDto = CompUpdateRespDto.builder()
                .compId(1)
                .password("123345")
                .compName("카카오")
                .representativeName("이정훈")
                .businessNumber("123-12-1231")
                .build();
        String requestBody = om.writeValueAsString(cDto);

        // when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comp/update")
                .header(JwtProvider.HEADER, token)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        ResultActions result = mvc.perform(requestBuilder);

        // then
        System.out.println("테스트 : " + result.andReturn().getResponse().getContentAsString());
    }

//     @Test
//     public void profileUpdate_test() throws Exception {
//         // given
//         String = 
//         byte[] in = String.
//         CompPhotoUpdateDto cDto = CompPhotoUpdateDto.builder()
//         .name("test.jpg")
//         .type("image/jpeg")
//         .data(null)

//         // when
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comp/profileUpdate")
//                 .header(JwtProvider.HEADER, token)
//                 .content(requestBody)
//                 .contentType(MediaType.APPLICATION_JSON_VALUE);
//         ResultActions result = mvc.perform(requestBuilder);

//         // then
//         System.out.println("테스트 : " + result.andReturn().getResponse().getContentAsString());
//     }
}
