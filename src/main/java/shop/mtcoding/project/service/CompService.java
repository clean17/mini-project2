package shop.mtcoding.project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.config.exception.CustomException;
import shop.mtcoding.project.dto.comp.CompReq.CompJoinReqDto;
import shop.mtcoding.project.dto.comp.CompReq.CompLoginReqDto;
import shop.mtcoding.project.dto.comp.CompReq.CompUpdateReqDto;
import shop.mtcoding.project.dto.comp.CompResp.CompHomeOutDto;
import shop.mtcoding.project.dto.comp.CompResp.CompHomeOutDto.JobsManageJobsRespDto;
import shop.mtcoding.project.dto.comp.CompResp.CompHomeOutDto.ResumeMatchOutDto;
import shop.mtcoding.project.dto.comp.CompResp.CompLoginRespDto;
import shop.mtcoding.project.model.comp.Comp;
import shop.mtcoding.project.model.comp.CompRepository;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.resume.ResumeRepository;
import shop.mtcoding.project.util.DateUtil;
import shop.mtcoding.project.util.PathUtil;
import shop.mtcoding.project.util.Sha256;

@RequiredArgsConstructor
@Service
public class CompService {
    private final CompRepository compRepository;
    private final JobsRepository jobsRepository;
    private final ResumeRepository resumeRepository;

    @Transactional
    public CompJoinReqDto 회원가입(CompJoinReqDto compJoinReqDto) {
        Comp compPS = compRepository.findByCompEmail(compJoinReqDto.getEmail());
        if (compPS != null) {
            throw new CustomException("존재 하는 회원입니다.");
        }
        compJoinReqDto.setPassword(Sha256.encode(compJoinReqDto.getPassword()));
        Integer id = 0;
        try {
            compRepository.insert(compJoinReqDto);
            id = compJoinReqDto.getCompId();
        } catch (Exception e) {
            throw new CustomException("서버 에러가 발생 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Comp compPS2 = compRepository.findByCompId(id);
        compJoinReqDto.setCompId(compPS2.getCompId());
        compJoinReqDto.setCreatedAt(compPS2.getCreatedAt());
        return compJoinReqDto;
    }

    @Transactional(readOnly = true)
    public CompLoginRespDto 로그인(CompLoginReqDto compLoginReqDto) {
        compLoginReqDto.setPassword(Sha256.encode(compLoginReqDto.getPassword()));
        CompLoginRespDto principal = compRepository.findByEmailAndPassword2(compLoginReqDto.getEmail(),
                compLoginReqDto.getPassword());
        if (principal == null) {
            throw new CustomException("이메일 혹은 패스워드가 잘못 입력 되었습니다.");
        }
        return principal;
    }

    @Transactional
    public CompUpdateReqDto 회사정보수정(CompUpdateReqDto compUpdateReqDto, Integer compId) {
        if (compId != compUpdateReqDto.getCompId()) {
            throw new CustomApiException("정상적인 접근이 아닙니다.", HttpStatus.FORBIDDEN);
        }
        Comp compPS = compRepository.findByCompId(compUpdateReqDto.getCompId());
        if (compPS == null)
            throw new CustomException("존재하지 않는 회원입니다.");
        try {
            compRepository.updateByCompId(compUpdateReqDto);
        } catch (Exception e) {
            throw new CustomException("서버 에러가 발생 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return compUpdateReqDto;
    }

    @Transactional
    public String 프로필사진수정(MultipartFile photo, Integer compId) {

        String uuidImageName = PathUtil.writeImageFile(photo);

        Comp compPS = compRepository.findByCompId(compId);
        compPS.setPhoto(uuidImageName);
        try {
            compRepository.updatePhotoById(uuidImageName, compId);
        } catch (Exception e) {
            throw new CustomException("사진 수정에 실패 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidImageName;
    }

    public CompHomeOutDto 기업홈정보와매칭이력서(Comp comp) {
        CompHomeOutDto result = new CompHomeOutDto();
        Set<String> set = new HashSet<>(); // 기업 스킬 셋에 넣어야함
        List<JobsManageJobsRespDto> jDtos = jobsRepository.findByIdtoManageJobs(comp.getCompId());
        for (JobsManageJobsRespDto jDto : jDtos) {
            jDto.setLeftTime(DateUtil.dDay(jDto.getEndDate()));
            set.addAll(jDto.getSkillList());
        }
        List<String> sList = new ArrayList<>(set);
        result.setSkillList(sList);
        result.setJDto(jDtos);

        List<ResumeMatchOutDto> fiveMatchList = new ArrayList<>();
        List<ResumeMatchOutDto> fourMatchList = new ArrayList<>();
        List<ResumeMatchOutDto> threeMatchList = new ArrayList<>();
        List<ResumeMatchOutDto> twoMatchList = new ArrayList<>();
        List<ResumeMatchOutDto> oneMatchList = new ArrayList<>();

        List<ResumeMatchOutDto> rDtos = resumeRepository.findMatchResumeByCompId(comp.getCompId());
        for (ResumeMatchOutDto rDto : rDtos) {
            int count = 0;
            List<String> insertList = rDto.getSkillList();
            for (String skill : insertList) {
                if (set.contains(skill)) {
                    count++;
                }
            } 
            if (count >= 5) {
                fiveMatchList.add(rDto);
            } else if (count >= 4) {
                fourMatchList.add(rDto);
            } else if (count >= 3) {
                threeMatchList.add(rDto);
            } else if (count >= 2) {
                twoMatchList.add(rDto);
            } else if (count >= 1) {
                oneMatchList.add(rDto);
            }
            count = 0;
        }
        List<ResumeMatchOutDto> resultList = new ArrayList<>();
        resultList.addAll(fiveMatchList);
        resultList.addAll(fourMatchList);
        resultList.addAll(threeMatchList);
        resultList.addAll(twoMatchList);
        resultList.addAll(oneMatchList);
        result.setRDto(resultList);
        return result;
    }
}
