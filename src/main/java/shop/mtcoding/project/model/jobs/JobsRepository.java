package shop.mtcoding.project.model.jobs;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.comp.CompResp.CompHomeOutDto.JobsManageJobsRespDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsCheckBoxReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsUpdateReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsWriteReqDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsCheckOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsDetailOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsIdRespDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainRecommendRespDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainRespDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMatchRespDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSearchOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSuggestRespDto;

@Mapper
public interface JobsRepository {
    public List<Jobs> findAll();
    public Jobs findById(Integer jobsId);
    public List<JobsMatchRespDto> findMatchJobsByUserId(Integer userId);

    public List<JobsManageJobsRespDto> findByIdtoManageJobs(Integer compId);

    public List<JobsSuggestRespDto> findAllToSuggestReq(Integer compId);

    public List<JobsIdRespDto> findJobsIdByCompId(Integer CompId);

    public int insert(
        @Param("jDto") JobsWriteReqDto jDto
    );
    public int updateById(
        @Param("jDto") JobsUpdateReqDto jDto
    );
    public int deleteById(Integer jobsId);
    
    public List<JobsMainRecommendRespDto> findAlltoMainRecommend(Integer userId);

    public List<JobsMainRecommendRespDto> findAlltoMainRecommendRandom(Integer userId);

    public List<JobsMainOutDto> findAlltoMain(Integer userId);
    public List<JobsMainRespDto> findAlltoMainRamdom(Integer userId);

    public List<JobsSearchOutDto> findBySearch(
        @Param("keyword") String keyword,
        @Param("userId") Integer userId
        );
        
    public List<JobsCheckOutDto> findByCheckBox(
        @Param("jDto") JobsCheckBoxReqDto jDto,
        @Param("userId") Integer userId
        );
    public JobsDetailOutDto findByJobsDetail(
        @Param("jobsId") Integer jobsId,
        @Param("userId") Integer userId
    );
}
