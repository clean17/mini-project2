package shop.mtcoding.project.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestReqDto;
import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestUpdateReqDto;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.suggest.SuggestRepository;
import shop.mtcoding.project.model.user.User;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SuggestController {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SuggestRepository suggestRepository;
    private MockHttpSession mockSession;

    private void mockCompSession() {
        Comp mockcomp = new Comp(
            1,
            "kakao@kakao.com",
            "1234",
            "카카오(주)",
            "홍은택",
            "120-81-47521",
            "/images/kakao.png",
            "http://www.kakaocorp.com",
            new Timestamp(System.currentTimeMillis()));
        mockSession = new MockHttpSession();
        mockSession.setAttribute("compSession", mockcomp);
    }

    private void mockUserSession() {
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
        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", mockUser);
    }

    @Test
    @Transactional
    public void suggestJobs_test() throws Exception {
        // given
        mockCompSession();
        SuggestReqDto sDto = new SuggestReqDto();
        sDto.setJobsId(1);
        sDto.setResumeId(1);
        sDto.setCompId(1);
        String json = om.writeValueAsString(sDto);
    
        // when
        ResultActions rs = mvc.perform(post("/comp/suggest/jobs")
                              .content(json).contentType(MediaType.APPLICATION_JSON_VALUE).session(mockSession));
    
        // then
        // assertThat(suggestRepository.findAllByCompIdtoSuggest(sDto.getCompId()).get(0).getState()).isEqualTo(0);
        // assertThat(suggestRepository.findAllByCompIdtoSuggest(sDto.getCompId()).get(0).getName()).isEqualTo("박인우");
        System.out.println("테스트 : "+ rs.andReturn().getResponse().getContentAsString()); 
    }

    @Test
    @Transactional
    public void updateSuggest_test() throws Exception {
        // given
        mockUserSession();
        SuggestUpdateReqDto sDto = new SuggestUpdateReqDto();
        sDto.setState(1);
        sDto.setSuggestId(1);
        sDto.setUserId(1);
        String requestBody = om.writeValueAsString(sDto);
    
        // when
        ResultActions rs = mvc.perform(put("/user/suggest/update")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .session(mockSession));
        
        System.out.println("테스트 : "+ rs.andReturn().getResponse().getStatus()); 
        System.out.println("테스트 : "+ rs.andReturn().getResponse().getContentAsString()); 
    
        // then
        // assertThat(suggestRepository.findAllGetOfferByUserId(sDto.getUserId()).get(0).getCompName()).isEqualTo("쿠팡");
        // assertThat(suggestRepository.findAllGetOfferByUserId(sDto.getUserId()).get(0).getPosition()).isEqualTo("백엔드 개발자");
    
    }
}
