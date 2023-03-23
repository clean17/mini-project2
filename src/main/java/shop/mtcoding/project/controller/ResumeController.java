package shop.mtcoding.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.annotation.LoginComp;
import shop.mtcoding.project.config.annotation.LoginUser;
import shop.mtcoding.project.config.exception.CustomException;
import shop.mtcoding.project.dto.apply.ApplyResp.ApplytoCompRespDto;
import shop.mtcoding.project.dto.common.ResponseDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeCheckboxReqDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeUpdateInDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeUpdateReqDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteOutDto;
import shop.mtcoding.project.dto.resume.ResumeReq.ResumeWriteReqDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeDetailRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeManageRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeSaveRespDto;
import shop.mtcoding.project.dto.resume.ResumeResp.ResumeSearchRespDto;
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
@RequiredArgsConstructor
public class ResumeController {

    private final UserRepository userRepository;
    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final SkillRepository skillRepository;
    private final SuggestRepository suggestRepository;
    private final ApplyRepository applyRepository;
    private final HttpSession session;

    // 완료
    @DeleteMapping("/resume/{id}/delete")
    public ResponseEntity<?> deleteResume(@LoginUser User user, @PathVariable int id) {
        resumeService.이력서삭제(id, user.getUserId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/user/resume") // 이력서관리
    public ResponseEntity<?> manageResume(@LoginUser User user) {

        List<ResumeManageRespDto> rLists = resumeRepository.findAllByUserId(user.getUserId());

        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 목록 보기 성공", rLists), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/user/request/resume") // 공고에 지원할 이력서 불러오기
    public ResponseEntity<?> requestResume(@LoginUser User user) {
        List<ResumeManageRespDto> rDtos = resumeRepository.findAllByUserId(user.getUserId());
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 불러오기 성공", rDtos), HttpStatus.OK);
    }

    // 완료
    @PostMapping("/user/resume/write")
    public @ResponseBody ResponseEntity<?> writeResume(@LoginUser User user,
            @Valid ResumeWriteReqDto resumeWriteReqDto) {

        ResumeWriteOutDto rDto = resumeService.이력서쓰기(resumeWriteReqDto, user.getUserId());

        return new ResponseEntity<>(new ResponseDto<>(1, "저장 완료!", rDto), HttpStatus.CREATED);
    }

    //완료
    @PutMapping("/user/resume/update")
    public ResponseEntity<?> saveTempResume(@LoginUser User user,
            @Valid ResumeUpdateReqDto resumeUpdateReqDto) {

        ResumeUpdateInDto rDto = resumeService.이력서수정(resumeUpdateReqDto, user.getUserId());

        return new ResponseEntity<>(new ResponseDto<>(1, "수정 완료!", rDto), HttpStatus.CREATED);
    }

    // 완료
    @GetMapping("/user/resume/write")
    @ResponseBody
    public ResponseEntity<?> writeResumeForm(@LoginUser User user) {
        UserDataRespDto userPS = userRepository.findByUserId(user.getUserId());
        return ResponseEntity.ok().body(userPS);
    }
    //완료
    @GetMapping("/user/resume/{id}/update")
    public ResponseEntity<?> updateResumeForm(@LoginUser User user, @PathVariable Integer id) {
        if (user == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        ResumeSaveRespDto rDto = resumeRepository.findById(id);

        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 수정 정보", rDto), HttpStatus.OK);
    }

    // 완료
    @GetMapping("/resume/{id}")
    public ResponseEntity<?> resumeDetail(@PathVariable Integer id, @LoginComp Comp comp) {
        if (ObjectUtils.isEmpty(resumeRepository.findByResumeId(id))) {
            throw new CustomException("존재하지 않는 이력서 입니다.");
        }
        ResumeDetailRespDto rDto;
        Integer num = null;

        if (comp != null)
            num = comp.getCompId();
        rDto = resumeRepository.findDetailPublicResumebyById(id, num, num);
        return new ResponseEntity<>(new ResponseDto<>(1, "이력서 상세보기 완료", rDto), HttpStatus.OK);
    }

    @GetMapping("/comp/resume/search")
    public ResponseEntity<?> searchCheckbox(ResumeCheckboxReqDto rDto) {
        if (rDto.getCareer() == null || rDto.getCareer().isEmpty()) {
            rDto.setCareer("");
        }
        List<ResumeSearchRespDto> rDtos = resumeRepository.findResumeByCheckBox(rDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "검색 성공", rDtos), HttpStatus.OK);
    }

    @GetMapping("/comp/resume/apply/{id}")
    public ResponseEntity<?> applyResumeDetail(@LoginComp Comp comp, @PathVariable Integer id) {
        if (id == null) {
            throw new CustomException("지원한 아이디가 필요합니다.");
        }
        Apply applyPS = applyRepository.findByApplyId(id);
        if (applyPS == null) {
            throw new CustomException("지원 결과 데이터가 없습니다.");
        }
        ResumeDetailRespDto rDto = resumeRepository.findDetailPublicResumebyById(applyPS.getApplyId(), applyPS.getResumeId(),
        comp.getCompId());

        // if (comp != null) {
        // try {
        // rDto.setSuggestState(suggestRepository
        // .findByCompIdAndResumeId(comp.getCompId(),
        // applyPS.getResumeId()).getState());
        // } catch (Exception e) {

        // }
        // try {
        // ApplytoCompRespDto aDto =
        // applyRepository.findByCompIdAndApplyId(comp.getCompId(), id);
        // rDto.setApplyState(aDto.getState());
        // rDto.setApplyId(aDto.getApplyId());
        // } catch (Exception e) {
        // }
        // }
        return new ResponseEntity<>(new ResponseDto<>(1, "", rDto), HttpStatus.OK);
    }

}

// 🟨 공개하면 기업이 접근 가능 🟨
// 🟨 이력서번호 "/resume/이력서번호"

// 🟩 🔐 유저권한필요 🔐 🟩
// 🟩 이력서관리 "/user/resume"
// 🟩 이력서작성 "/user/resume/write"
// 🟩 이력서수정 "/user/resume/이력서번호/update"