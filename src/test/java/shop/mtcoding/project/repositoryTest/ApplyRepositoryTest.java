package shop.mtcoding.project.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.project.dto.apply.ApplyReq.ApplyReqDto;
import shop.mtcoding.project.dto.apply.ApplyReq.ApplyUpdateReqDto;
import shop.mtcoding.project.model.apply.Apply;
import shop.mtcoding.project.model.apply.ApplyRepository;

@MybatisTest
public class ApplyRepositoryTest {
    
    @Autowired
    private ApplyRepository applyRepository;

    @Test
    @Transactional
    public void insert_test() throws Exception {
        // given
        ApplyReqDto aDto = ApplyReqDto.builder()
                        .resumeId(1)
                        .jobsId(1)
                        .build();
        // when
        System.out.println("테스트 : "+ applyRepository.insert(aDto)); 
    
        // then
        Apply apply = applyRepository.findByApplyId(1);
        assertThat(apply.getState()).isEqualTo("0");
    }

    @Test
    @Transactional
    public void updateById_test() throws Exception {
        // given
        ApplyUpdateReqDto aDto = new ApplyUpdateReqDto();
        aDto.setApplyId(1);
        aDto.setState(1);
    
        // when
        System.out.println("테스트 : "+ applyRepository.updateByApplyId(aDto));
    
        // then
        Apply apply = applyRepository.findByApplyId(1);
        assertThat(apply.getState()).isEqualTo("1");
    }
}
