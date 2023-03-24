package shop.mtcoding.project.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.auth.JwtProvider;
import shop.mtcoding.project.config.auth.LUser;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.interest.InterestResp.InterestChangeRespDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainRecommendRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeManageRespDto;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapRespDto;

import shop.mtcoding.project.dto.skill.RequiredSkillReq.RequiredSkillWriteReqDto;
import shop.mtcoding.project.dto.skill.ResumeSkillResp.ResumeSkillRespDto;

import shop.mtcoding.project.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserLoginReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserPasswordReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserUpdateReqDto;
import shop.mtcoding.project.dto.user.UserResp.UserApplyOutDto;
import shop.mtcoding.project.dto.user.UserResp.UserHomeOutDto;
import shop.mtcoding.project.dto.user.UserResp.UserLoginRespDto;
import shop.mtcoding.project.dto.user.UserResp.UserUpdatePhotoOutDto;
import shop.mtcoding.project.dto.user.UserResp.UserUpdateRespDto;
import shop.mtcoding.project.model.apply.ApplyRepository;
import shop.mtcoding.project.model.interest.InterestRepository;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.resume.ResumeRepository;
import shop.mtcoding.project.model.scrap.ScrapRepository;
import shop.mtcoding.project.model.skill.SkillRepository;
import shop.mtcoding.project.model.suggest.SuggestRepository;
import shop.mtcoding.project.model.user.User;
import shop.mtcoding.project.model.user.UserRepository;
import shop.mtcoding.project.service.UserService;
import shop.mtcoding.project.util.CheckValid;
import shop.mtcoding.project.util.DateUtil;
import shop.mtcoding.project.util.Sha256;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final HttpSession session;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ApplyRepository applyRepository;
    private final SuggestRepository suggestRepository;
    private final ScrapRepository scrapRepository;
    private final ResumeRepository resumeRepository;
    private final SkillRepository skillRepository;
    private final InterestRepository interestRepository;
    private final JobsRepository jobsRepository;

    // 완료
    @PostMapping("/userjoin")
    public @ResponseBody ResponseEntity<?> join(@Valid UserJoinReqDto userJoinReqDto, BindingResult bindingResult) {
        UserJoinReqDto userJoinOutDto = userService.회원가입(userJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입완료", userJoinOutDto), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/user/emailCheck")
    public @ResponseBody ResponseEntity<?> sameEmailCheck(String email) {
        CheckValid.inNullApi(email, "이메일을 입력해주세요.");
        User userPS = userRepository.findByUserEmail(email);
        if (userPS != null) {
            throw new CustomApiException("동일한 email이 존재합니다.");
        }
        // CheckValid.inNullApi(userPS, "동일한 email이 존재합니다.");
        return new ResponseEntity<>(new ResponseDto<>(1, "해당 email은 사용 가능합니다.", null), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/userjoin")
    public String joinForm() {
        return "user/joinForm";
    }

    // 완료
    @GetMapping("/userlogin")
    public String loginForm() {
        return "user/loginForm";
    }

    // 완료
    @PostMapping("/userlogin")
    public @ResponseBody ResponseEntity<?> login(@Valid UserLoginReqDto userloginReqDto, BindingResult bindingResult,
            HttpServletResponse httpServletResponse) {
        UserLoginRespDto principal = userService.로그인(userloginReqDto);

        if (principal == null) {
            throw new CustomApiException("존재하지 않는 회원입니다.");
        } else {
            if (userloginReqDto.getRememberEmail() == null) {
                userloginReqDto.setRememberEmail("");
            }
            if (userloginReqDto.getRememberEmail().equals("on")) {
                Cookie cookie = new Cookie("rememberEmail", userloginReqDto.getEmail());
                httpServletResponse.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("remember", "");
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
            }
            Optional<User> userOP = userRepository.findByEmailAndPassword(userloginReqDto.getEmail(),
                    userloginReqDto.getPassword());
            String jwt ;
            if (userOP.isPresent()) { // 값이 존재하면
                jwt = JwtProvider.create(userOP.get());
                return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body(new ResponseDto<>(1, "로그인 성공", principal)); // 계정 있으면 토큰 리턴
            } else {
                return ResponseEntity.badRequest().build();
            }
            // session.setAttribute("principal", user);
            // return new ResponseEntity<>(new ResponseDto<>(1, "로그인 성공", principal), HttpStatus.OK);
        }
    }

    // 완료
    @PostMapping("/userlogin2")
    public ResponseEntity<?> login2(@RequestBody @Valid UserLoginReqDto userloginReqDto,
            HttpServletResponse httpServletResponse) {
        UserLoginRespDto principal = userService.ajax로그인(userloginReqDto);

        if (principal != null) {
            if (userloginReqDto.getRememberEmail() == null) {
                userloginReqDto.setRememberEmail("");
            }
            if (userloginReqDto.getRememberEmail().equals("on")) {
                Cookie cookie = new Cookie("rememberEmail", userloginReqDto.getEmail());
                httpServletResponse.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("remember", "");
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
            }
            session.setAttribute("principal", principal);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 성공", null), HttpStatus.OK);
    }

    // 완료
    @PostMapping("/user/passwordCheck")
    public @ResponseBody ResponseEntity<?> samePasswordCheck(@RequestBody UserPasswordReqDto userPasswordReqDto) {
        userPasswordReqDto.setPassword(Sha256.encode(userPasswordReqDto.getPassword()));
        User userPS = userRepository.findByUseridAndPassword(
                userPasswordReqDto.getUserId(),
                userPasswordReqDto.getPassword());
        if (userPS == null) {
            throw new CustomApiException("비밀번호가 틀렸습니다.");
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "인증에 성공하였습니다.",
                null), HttpStatus.OK);
    }

    // 완료
    @PutMapping("/user/update")
    public @ResponseBody ResponseEntity<?> updateUser(@LoginUser LUser user,
            @RequestBody @Valid UserUpdateReqDto userUpdateReqDto, BindingResult bindingResult) {
        userUpdateReqDto.setPassword(Sha256.encode(userUpdateReqDto.getPassword()));

        UserUpdateReqDto userPS = userService.개인정보수정(userUpdateReqDto, user.getId());
        User principal = userRepository.findById(userPS.getUserId());
        session.setAttribute("principal", principal);
        return new ResponseEntity<>(new ResponseDto<>(1, "수정완료", userPS), HttpStatus.OK);

    }

    // 완료
    @GetMapping("/user/update")
    public @ResponseBody ResponseEntity<?> updateForm(@LoginUser LUser user, UserUpdateReqDto userUpdateReqDto) {
        UserUpdateRespDto userPS = userRepository.findById1(user.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 수정 완료", userPS), HttpStatus.OK);
    }


    // 완료
    @GetMapping("/user/myhome")
    public @ResponseBody ResponseEntity<?> myhome(@LoginUser User user) {
        UserHomeOutDto userResult = userService.마이홈조회(user);
        return new ResponseEntity<>(new ResponseDto<>(1, "마이홈 보기 성공", userResult), HttpStatus.OK);
    }
    
 
    // 완료
    @GetMapping("/user/scrap")
    public @ResponseBody ResponseEntity<?> scarp(@LoginUser LUser user) {
        List<UserScrapRespDto> usDtos = scrapRepository.findAllScrapByUserId(user.getId());
        for (UserScrapRespDto usDto : usDtos) {
            long dDay = DateUtil.dDay(usDto.getJobs().getEndDate());
            usDto.setLeftTime(dDay);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "스크랩 보기", usDtos), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/user/offer")
    public @ResponseBody ResponseEntity<?> offer(@LoginUser User user) {
        UserApplyOutDto result = userRepository.findApplyAndSuggestByUserId(user.getUserId());

        return new ResponseEntity<>(new ResponseDto<>(1, "지원 및 제안 보기", result), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // 완료
    @GetMapping("/user/profileUpdateForm")
    public @ResponseBody ResponseEntity<?> profileUpdateForm(@LoginUser LUser user) {
        UserUpdatePhotoOutDto userPS = userRepository.findByUserPhoto(user.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 수정 완료", userPS), HttpStatus.OK);
    }

    // 완료
    @PutMapping("/user/profileUpdate")
    public @ResponseBody ResponseEntity<?> profileUpdate(@LoginUser LUser user, MultipartFile photo) throws Exception {
        CheckValid.inNullApi(photo, "사진이 전송 되지 않았습니다.");
        String result = userService.프로필사진수정(photo, user.getId());
        // user.setPhoto(result);  //  dto 에 사진 확인 필요
        UserUpdatePhotoOutDto update = UserUpdatePhotoOutDto.builder()
                .userId(user.getId())
                .photo(result)
                .build();
        session.setAttribute("principal", user);
        return new ResponseEntity<>(new ResponseDto<>(1, "프로필 수정 성공", update),
                HttpStatus.OK);
    }
}

// ⬜ 회원가입 "/user/join"
// ⬜ 로그인 "/user/login"
// ⬜ 관심기업 "/user/interest"

// 🟩 🔐 유저권한필요 🔐 🟩
// 🟩 유저홈 "/user/myhome"
// 🟩 회원수정 "/user/update"
// 🟩 스크랩 "/user/scrap"
// 🟩 지원 및 받은제안 "/user/offer"