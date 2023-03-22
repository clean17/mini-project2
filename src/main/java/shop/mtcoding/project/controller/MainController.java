package shop.mtcoding.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainOutDto;
import shop.mtcoding.project.dto.jobs.JobsResp.JobsMainRecommendRespDto;
import shop.mtcoding.project.dto.skill.RequiredSkillReq.RequiredSkillWriteReqDto;
import shop.mtcoding.project.model.jobs.JobsRepository;
import shop.mtcoding.project.model.skill.SkillRepository;
import shop.mtcoding.project.model.user.User;
import shop.mtcoding.project.util.DateUtil;

@Controller
public class MainController {

    @Autowired
    private HttpSession session;

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private SkillRepository skillRepository;

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
    public String main(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal != null) {
            List<JobsMainRecommendRespDto> rDtos = jobsRepository.findAlltoMainRecommend(principal.getUserId());
            for (JobsMainRecommendRespDto jDto : rDtos) {
                long dDay = DateUtil.dDay(jDto.getEndDate());
                jDto.setLeftTime(dDay);
                List<String> insertList = new ArrayList<>();
                for (RequiredSkillWriteReqDto skill : skillRepository.findByJobsSkill(jDto.getJobsId())) {
                    insertList.add(skill.getSkill());
                }
                jDto.setSkillList(insertList);
            }
            List<JobsMainRecommendRespDto> rDtos2 = jobsRepository.findAlltoMainRecommendRandom(principal.getUserId());
            for (JobsMainRecommendRespDto jDto : rDtos2) {
                long dDay = DateUtil.dDay(jDto.getEndDate());
                jDto.setLeftTime(dDay);
                List<String> insertList = new ArrayList<>();
                for (RequiredSkillWriteReqDto skill : skillRepository.findByJobsSkill(jDto.getJobsId())) {
                    insertList.add(skill.getSkill());
                }
                jDto.setSkillList(insertList);
                rDtos.add(jDto);
            }
            model.addAttribute("rDtos", rDtos);

            List<JobsMainOutDto> jDtos = jobsRepository.findAlltoMain(principal.getUserId());
            for (JobsMainOutDto jDto : jDtos) {
                long dDay = DateUtil.dDay(jDto.getEndDate());
                jDto.setLeftTime(dDay);
                List<String> insertList = new ArrayList<>();
                for (RequiredSkillWriteReqDto skill : skillRepository.findByJobsSkill(jDto.getJobsId())) {
                    insertList.add(skill.getSkill());
                }
                jDto.setSkillList(insertList);
            }
            model.addAttribute("jDtos", jDtos);
        } else {
            List<JobsMainOutDto> jDtost = jobsRepository.findAlltoMain(null);
            for (JobsMainOutDto jDto : jDtost) {
                long dDay = DateUtil.dDay(jDto.getEndDate());
                jDto.setLeftTime(dDay);
                List<String> insertList = new ArrayList<>();
                for (RequiredSkillWriteReqDto skill : skillRepository.findByJobsSkill(jDto.getJobsId())) {
                    insertList.add(skill.getSkill());
                }
                jDto.setSkillList(insertList);
            }
            // 랜덤으로 공고에서 몇개만 추려서 상단에 뿌려도 괜찮을듯 ??
            model.addAttribute("rDtos", jDtost);

            List<JobsMainOutDto> jDtosb = jobsRepository.findAlltoMain(null);
            for (JobsMainOutDto jDto : jDtosb) {
                long dDay = DateUtil.dDay(jDto.getEndDate());
                jDto.setLeftTime(dDay);
                List<String> insertList = new ArrayList<>();
                for (RequiredSkillWriteReqDto skill : skillRepository.findByJobsSkill(jDto.getJobsId())) {
                    insertList.add(skill.getSkill());
                }
                jDto.setSkillList(insertList);
            }
            model.addAttribute("jDtos", jDtosb);
        }
        return "main/main";
    }
}

// ⬜ 메인 "/"
// ⬜ 고객센터 "/help"
