package shop.mtcoding.project.model.resume;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.project.dto.comp.CompResp.CompHomeOutDto.ResumeMatchOutDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeCheckboxReqDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeUpdateInDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeUpdateReqDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteOutDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteReqDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeDetailRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeIdRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeManageRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeMatchPageOutDto.ResumeMatchDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumePublicOutDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeSaveRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeSearchRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeUpdateRespDto;

@Mapper
public interface ResumeRepository {
        public void findAll();

        public ResumeSaveRespDto findById(Integer resumeId);

        public List<ResumeIdRespDto> findResumeIdByUserId(Integer userId);

        public List<ResumeMatchOutDto> findMatchResumeByCompId(Integer compId);
        public List<ResumeMatchDto> findMatchResumeByCompId2(Integer compId);

        public ResumeUpdateRespDto findUpdateById(Integer resumeId);

        public Resume findByResumeId(Integer resumeId);

        public ResumeWriteOutDto findDataByResumeId(Integer resumeId);

        public List<ResumeManageRespDto> findAllByUserId(Integer userId);

        public List<ResumePublicOutDto> findAllResumebyState(Integer compId);

        public ResumeDetailRespDto findDetailPublicResumebyById(
                        @Param("resumeId") Integer resumeId,
                        @Param("compId") Integer compId);

        public List<ResumeSearchRespDto> findResumeByCheckBox(
                        @Param("resumeDto") ResumeCheckboxReqDto resumeDto);

        public int insert(
                        @Param("rDto") ResumeWriteReqDto rDto);

        public int updateById(
                        @Param("rDto") ResumeUpdateReqDto rDto);

        public int deleteById(Integer resumeId);

        public ResumeUpdateInDto findUpdateDataByResumeId(Integer resumeId);
}
