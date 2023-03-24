package shop.mtcoding.project.controller;

import javax.validation.Valid;

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
import shop.mtcoding.project.dto.apply.ApplyReq.ApplyReqDto;
import shop.mtcoding.project.dto.apply.ApplyReq.ApplyUpdateReqDto;
import shop.mtcoding.project.dto.apply.ApplyResp.ApplyOutDto;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.service.ApplyService;

@Controller
@RequiredArgsConstructor
public class ApplyController {
    private final ApplyService applyService;

    @PostMapping("/user/apply/resume")
    public ResponseEntity<?> applyResume(@RequestBody @Valid ApplyReqDto aDto, @LoginUser LUser user) {
        ApplyOutDto applyDto = applyService.지원하기(aDto, user.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "지원 성공", applyDto), HttpStatus.CREATED);
    }

    @PutMapping("/comp/apply/update")
    public ResponseEntity<?> updateApply(@RequestBody @Valid ApplyUpdateReqDto aDto, @LoginComp LComp comp) {
        if (!(aDto.getState() == 1 || aDto.getState() == -1)) {
            throw new CustomApiException("상태정보가 다릅니다.");
        }
        if (aDto.getState() == 1) {
            ApplyOutDto applyDto = applyService.합격(aDto, comp.getId());
            return new ResponseEntity<>(new ResponseDto<>(1, "지원 결과는 합격입니다.", applyDto), HttpStatus.OK);
        }else {
            ApplyOutDto applyDto = applyService.불합격(aDto, comp.getId());
            return new ResponseEntity<>(new ResponseDto<>(1, "지원 결과는 불합격입니다.", applyDto), HttpStatus.OK);
        }
    }
}
