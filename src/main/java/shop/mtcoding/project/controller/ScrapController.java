package shop.mtcoding.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginComp;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.scrap.CompScrapReq.CompInsertScrapReqDto;
import shop.mtcoding.project.dto.scrap.CompScrapResp.CompScrapOutDto;
import shop.mtcoding.project.dto.scrap.UserScrapReq.UserInsertScrapReqDto;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapOutDto;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.user.User;
import shop.mtcoding.project.service.ScrapService;

@Controller
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/user/scrap/insert")
    public ResponseEntity<?> insertJobsScrap(@RequestBody UserInsertScrapReqDto sDto, @LoginUser User user) {
        if (sDto.getJobsId() == null) throw new CustomApiException("공고 번호가 필요합니다.");
        UserScrapOutDto result = scrapService.공고스크랩(user.getUserId(), sDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "공고 스크랩 완료", result), HttpStatus.OK);
    }

    @DeleteMapping("/user/scrap/{id}/delete")
    public ResponseEntity<?> deleteJobsScrap(@PathVariable Integer id, @LoginUser User user) {
        if (id == null) throw new CustomApiException("스크랩 번호가 필요합니다.");
        scrapService.공고스크랩삭제(user.getUserId(), id);
        return new ResponseEntity<>(new ResponseDto<>(1, "공고 스크랩 삭제 완료", null), HttpStatus.OK);
    }

    @PostMapping("/comp/scrap/insert")
    public ResponseEntity<?> insertResumeScrap(@RequestBody CompInsertScrapReqDto sDto, @LoginComp Comp comp) {
        if (sDto.getResumeId() == null) throw new CustomApiException("이력서 번호가 필요합니다.");
        CompScrapOutDto result = scrapService.이력서스크랩(comp.getCompId(), sDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 스크랩 완료", result), HttpStatus.OK);
    }

    @DeleteMapping("/comp/scrap/{id}/delete")
    public ResponseEntity<?> deleteResumeScrap(@PathVariable Integer id, @LoginComp Comp comp) {
        if (id == null) throw new CustomApiException("스크랩 번호가 필요합니다.");
        scrapService.이력서스크랩삭제(comp.getCompId(), id);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 스크랩 삭제 완료", null), HttpStatus.OK);
    }
}
