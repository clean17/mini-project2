package shop.mtcoding.project.repositoryTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteReqDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeDetailRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeReadRespDto;
import shop.mtcoding.project.model.resume.ResumeRepository;

@MybatisTest
public class ResumeRepositoryTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    @Transactional
    public void insert_test() throws Exception {
        // given
        ResumeWriteReqDto resumeWriteReqDto = new ResumeWriteReqDto();
        resumeWriteReqDto.setUserId(1);
        resumeWriteReqDto.setTitle("백엔드 이력서");
        resumeWriteReqDto.setContent("백엔드 이력서의 내용입니다.");
        resumeWriteReqDto.setEducation("고졸");
        resumeWriteReqDto.setCareer("신입");
        resumeWriteReqDto.setLink("블로그 주소");
        resumeWriteReqDto.setState(1);

        // when
        System.out.println("테스트 : " + resumeRepository.insert(resumeWriteReqDto));

        // then
    }

    @Test
    public void findAllResumebyState_test() throws Exception {
        // given
        
        // when
        // List<ResumeReadRespDto> rList = resumeRepository.findAllResumebyState();

        // then
        // rList.forEach((s)->{System.out.println("테스트 : "+ s.toString());});
    }   

    @Test
    public void findDetailPublicResumebyById_test() throws Exception {
        // given
        Integer test = 1;
        // when
        // ResumeDetailRespDto rDto = resumeRepository.findDetailPublicResumebyById(test,null);

        // then
        // System.out.println("테스트 : "+ rDto.toString());
    }   


}