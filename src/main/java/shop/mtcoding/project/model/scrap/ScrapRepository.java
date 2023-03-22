package shop.mtcoding.project.model.scrap;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.scrap.CompScrapReq.CompInsertScrapReqDto;
import shop.mtcoding.project.dto.scrap.CompScrapResp.CompScrapOutDto;
import shop.mtcoding.project.dto.scrap.CompScrapResp.CompScrapResumeRespDto;
import shop.mtcoding.project.dto.scrap.UserScrapReq.UserInsertScrapReqDto;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapIdRespDto;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapOutDto;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapRespDto;

@Mapper
public interface ScrapRepository {
    public void findAll();
    public UserScrapOutDto findByJobsScrapId(Integer userScrapId);
    
    public CompScrapOutDto findByResumeScrapId(Integer compScrapId);
    
    public UserScrap findByUserId(
            @Param("userScrapId") Integer userScrapId);

    public UserScrapIdRespDto findScrapIdByUserIdAndJobsId(
        @Param("userId") Integer userId,
        @Param("jobsId") Integer jobsId
    );
    public List<UserScrapRespDto> findAllScrapByUserId(Integer userId);

    public List<CompScrapResumeRespDto> findAllScrapByCompId(Integer compId);

    public CompScrap findByCompId(
            @Param("compScrapId") Integer compScrapId);

    public int insertbyUser(
            @Param("userId") Integer userId,
            @Param("sDto") UserInsertScrapReqDto sDto);

    public int insertbyComp(
            @Param("compId") Integer compId,
            @Param("sDto") CompInsertScrapReqDto sDto);

    public int updateById(

    );

    public int deleteByUserScrapId(
            @Param("userScrapId") Integer userScrapId);

    public int deleteByCompScrapId(
            @Param("compScrapId") Integer compScrapId);
}
