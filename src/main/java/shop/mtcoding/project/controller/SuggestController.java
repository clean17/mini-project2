package shop.mtcoding.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginComp;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.auth.LComp;
import shop.mtcoding.project.config.auth.LUser;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestReqDto;
import shop.mtcoding.project.dto.suggest.SuggestReq.SuggestUpdateReqDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestResumeOutDto;
import shop.mtcoding.project.service.SuggestService;

@Controller
@RequiredArgsConstructor
public class SuggestController {
    private final SuggestService suggestService;

    @PostMapping("/comp/suggest/jobs")
    public ResponseEntity<?> suggestJobs(@RequestBody SuggestReqDto sDto, @LoginComp LComp comp){
        SuggestResumeOutDto result = suggestService.제안하기(sDto, comp.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "제안 성공", result), HttpStatus.CREATED);
    }

    @PutMapping("/user/suggest/update")
    public ResponseEntity<?> updateSuggest(@RequestBody SuggestUpdateReqDto sDto, @LoginUser LUser user){
        if( !(sDto.getState() == 1 || sDto.getState() == -1)){
            throw new CustomApiException("상태정보가 다릅니다.");
        }
        if( sDto.getState() == 1){
            SuggestResumeOutDto result = suggestService.제안수락(sDto, user.getId());
            return new ResponseEntity<>(new ResponseDto<>(1, "제안을 수락했습니다.", result), HttpStatus.OK);
        }else{
            SuggestResumeOutDto result = suggestService.제안거절(sDto, user.getId());
            return new ResponseEntity<>(new ResponseDto<>(1, "제안을 거절했습니다.", result), HttpStatus.OK);
        }
    }
}
