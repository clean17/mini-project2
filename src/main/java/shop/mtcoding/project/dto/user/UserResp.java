package shop.mtcoding.project.dto.user;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.project.dto.apply.ApplyResp.ApplyStatusUserRespDto;
import shop.mtcoding.project.dto.suggest.SuggestResp.SuggestToCompRespDto;

public class UserResp {

    @Getter
    @Setter
    public static class UserApplyOutDto {
        private Integer userId;
        private List<ApplyStatusUserRespDto> applyDto;
        private List<SuggestToCompRespDto> suggestDto;

        @Getter
        @Setter
        public static class ApplyStatusUserRespDto{
            private Integer applyId; // apply_tb(1)
            private Integer state; // apply_tb(1)
            private JobsDto jobs;
            private ResumeDto resume;
            
            @Getter @Setter
            public static class JobsDto{
                private Integer jobsId; // apply_tb(1)
                private String jobsTitle; // jobs_tb(2) -> title
                private String position; // jobs_tb(2)
            }
            @Getter @Setter
            public static class ResumeDto{
                private Integer resumeId; // apply_tb(1)
                private String resumeTitle; // resume_tb(3) -> title
            }
        }

        @Getter
        @Setter
        public static class SuggestToCompRespDto {
            private Integer suggestId; // suggest_tb(1)
            private Integer resumeId; // suggest_tb(1)
            private Integer state; // suggest_tb(1)
            private JobsDto jobs;
            private CompDto comp;
            
            @Getter @Setter
            public static class JobsDto{
                private Integer jobsId; // suggest_tb(1)
                private String title; // jobs_tb(2)
                private String position; // jobs_tb(2)
            }
            @Getter @Setter
            public static class CompDto{
                private Integer CompId;
                private String name; // comp_tb(3) -> comp_name
            }
        }
    }

    @Getter
    @Setter
    public static class UserUpdatePhotoOutDto {
        private Integer userId;
        private String photo;

        @Builder
        public UserUpdatePhotoOutDto(Integer userId, String photo) {
            this.userId = userId;
            this.photo = photo;
        }

    }

    @Getter
    @Setter
    public static class UserLoginRespDto {
        private Integer userId;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class UserDataRespDto {
        private Integer userId;
        private String email;
        private String name;
        private String birth;
        private String tel;
        private String photo;
        private String address;

    }

    @Getter
    @Setter
    public static class UserUpdateRespDto {
        private Integer userId;
        private String email;
        private String password;
        private String name;
        private String birth;
        private String tel;
        private String address;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class UserDeleteRespDto {
        private Integer userId;
    }

    @Getter
    @Setter
    public static class UserSkillAndInterestDto {
        private List<String> skillList;
        private List<String> interestList;
    }

}
