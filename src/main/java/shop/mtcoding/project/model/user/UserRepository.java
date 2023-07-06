package shop.mtcoding.project.model.user;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserUpdateReqDto;
import shop.mtcoding.project.dto.user.UserResp.UserApplyOutDto;
import shop.mtcoding.project.dto.user.UserResp.UserDataRespDto;
import shop.mtcoding.project.dto.user.UserResp.UserDeleteRespDto;
import shop.mtcoding.project.dto.user.UserResp.UserHomeOutDto;
import shop.mtcoding.project.dto.user.UserResp.UserHomeOutDto.JobsMainRecommendRespDto;
import shop.mtcoding.project.dto.user.UserResp.UserLoginRespDto;
import shop.mtcoding.project.dto.user.UserResp.UserUpdatePhotoOutDto;
import shop.mtcoding.project.dto.user.UserResp.UserUpdateRespDto;

@Mapper
public interface UserRepository {
    public List<User> findAll();


    public UserHomeOutDto findByUserHome(Integer userId);

    public List<JobsMainRecommendRespDto> findRecommendByUserHome(Integer userId);

    public Optional<User> findByEmailAndPassword(@Param("email") String email, @Param("password") String password); 

    public UserLoginRespDto findByEmailAndPassword2(@Param("email") String email, @Param("password") String password);

    public User findByUseridAndPassword(@Param("userId") Integer userId, @Param("password") String password);

    public User findByUserEmail(@Param("email") String email);

    public User findById(@Param("userId") Integer userId);

    public UserUpdatePhotoOutDto findByUserPhoto(@Param("userId") Integer userId);

    public UserUpdateRespDto findById1(@Param("userId") Integer userId);

    public UserDataRespDto findByUserId(@Param("userId") Integer userId);

    public int insert(@Param("uDto") UserJoinReqDto uDto);

    public int updateById(@Param("uDto") UserUpdateReqDto uDto);

    public int updatePhotoById(@Param("photo") String photo, @Param("userId") Integer userId);

    public int deleteById(@Param("uDto") UserDeleteRespDto uDto);

    public UserApplyOutDto findApplyAndSuggestByUserId(Integer userId);

}
