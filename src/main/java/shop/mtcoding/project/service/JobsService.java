package shop.mtcoding.project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsSearchReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsUpdateReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsWriteReqDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsDetailOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMatchRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeIdRespDto;
import shop.mtcoding.project.dto.skill.RequiredSkillReq.RequiredSkillWriteReqDto;
import shop.mtcoding.project.dto.skill.ResumeSkillResp.ResumeSkillByUserRespDto;
import shop.mtcoding.project.dto.skill.ResumeSkillResp.ResumeSkillRespDto;
import shop.mtcoding.project.model.comp.CompRepository;
import shop.mtcoding.project.model.jobs.Jobs;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.resume.ResumeRepository;
import shop.mtcoding.project.model.skill.SkillRepository;
import shop.mtcoding.project.util.DateUtil;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class JobsService {
    private final JobsRepository jobsRepository;
    private final CompRepository compRepository;
    private final SkillRepository skillRepository;
    private final ResumeRepository resumeRepository;

    public void 공고검색(JobsSearchReqDto jDto) {
    }

    @Transactional
    public JobsDetailOutDto 공고작성(JobsWriteReqDto jDto, Integer compId) {
        Integer jobsId = 0;
        if ( compId != jDto.getCompId()){
            throw new CustomApiException("정상적인 접근이 아닙니다.", HttpStatus.FORBIDDEN);
        }

        JobsUpdateReqDto jUDto = new JobsUpdateReqDto();
        jUDto.setCompName(jDto.getCompName());
        jUDto.setPhoto(jDto.getPhoto());
        jUDto.setRepresentativeName(jDto.getRepresentativeName());
        jUDto.setHomepage(jDto.getHomepage());

        try {
            compRepository.updateById(jUDto);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다11.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            jobsRepository.insert(jDto);
            jobsId = jDto.getJobsId();
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다22.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if ( !ObjectUtils.isEmpty(jDto.getSkillList()) ){
            try {
                skillRepository.insertRequiredSkill(jDto.getSkillList(),jDto.getJobsId());
            } catch (Exception e) {
                throw new CustomApiException("서버에 일시적인 오류가 발생했습니다33.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        JobsDetailOutDto result = jobsRepository.findByJobsDetail(jobsId, null);

        return result;
    }

    @Transactional
    public JobsDetailOutDto 공고수정(JobsUpdateReqDto jDto, Integer compId) {
        Integer jobsId = 0;
        if ( compId != jDto.getCompId()){
            throw new CustomApiException("정상적인 접근이 아닙니다.", HttpStatus.FORBIDDEN);
        }
        try {
            compRepository.updateById(jDto);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다11.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            jobsRepository.updateById(jDto);
            jobsId = jDto.getJobsId();
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다22.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            skillRepository.deleteByJobsId(jDto.getJobsId());
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다22.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ( !ObjectUtils.isEmpty(jDto.getSkillList()) ){
            try {
                skillRepository.insertRequiredSkill(jDto.getSkillList(),jDto.getJobsId());
            } catch (Exception e) {
                throw new CustomApiException("서버에 일시적인 오류가 발생했습니다33.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        JobsDetailOutDto result = jobsRepository.findByJobsDetail(jobsId, null);

        return result;
    }

    @Transactional
    public void 공고삭제(Integer id, Integer compId) {
        Jobs jobsPS = jobsRepository.findById(id);
        if ( jobsPS == null ){
            throw new CustomApiException("해당 공고가 존재 하지 않습니다.");
        }
        if ( jobsPS.getCompId() != compId){
            throw new CustomApiException("공고 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        try {
            jobsRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public List<JobsMatchRespDto> 공고매칭서비스(Integer userId) {
        Set<String> set = new HashSet<>();
        List<ResumeIdRespDto> resumeIdList = resumeRepository.findResumeIdByUserId(userId);
        for (ResumeIdRespDto resumeId : resumeIdList) {
            List<ResumeSkillRespDto> rSkillList = skillRepository.findByResumeSkill(resumeId.getResumeId());
            for (ResumeSkillRespDto skill : rSkillList) {
                set.add(skill.getSkill());
            }
        }

        ResumeSkillByUserRespDto rSkillList = new ResumeSkillByUserRespDto();
        List<String> skillList = new ArrayList<>(set);
        rSkillList.setSkillList(skillList);

        List<JobsMatchRespDto> fiveMatchList = new ArrayList<>();
        List<JobsMatchRespDto> fourMatchList = new ArrayList<>();
        List<JobsMatchRespDto> threeMatchList = new ArrayList<>();
        List<JobsMatchRespDto> twoMatchList = new ArrayList<>();
        List<JobsMatchRespDto> oneMatchList = new ArrayList<>();

        List<JobsMatchRespDto> jDtos = jobsRepository.findMatchJobsByUserId(userId);
        for (JobsMatchRespDto jDto : jDtos) {
            long dDay = DateUtil.dDay(jDto.getEndDate());
            jDto.setLeftTime(dDay);

            int count = 0;
            List<String> insertList = new ArrayList<>();
            for (RequiredSkillWriteReqDto skill : skillRepository.findByJobsSkill(jDto.getJobsId())) {
                insertList.add(skill.getSkill());
                if ( set.contains(skill.getSkill())){
                    count ++ ;
                }
            }
            jDto.setSkillList(insertList);
            if ( count >= 5 ){
                fiveMatchList.add(jDto);
            }else if ( count >= 4 ){
                fourMatchList.add(jDto);
            }else if ( count >= 3 ){
                threeMatchList.add(jDto);
            }else if ( count >= 2 ){
                twoMatchList.add(jDto);
            }else if ( count >= 1 ){
                oneMatchList.add(jDto);
            }
            count = 0;
        }        
        List<JobsMatchRespDto> resultList = new ArrayList<>();
        resultList.addAll(fiveMatchList);
        resultList.addAll(fourMatchList);
        resultList.addAll(threeMatchList);
        resultList.addAll(twoMatchList);
        resultList.addAll(oneMatchList);
        return resultList;
    }
    
}
