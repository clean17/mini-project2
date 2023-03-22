package shop.mtcoding.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainOutDto;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.skill.SkillRepository;
import shop.mtcoding.project.model.user.User;
import shop.mtcoding.project.service.UserService;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping("/sample")
    public String sample() {
    return "main/sample";
    }

    @GetMapping("/jusoPopup")
    public String jusoPopup1() {
        return "main/jusoPopup";
    }

    @PostMapping("/jusoPopup")
    public String jusoPopup() {
        return "main/jusoPopup";
    }

    @GetMapping("/testtest")
    public String  testtest(){
        return "testtest";
    }

    @GetMapping("/")
    public ResponseEntity<?> main(@LoginUser User user) {
        List<JobsMainOutDto> result = userService.메인화면공고(user);
        return new ResponseEntity<>(new ResponseDto<>(1, "메인 공고 조회 성공", result), HttpStatus.OK);
    }
}

// ⬜ 메인 "/"
// ⬜ 고객센터 "/help"
