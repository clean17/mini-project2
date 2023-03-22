package shop.mtcoding.project.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.scrap.CompScrapReq.CompInsertScrapReqDto;
import shop.mtcoding.project.dto.scrap.CompScrapResp.CompScrapOutDto;
import shop.mtcoding.project.dto.scrap.UserScrapReq.UserInsertScrapReqDto;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapOutDto;
import shop.mtcoding.project.model.jobs.Jobs;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.resume.Resume;
import shop.mtcoding.project.model.resume.ResumeRepository;
import shop.mtcoding.project.model.scrap.CompScrap;
import shop.mtcoding.project.model.scrap.ScrapRepository;
import shop.mtcoding.project.model.scrap.UserScrap;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final JobsRepository jobsRepository;
    private final ResumeRepository resumeRepository;

    @Transactional
    public UserScrapOutDto 공고스크랩(Integer userId, UserInsertScrapReqDto sDto ){
        Integer userScrapId = 0;
        if ( userId != sDto.getUserId()){
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        Jobs jobs = jobsRepository.findById(sDto.getJobsId());
        if ( jobs == null ){
            throw new CustomApiException("존재하지 않는 공고 입니다.");
        }
        try {
            scrapRepository.insertbyUser(userId, sDto);
            userScrapId = sDto.getUserScrapId();
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserScrapOutDto reseult = scrapRepository.findByJobsScrapId(userScrapId);
        return reseult;
    }

    @Transactional
    public void 공고스크랩삭제(Integer userId, Integer userScrapId) {
        UserScrap USPS = scrapRepository.findByUserId(userScrapId);
        if ( USPS == null ){
            throw new CustomApiException("스크랩이 존재하지 않습니다.");
        }
        if ( userId != USPS.getUserId()){
            throw new CustomApiException("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        try {
            scrapRepository.deleteByUserScrapId(userScrapId);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public CompScrapOutDto 이력서스크랩(Integer compId, CompInsertScrapReqDto sDto ){
        Integer compScrapId = 0;
        if ( compId != sDto.getCompId()){
            throw new CustomApiException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        Resume resumePS =  resumeRepository.findByResumeId(sDto.getResumeId());
        if ( resumePS == null ){
            throw new CustomApiException("존재하지 않는 이력서 입니다.");
        }
        try {
            scrapRepository.insertbyComp(compId, sDto);
            compScrapId = sDto.getCompScrapId();
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CompScrapOutDto result = scrapRepository.findByResumeScrapId(compScrapId);
        return result;
    }

    @Transactional
    public void 이력서스크랩삭제(Integer compId, Integer compScrapId) {
        CompScrap CSPS = scrapRepository.findByCompId(compScrapId);
        if ( CSPS == null ){
            throw new CustomApiException("스크랩이 존재하지 않습니다.");
        }
        if ( compId != CSPS.getCompId()){
            throw new CustomApiException("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        try {
            scrapRepository.deleteByCompScrapId(compScrapId);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
