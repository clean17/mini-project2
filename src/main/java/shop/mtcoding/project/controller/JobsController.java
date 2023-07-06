package shop.mtcoding.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginComp;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.auth.LComp;
import shop.mtcoding.project.config.auth.LUser;
import shop.mtcoding.project.config.exception.CustomException;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.comp.CompResp.CompWriteJobsRespDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsCheckBoxReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsUpdateReqDto;
import shop.mtcoding.project.dto.jobs.JobsReq.JobsWriteReqDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsCheckOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsDetailOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMatchRespDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSearchOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSearchkeyOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsSuggestRespDto;
import shop.mtcoding.project.model.comp.CompRepository;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.service.JobsService;
import shop.mtcoding.project.util.CheckValid;
import shop.mtcoding.project.util.DateUtil;

@Controller
@RequiredArgsConstructor
public class JobsController {
    private final JobsRepository jobsRepository;
    private final CompRepository compRepository;
    private final JobsService jobsService;

    @GetMapping("/comp/request/jobs")
    @ResponseBody
    public ResponseEntity<?> requestJobs(@LoginComp LComp comp) {
        List<JobsSuggestRespDto> jDtos = jobsRepository.findAllToSuggestReq(comp.getId());
        for (JobsSuggestRespDto jDto : jDtos) {
            jDto.setLeftTime(DateUtil.dDay(jDto.getEndDate()));
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "공고 불러오기 완료", jDtos), HttpStatus.OK);
    }

    @GetMapping("/jobs/search")
    @ResponseBody
    // public ResponseEntity<?> searchJobs(@LoginUser LUser user, @RequestBody SearchText sDto){
    public ResponseEntity<?> searchJobs(@LoginUser LUser user, String keyword){  // 쿼리스트링으로 검색
        // String keyword ;
        // if(ObjectUtils.isEmpty(sDto.getKeyword())){
        if(ObjectUtils.isEmpty(keyword)){
            keyword = "검색어를 입력해 주세요 !!!";
            throw new CustomException("검색어가 없습니다.");
        }
        // keyword = sDto.getKeyword();
        Integer num = null;
        if( user != null ) num = user.getId();
        // keyword="개발";
        List<JobsSearchOutDto> jDtos = jobsRepository.findBySearch(keyword, num);
        for (JobsSearchOutDto jDto : jDtos) {
            jDto.setLeftTime(DateUtil.dDay(jDto.getEndDate()));
        }        
        JobsSearchkeyOutDto jDto = JobsSearchkeyOutDto.builder()
                                            .keyword(keyword)
                                            .jobsSearchOutDto(jDtos)
                                            .build();
        return new ResponseEntity<>(new ResponseDto<>(1, "검색 완료", jDto), HttpStatus.OK);
    }


    @GetMapping("/jobs/info/search")
    public ResponseEntity<?> searchCheckbox(@LoginUser LUser user, JobsCheckBoxReqDto jobsDto) {
        if (ObjectUtils.isEmpty(jobsDto.getCareer())) jobsDto.setCareer("");
        Integer num = null;
        if( user != null ) num = user.getId();

        List<JobsCheckOutDto> jDtos = jobsRepository.findByCheckBox(jobsDto, num);
        for (JobsCheckOutDto jDto : jDtos) {
            jDto.setLeftTime(DateUtil.dDay(jDto.getEndDate()));
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "검색 성공", jDtos), HttpStatus.OK);
    }

    @GetMapping("/jobs/info")
    public ResponseEntity<?> info(@LoginUser LUser user) throws Exception {
        Integer num = null;
        if( user != null ) num = user.getId();

        List<JobsMainOutDto> jDtos = jobsRepository.findAlltoMain(num);
        for (JobsMainOutDto jDto1 : jDtos) {
            jDto1.setLeftTime(DateUtil.dDay(jDto1.getEndDate()));
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "조회 성공", jDtos), HttpStatus.OK);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> viewJobs(@LoginUser LUser user, @PathVariable Integer id) {
        CheckValid.isNull(jobsRepository.findById(id), "조회한 공고가 존재하지 않습니다.");
        Integer num = null;
        if( user != null ) num = user.getId();
        JobsDetailOutDto jDto = jobsRepository.findByJobsDetail(id, num);
        jDto.setLeftTime(DateUtil.dDay(jDto.getEndDate()));
        jDto.setFormatEndDate(DateUtil.format(jDto.getEndDate()));
        return new ResponseEntity<>(new ResponseDto<>(1, "상세 공고조회 성공", jDto), HttpStatus.OK);
    }

    @GetMapping("/comp/jobs/write")
    public ResponseEntity<?> writeJobs(@LoginComp LComp comp) {
        CompWriteJobsRespDto cDto = compRepository.findByIdToJobs(comp.getId());
        CheckValid.isNullApi(cDto, "회사정보가 없습니다.");
        return new ResponseEntity<>(new ResponseDto<>(1, "공고 작성 양식 불러오기 성공", cDto), HttpStatus.OK);
    }

    @GetMapping("/jobs/{id}/update")
    public ResponseEntity<?> updateJobs(@PathVariable Integer id) {
        CheckValid.isNull(jobsRepository.findById(id), "조회한 공고가 존재하지 않습니다.");
        JobsDetailOutDto jDto = jobsRepository.findByJobsDetail(id, null);
        jDto.setLeftTime(DateUtil.dDay(jDto.getEndDate()));
        jDto.setFormatEndDate(DateUtil.format(jDto.getEndDate()));
        return new ResponseEntity<>(new ResponseDto<>(1, "공고 업데이트 양식 불러오기 성공", jDto), HttpStatus.OK);
    }

    @PostMapping("/jobs/info/list")
    public ResponseEntity<?> searchJobsSize(@LoginUser LUser user, @RequestBody JobsCheckBoxReqDto jDto) {
        if (ObjectUtils.isEmpty(jDto.getCareer())) jDto.setCareer("");
        Integer num = null;
        if( user != null ) num = user.getId();

        List<JobsCheckOutDto> jDtos = jobsRepository.findByCheckBox(jDto, num);
        for (JobsCheckOutDto jDto1 : jDtos) {
            jDto1.setLeftTime(DateUtil.dDay(jDto1.getEndDate()));
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "검색 성공", jDtos.size()), HttpStatus.OK);
    }

    @GetMapping("/user/jobs/interest")
    public ResponseEntity<?> interest(@LoginUser LUser user) {
        List<JobsMatchRespDto> jDtos = jobsService.공고매칭서비스(user.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "매칭 공고 조회 성공", jDtos), HttpStatus.OK);
    }
    

    @PostMapping("/comp/jobs/write")
    public ResponseEntity<?> writeJobs(@Valid @RequestBody JobsWriteReqDto jDto, BindingResult bindingResult, @LoginComp LComp comp) {
        JobsDetailOutDto jobsId = jobsService.공고작성(jDto, comp.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "저장 완료", jobsId), HttpStatus.CREATED);
    }

    @PutMapping("/comp/jobs/update")
    public ResponseEntity<?> updateJobs(@Valid @RequestBody JobsUpdateReqDto jDto, BindingResult bindingResult, @LoginComp LComp comp) {
        JobsDetailOutDto jobdId = jobsService.공고수정(jDto, comp.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "수정 완료", jobdId), HttpStatus.CREATED);
    }

    @DeleteMapping("/comp/jobs/{id}/delete")
    public ResponseEntity<?> deleteJobs(@PathVariable Integer id, @LoginComp LComp comp){
        if( ObjectUtils.isEmpty(jobsRepository.findById(id))){
            throw new CustomException("조회한 공고가 존재하지 않습니다.");
        }
        jobsService.공고삭제(id, comp.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "공고 삭제 성공", null), HttpStatus.OK);
    }
}
// ⬜ 채용정보 "/jobs/info"
// ⬜ 공고 "/jobs/1"

// 🟦 공고등록 "/comp/jobs/write
// 🟦 공고수정 "/jobs/공고번호/update"