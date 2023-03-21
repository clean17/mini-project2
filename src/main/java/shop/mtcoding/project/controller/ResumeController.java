package shop.mtcoding.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.config.exception.CustomException;
import shop.mtcoding.project.dto.apply.ApplyResp.ApplytoCompRespDto;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeCheckboxReqDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeUpdateReqDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteOutDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteReqDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeDetailRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeManageRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeSaveRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeSearchRespDto;
import shop.mtcoding.project.dto.skill.ResumeSkillResp.ResumeSkillRespDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestToCompRespIdDto;
import shop.mtcoding.project.dto.user.UserResp.UserDataRespDto;
import shop.mtcoding.project.model.apply.Apply;
import shop.mtcoding.project.model.apply.ApplyRepository;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.resume.ResumeRepository;
import shop.mtcoding.project.model.skill.SkillRepository;
import shop.mtcoding.project.model.suggest.SuggestRepository;
import shop.mtcoding.project.model.user.User;
import shop.mtcoding.project.model.user.UserRepository;
import shop.mtcoding.project.service.ResumeService;

@Controller
public class ResumeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SuggestRepository suggestRepository;

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private HttpSession session;

    @DeleteMapping("/resume/{id}/delete")
    public ResponseEntity<?> deleteResume(@PathVariable int id) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        resumeService.이력서삭제(id, principal.getUserId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);
    }

    @GetMapping("/user/resume") // 이력서관리
    public String manageResume(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        List<ResumeManageRespDto> rLists = resumeRepository.findAllByUserId(principal.getUserId());
        for (ResumeManageRespDto rList : rLists) {
            List<String> insertList = new ArrayList<>();
            for (ResumeSkillRespDto skill : skillRepository.findByResumeSkill(rList.getResumeId())) {
                insertList.add(skill.getSkill());
                rList.setSkillList(insertList);
            }
        }
        model.addAttribute("rDtos", rLists);
        User userPS = userRepository.findById(principal.getUserId());
        model.addAttribute("user", userPS);
        // rList.forEach((s)->{System.out.println("테스트 : "+ s.toString());});
        return "resume/manageResume";
    }

    @GetMapping("/user/request/resume") // 공고에 지원할 이력서 불러오기
    public ResponseEntity<?> requestResume(Model model) {
        User principal = (User) session.getAttribute("principal");
        List<ResumeManageRespDto> rDtos = resumeRepository.findAllByUserId(principal.getUserId());
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 불러오기 성공", rDtos), HttpStatus.OK);
    }

    @PostMapping("/user/resume/write")
    public ResponseEntity<?> writeResume(@LoginUser User user, @Valid ResumeWriteReqDto resumeWriteReqDto) {

        ResumeWriteOutDto rDto = resumeService.이력서쓰기(resumeWriteReqDto, user.getUserId());

        return new ResponseEntity<>(new ResponseDto<>(1, "저장 완료!", rDto), HttpStatus.CREATED);
    }

    @PutMapping("/user/resume/update")
    public ResponseEntity<?> saveTempResume(@RequestBody ResumeUpdateReqDto resumeWriteReqDto) {

        // System.out.println("테스트 : "+ resumeWriteReqDto.toString());

        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (resumeWriteReqDto.getEducation() == null || resumeWriteReqDto.getEducation().isEmpty()) {
            throw new CustomApiException("학력을 입력해주세요");
        }
        if (resumeWriteReqDto.getCareer() == null || resumeWriteReqDto.getCareer().isEmpty()) {
            throw new CustomApiException("경력을 입력해주세요");
        }
        if (resumeWriteReqDto.getTitle() == null || resumeWriteReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("제목을 입력해주세요");
        }
        if (!(resumeWriteReqDto.getState() == 0 || resumeWriteReqDto.getState() == 1)) {
            throw new CustomApiException("공개여부를 선택해주세요");
        }
        if (ObjectUtils.isEmpty(resumeWriteReqDto.getSkillList())) {
            throw new CustomApiException("기술을 선택해주세요");
        }

        resumeService.이력서수정(resumeWriteReqDto, principal.getUserId());

        return new ResponseEntity<>(new ResponseDto<>(1, "저장 완료!", null), HttpStatus.CREATED);
    }

    @GetMapping("/user/resume/write")
    @ResponseBody
    public ResponseEntity<?> writeResumeForm(@LoginUser User user, Model model) {
        UserDataRespDto userPS = userRepository.findByUserId(user.getUserId());
        model.addAttribute("rDto", userPS);
        User userPS1 = userRepository.findById(user.getUserId());
        model.addAttribute("user", userPS1);
        return ResponseEntity.ok().body(userPS);
    }

    @GetMapping("/user/resume/{id}/update")
    public String updateResumeForm(@PathVariable Integer id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        ResumeSaveRespDto rDto = resumeRepository.findById(id);
        List<String> insertList = new ArrayList<>();
        for (ResumeSkillRespDto skill : skillRepository.findByResumeSkill(rDto.getResumeId())) {
            insertList.add(skill.getSkill());
            rDto.setSkillList(insertList);
        }
        model.addAttribute("rDto", rDto);

        return "resume/updateResumeForm";
    }

    @GetMapping("/resume/{id}")
    public String resumeDetail(@PathVariable Integer id, Model model) {
        if (ObjectUtils.isEmpty(resumeRepository.findByResumeId(id))) {
            throw new CustomException("존재하지 않는 이력서 입니다.");
        }

        Comp compSession = (Comp) session.getAttribute("compSession");
        ResumeDetailRespDto rDto;
        if (compSession != null) {
            rDto = resumeRepository.findDetailPublicResumebyById(id, compSession.getCompId());
        } else {
            rDto = resumeRepository.findDetailPublicResumebyById(id, null);
        }
        List<String> insertList = new ArrayList<>();
        for (ResumeSkillRespDto skill : skillRepository.findByResumeSkill(rDto.getResumeId())) {
            insertList.add(skill.getSkill());
            rDto.setSkillList(insertList);
        }

        compSession = (Comp) session.getAttribute("compSession");
        if (compSession != null) {
            try {
                SuggestToCompRespIdDto sDto = suggestRepository.findByCompIdAndResumeId(compSession.getCompId(), id);
                rDto.setSuggestState(sDto.getState());
            } catch (Exception e) {
            }
        }
        model.addAttribute("rDto", rDto);
        return "/resume/resumeDetail";
    }

    @GetMapping("/comp/resume/search")
    public ResponseEntity<?> searchCheckbox(ResumeCheckboxReqDto rDto, Model model) {
        if (rDto.getCareer() == null || rDto.getCareer().isEmpty()) {
            rDto.setCareer("");
        }
        List<ResumeSearchRespDto> rDtos = resumeRepository.findResumeByCheckBox(rDto);
        for (ResumeSearchRespDto rDto1 : rDtos) {
            List<String> insertList = new ArrayList<>();
            for (ResumeSkillRespDto skill : skillRepository.findByResumeSkill(rDto1.getResumeId())) {
                insertList.add(skill.getSkill());
            }
            rDto1.setSkillList(insertList);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "검색 성공", null), HttpStatus.OK);
    }

    @GetMapping("/comp/resume/apply/{id}")
    public String applyResumeDetail(@PathVariable Integer id, Model model) {
        if (id == null) {
            throw new CustomException("지원한 아이디가 필요합니다.");
        }
        Apply applyPS = applyRepository.findByApplyId(id);
        if (applyPS == null) {
            throw new CustomException("지원 결과 데이터가 없습니다.");
        }
        Comp compSession = (Comp) session.getAttribute("compSession");
        ResumeDetailRespDto rDto = resumeRepository.findDetailPublicResumebyById(applyPS.getResumeId(),
                compSession.getCompId());
        List<String> insertList = new ArrayList<>();

        for (ResumeSkillRespDto skill : skillRepository.findByResumeSkill(rDto.getResumeId())) {
            insertList.add(skill.getSkill());
            rDto.setSkillList(insertList);
        }

        if (compSession != null) {
            try {
                rDto.setSuggestState(suggestRepository
                        .findByCompIdAndResumeId(compSession.getCompId(), applyPS.getResumeId()).getState());
            } catch (Exception e) {

            }
            try {
                ApplytoCompRespDto aDto = applyRepository.findByCompIdAndApplyId(compSession.getCompId(), id);
                rDto.setApplyState(aDto.getState());
                rDto.setApplyId(aDto.getApplyId());
            } catch (Exception e) {
            }
        }
        model.addAttribute("rDto", rDto);
        return "/resume/resumeDetail";
    }

}

// 🟨 공개하면 기업이 접근 가능 🟨
// 🟨 이력서번호 "/resume/이력서번호"

// 🟩 🔐 유저권한필요 🔐 🟩
// 🟩 이력서관리 "/user/resume"
// 🟩 이력서작성 "/user/resume/write"
// 🟩 이력서수정 "/user/resume/이력서번호/update"