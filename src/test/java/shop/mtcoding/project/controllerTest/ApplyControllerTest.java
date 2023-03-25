package shop.mtcoding.project.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;
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

import shop.mtcoding.project.dto.apply.ApplyReq.ApplyReqDto;
import shop.mtcoding.project.dto.apply.ApplyReq.ApplyUpdateReqDto;
import shop.mtcoding.project.model.apply.Apply;
import shop.mtcoding.project.model.apply.ApplyRepository;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.user.User;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ApplyControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApplyRepository applyRepository;

    private MockHttpSession mockSession;

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
    
    @Test
    @Transactional
    public void applyResume_test() throws Exception {
        // given
        mockUserSession();
        ApplyReqDto aDto = ApplyReqDto.builder()
                            .resumeId(1)
                            .jobsId(1)
                            .userId(1)
                            .applyId(1)
                            .build();
        String json = om.writeValueAsString(aDto);
    
        // when
        ResultActions rs = mvc.perform(post("/user/apply/resume")
                              .content(json).contentType(MediaType.APPLICATION_JSON_VALUE).session(mockSession));
    
        // then
        System.out.println("테스트 : "+ rs.andReturn().getResponse().getContentAsString()); 
        // assertThat(applyRepository.findAllByUserIdtoApply(aDto.getUserId()).get(0).getJobsTitle()).isEqualTo("백엔드 개발자");
    
    }

    @Test
    @Transactional
    public void updateApply_test() throws Exception {
        // given
        mockCompSession();
        ApplyUpdateReqDto aDto = new ApplyUpdateReqDto();
        aDto.setApplyId(1);
        aDto.setState(1);
        aDto.setCompId(1);
        String requestBody = om.writeValueAsString(aDto);
    
        // when
        ResultActions rs = mvc.perform(put("/comp/apply/update")
                                                .content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .session(mockSession));
        // then
        System.out.println("테스트 : "+  rs.andReturn().getResponse().getContentAsString());
        Apply apply = applyRepository.findByApplyId(1);
        assertThat(apply.getState()).isEqualTo("1");
    
    }
        
}
