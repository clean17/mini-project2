package shop.mtcoding.project.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.project.dto.jobs.JobsReq.JobsCheckBoxReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsUpdateReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsWriteReqDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSearchOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSuggestRespDto;
import shop.mtcoding.project.model.jobs.Jobs;
import shop.mtcoding.project.model.jobs.JobsRepository;

@MybatisTest
public class JobsRepositoryTest {
    
    @Autowired
    private JobsRepository jobsRepository;

    @Test
    @Transactional
    public void findBySearch_test() throws Exception {
        // given
        String keyword = "백";
    
        // when
        List<JobsSearchOutDto> sDtos = jobsRepository.findBySearch(keyword, null);
        
        // then
        System.out.println("테스트 : "+ sDtos.size());
        // sDtos.forEach(System.out::print);
    
    }

    @Test
    @Transactional
    public void findByCheckBox_test() throws Exception {
        // given
        JobsCheckBoxReqDto j = new JobsCheckBoxReqDto();
        j.setCareer("");
        List<String> insertList = new ArrayList<>();
        insertList.add("인천");
        insertList.add("제주");
        j.setAddress(insertList);
        // List<String> position = Arrays.asList("서울");

        // when
        // List<JobsSearchRespDto> jDtos = jobsRepository.findByCheckBox(j);
        // for (JobsSearchRespDto jDto : jDtos) {
        //     System.out.println("테스트 : "+ jDto.toString());
        //     }
        // System.out.println("테스트 : "+ jDtos.size());

        //then
    }

    @Test
    @Transactional
    public void findAlltoMain_test() throws Exception {
        //given
        Integer test = null ;

        //when
        List<JobsMainOutDto> t = jobsRepository.findAlltoMain(test);

        //then
        System.out.println("테스트 : "+ t.size());
    }

    @Test
    @Transactional
    public void findByJobsDetail_test() throws Exception {
        //given
        Integer test = null ;

        //when
        List<JobsMainOutDto> t = jobsRepository.findAlltoMain(test);

        //then
        System.out.println("테스트 : "+ t.size());
    }

    @Test
    @Transactional
    public void insert_test() throws Exception {
        // given
        JobsWriteReqDto jDto = new JobsWriteReqDto();   
        jDto.setAddress("123");
        jDto.setTitle("123");
        jDto.setContent("123");
        jDto.setPhoto("123");
        jDto.setPosition("123");
        jDto.setEducation("123");
        jDto.setEndDate(new Timestamp(System.currentTimeMillis()));
        jDto.setCareer("123");
        jDto.setCompId(123);
        // jDto.setHomepage("123");
        jDto.setReceipt("123");
       
        // when
        System.out.println("테스트 : "+jobsRepository.insert(jDto)); 
    
        // then
    }

    @Test
    @Transactional
    public void updateById_test() throws Exception {
        // given
        JobsUpdateReqDto jDto = new JobsUpdateReqDto();   
        jDto.setJobsId(1);
        jDto.setAddress("123");
        jDto.setTitle("123");
        jDto.setContent("123");
        jDto.setPhoto("123");
        jDto.setPosition("123");
        jDto.setEducation("123");
        jDto.setEndDate(new Timestamp(System.currentTimeMillis()));
        jDto.setCareer("123");
        jDto.setCompId(123);
        // jDto.setHomepage("123");
        jDto.setReceipt("123");
       
        // when
        System.out.println("테스트 : "+jobsRepository.updateById(jDto)); 
    
        // then
        assertThat(jobsRepository.findById(jDto.getJobsId()).getEducation()).isEqualTo("123");
        
    }

    @Test
    @Transactional
    public void findAllToSuggestReq_test() throws Exception {
        // given
        Integer test = 1;
        // when
        List<JobsSuggestRespDto> sList = jobsRepository.findAllToSuggestReq(test);

        // then
        sList.forEach((s)->{System.out.println("테스트 : "+ s.toString());});
        }
    
    @Test
    @Transactional
    public void deleteById_test() throws Exception {
        // given
        Integer id = 1;
    
        // when
        jobsRepository.deleteById(id);
    
        // then
        Jobs jobsPS = jobsRepository.findById(id);
        if (jobsPS == null ){
            System.out.println("테스트 : 삭제성공");
        }
    }
}
