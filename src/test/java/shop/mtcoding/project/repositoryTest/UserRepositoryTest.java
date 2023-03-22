package shop.mtcoding.project.repositoryTest;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.project.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserUpdateReqDto;
import shop.mtcoding.project.model.user.UserRepository;

@MybatisTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void insert_test() throws Exception {
        // given
        UserJoinReqDto uDto = new UserJoinReqDto();
        uDto.setUserId(24);
        uDto.setEmail("ssar25@nate.com");
        uDto.setPassword("1234");
        uDto.setName("ssar");
        uDto.setBirth("2000-01-01");
        uDto.setTel("010-1234-1234");
        uDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // when
        System.out.println("테스트 : " + userRepository.insert(uDto));

        // then

    }

    @Test
    @Transactional
    public void updateById_test() throws Exception {
        // given
        UserUpdateReqDto uDto = new UserUpdateReqDto();
        uDto.setPassword("1234");
        uDto.setName("ssar");
        uDto.setBirth("2000-01-01");
        uDto.setTel("010-1234-1234");
        uDto.setAddress("부산시 부산진구");

        // when
        System.out.println("테스트 : " + userRepository.updateById(uDto));

        // then

    }

    @Test
    @Transactional
    public void updatePhotoById_test() throws Exception {
        // given

        // when
        userRepository.updatePhotoById("/images/033fad18-eeb0-4d44-a99c-dfa00955ec24_logo192.png", 6);

        // then

    }

}
