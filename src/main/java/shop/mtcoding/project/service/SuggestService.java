package shop.mtcoding.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestReqDto;
import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestUpdateReqDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestResumeOutDto;
import shop.mtcoding.project.model.jobs.Jobs;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.resume.Resume;
import shop.mtcoding.project.model.resume.ResumeRepository;
import shop.mtcoding.project.model.suggest.Suggest;
import shop.mtcoding.project.model.suggest.SuggestRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SuggestService {
    private final SuggestRepository suggestRepository;
    private final ResumeRepository resumeRepository;
    private final JobsRepository jobsRepository;

    @Transactional
    public SuggestResumeOutDto 제안하기(SuggestReqDto sDto, Integer compId){
        if ( compId != sDto.getCompId()){
            throw new CustomApiException("수정 권한이 없습니다." , HttpStatus.FORBIDDEN);
        }
        Resume resumePS = resumeRepository.findByResumeId(sDto.getResumeId());
        if ( resumePS == null){
            throw new CustomApiException("존재하지 않는 이력서 입니다.");
        }
        Jobs jobsPS = jobsRepository.findById(sDto.getJobsId());
        if ( jobsPS == null ){
            throw new CustomApiException("존재하지 않는 공고 입니다.");
        }
        Integer suggestId = 0;
        try {
            suggestRepository.insert(sDto);
            suggestId = sDto.getSuggestId();
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SuggestResumeOutDto result = suggestRepository.findBySuggestId(suggestId);
        return result;
    }

    @Transactional
    public SuggestResumeOutDto 제안수락(SuggestUpdateReqDto sDto, Integer userId) {
        if (userId != sDto.getUserId()){
            throw new CustomApiException("수정 권한이 없습니다." , HttpStatus.FORBIDDEN);
        }
        Suggest suggest = suggestRepository.findById(sDto.getSuggestId());
        if ( suggest == null){
            throw new CustomApiException("존재하지 않는 제안입니다.");
        }
        try {
            suggestRepository.updateBySuggestId(sDto);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SuggestResumeOutDto result = suggestRepository.findBySuggestId(sDto.getSuggestId());
        return result;
    }

    @Transactional
    public SuggestResumeOutDto 제안거절(SuggestUpdateReqDto sDto, Integer userId) {
        if (userId != sDto.getUserId()){
            throw new CustomApiException("수정 권한이 없습니다." , HttpStatus.FORBIDDEN);
        }
        Suggest suggest = suggestRepository.findById(sDto.getSuggestId());
        if ( suggest == null){
            throw new CustomApiException("존재하지 않는 제안입니다.");
        }
        try {
            suggestRepository.updateBySuggestId(sDto);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        SuggestResumeOutDto result = suggestRepository.findBySuggestId(sDto.getSuggestId());
        return result;
    }
}