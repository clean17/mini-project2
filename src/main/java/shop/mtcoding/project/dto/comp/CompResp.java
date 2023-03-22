package shop.mtcoding.project.dto.comp;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CompResp {

    @Getter
    @Setter
    public static class CompHomeOutDto {
        private List<JobsManageJobsRespDto> jDto;
        private List<ResumeMatchOutDto> rDto;
        private List<String> skillList;

        @Getter
        @Setter
        public static class JobsManageJobsRespDto {
            private Integer jobsId;
            private Integer num;
            private String title;
            private String position;
            private String career;
            private Timestamp endDate;
            private List<String> skillList;
            private Long leftTime;
        }

        @Getter
        @Setter
        public static class ResumeMatchOutDto {
            private Integer resumeId;
            private String title;
            private String address;
            private String education;
            private String career;
            private Integer state;
            private List<String> skillList;
            private UserDto userDto;

            @Getter
            @Setter
            public static class UserDto {
                private Integer userId;
                private String name;
                private String photo;
            }
        }
    }

    @Getter
    @Setter
    public static class CompProfileOutDto {
        private Integer compId;
        private String photo;
    }

    @Getter
    @Setter
    public static class CompLoginRespDto {
        private Integer compId;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class CompWriteJobsRespDto {
        private String compName;
        private String representativeName;
        private String photo;
        private String homepage;
    }
}
