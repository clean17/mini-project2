package shop.mtcoding.project.controller;

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
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.user.UserReq.UserJoinReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserLoginReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserPasswordReqDto;
import shop.mtcoding.project.dto.user.UserReq.UserUpdateReqDto;
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
    @PostMapping("/user/join")
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
    @GetMapping("/user/join")
    public String joinForm() {
        return "user/joinForm";
    }

    // 완료
    @GetMapping("/user/login")
    public String loginForm() {
        return "user/loginForm";
    }

    // 완료
    @PostMapping("/user/login")
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
            User user = userRepository.findByEmailAndPassword(userloginReqDto.getEmail(),
                    userloginReqDto.getPassword());
            session.setAttribute("principal", user);
            return new ResponseEntity<>(new ResponseDto<>(1, "로그인 성공", principal), HttpStatus.OK);

        }
    }

    // 완료
    @PostMapping("/user/login2")
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
    public @ResponseBody ResponseEntity<?> updateUser(@LoginUser User user,
            @RequestBody @Valid UserUpdateReqDto userUpdateReqDto, BindingResult bindingResult) {
        userUpdateReqDto.setPassword(Sha256.encode(userUpdateReqDto.getPassword()));

        UserUpdateReqDto userPS = userService.개인정보수정(userUpdateReqDto, user.getUserId());
        User principal = userRepository.findById(userPS.getUserId());
        session.setAttribute("principal", principal);
        return new ResponseEntity<>(new ResponseDto<>(1, "수정완료", userPS), HttpStatus.OK);

    }

    // 완료
    @GetMapping("/user/update")
    public @ResponseBody ResponseEntity<?> updateForm(@LoginUser User user, UserUpdateReqDto userUpdateReqDto) {
        UserUpdateRespDto userPS = userRepository.findById1(user.getUserId());
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 수정 완료", userPS), HttpStatus.OK);
    }

    // @GetMapping("/user/myhome")
    // public String myhome(Model model) {
    // User principal = (User) session.getAttribute("principal");
    // if (principal == null) {
    // return "redirect:/user/login";
    // }
    // List<ResumeManageRespDto> rLists =
    // resumeRepository.findAllByUserId(principal.getUserId());
    // for (ResumeManageRespDto rList : rLists) {
    // List<String> insertList = new ArrayList<>();
    // for (ResumeSkillRespDto skill :
    // skillRepository.findByResumeSkill(rList.getResumeId())) {
    // insertList.add(skill.getSkill());
    // rList.setSkillList(insertList);
    // }
    // }
    // List<InterestChangeRespDto> iDtos =
    // interestRepository.findById(principal.getUserId());
    // model.addAttribute("iDtos", iDtos);
    // model.addAttribute("rDtos", rLists);
    // User userPS = userRepository.findById(principal.getUserId());
    // model.addAttribute("user", userPS);

    // List<JobsMainRecommendRespDto> rDtos =
    // jobsRepository.findAlltoMainRecommend(principal.getUserId());
    // for (JobsMainRecommendRespDto jDto : rDtos) {
    // try {
    // jDto.setUserScrapId(scrapRepository
    // .findScrapIdByUserIdAndJobsId(principal.getUserId(),
    // jDto.getJobsId()).getUserScrapId());
    // } catch (Exception e) {
    // }
    // long dDay = DateUtil.dDay(jDto.getEndDate());
    // jDto.setLeftTime(dDay);
    // List<String> insertList = new ArrayList<>();
    // for (RequiredSkillWriteReqDto skill :
    // skillRepository.findByJobsSkill(jDto.getJobsId())) {
    // insertList.add(skill.getSkill());
    // }

    // jDto.setSkillList(insertList);
    // }
    // List<JobsMainRecommendRespDto> rDtos2 =
    // jobsRepository.findAlltoMainRecommendRandom(principal.getUserId());
    // for (JobsMainRecommendRespDto jDto : rDtos2) {
    // try {
    // jDto.setUserScrapId(scrapRepository
    // .findScrapIdByUserIdAndJobsId(principal.getUserId(),
    // jDto.getJobsId()).getUserScrapId());
    // } catch (Exception e) {
    // }
    // long dDay = DateUtil.dDay(jDto.getEndDate());
    // jDto.setLeftTime(dDay);
    // List<String> insertList = new ArrayList<>();
    // for (RequiredSkillWriteReqDto skill :
    // skillRepository.findByJobsSkill(jDto.getJobsId())) {
    // insertList.add(skill.getSkill());
    // }
    // jDto.setSkillList(insertList);
    // rDtos.add(jDto);
    // }
    // model.addAttribute("jDtos", rDtos);

    // return "user/myhome";
    // }

    // @GetMapping("/user/scrap")
    // public String scarp(Model model) {
    // User principal = (User) session.getAttribute("principal");
    // if (principal != null) {
    // List<UserScrapRespDto> usDtos =
    // scrapRepository.findAllScrapByUserId(principal.getUserId());
    // for (UserScrapRespDto usDto : usDtos) {
    // long dDay = DateUtil.dDay(usDto.getEndDate());
    // usDto.setLeftTime(dDay);
    // List<String> insertList = new ArrayList<>();
    // for (RequiredSkillWriteReqDto skill :
    // skillRepository.findByJobsSkill(usDto.getJobsId())) {
    // insertList.add(skill.getSkill());
    // }
    // usDto.setSkillList(insertList);
    // }
    // model.addAttribute("usDtos", usDtos);
    // }
    // User userPS = userRepository.findById(principal.getUserId());
    // model.addAttribute("user", userPS);
    // return "user/scrap";
    // }

    // @GetMapping("/user/offer")
    // public String offer(Model model) {
    // User principal = (User) session.getAttribute("principal");
    // List<ApllyStatusUserRespDto> aDtos =
    // applyRepository.findAllByUserIdtoApply(principal.getUserId());
    // model.addAttribute("aDtos", aDtos);
    // List<SuggestToUserRespDto> sDtos =
    // suggestRepository.findAllGetOfferByUserId(principal.getUserId());
    // model.addAttribute("sDtos", sDtos);
    // User userPS = userRepository.findById(principal.getUserId());
    // model.addAttribute("user", userPS);
    // return "user/offer";
    // }

    // 완료
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // 완료
    @GetMapping("/user/profileUpdateForm")
    public @ResponseBody ResponseEntity<?> profileUpdateForm(@LoginUser User user) {
    UserUpdatePhotoOutDto userPS = userRepository.findByUserPhoto(user.getUserId());
    return new ResponseEntity<>(new ResponseDto<>(1, "회원 수정 완료", userPS), HttpStatus.OK);
    }

    // 완료
    @PutMapping("/user/profileUpdate")
    public @ResponseBody ResponseEntity<?> profileUpdate(@LoginUser User user, MultipartFile photo) throws Exception {
        CheckValid.inNullApi(photo, "사진이 전송 되지 않았습니다.");
        String result = userService.프로필사진수정(photo, user.getUserId());
        user.setPhoto(result);
        UserUpdatePhotoOutDto update = UserUpdatePhotoOutDto.builder()
                .userId(user.getUserId())
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