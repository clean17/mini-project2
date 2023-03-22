package shop.mtcoding.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.interest.InterestReq.InterestChangeReqDto;
import shop.mtcoding.project.dto.interest.InterestResp.InterestChangeOutDto;
import shop.mtcoding.project.model.user.User;
import shop.mtcoding.project.service.InterestService;

@Controller
@RequiredArgsConstructor
public class InterestController {
    private final InterestService InterestService;
    
    @PutMapping("/user/interest/change")
    public ResponseEntity<?> changeInterest(@RequestBody InterestChangeReqDto iDto, @LoginUser User user){
        if(ObjectUtils.isEmpty(iDto.getUserId())){
            throw new CustomApiException("유저아이디가 필요합니다.");
        }
        InterestChangeOutDto interDto = InterestService.관심수정(iDto, user.getUserId());
        return new ResponseEntity<>(new ResponseDto<>(1, "업데이트 완료", interDto), HttpStatus.CREATED);
    }
}
