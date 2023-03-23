package shop.mtcoding.project.dto.apply;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ApplyResp {

    @Getter @Setter @ToString
    public static class ApplyOutDto{
        private Integer applyId;
        private ResumeDto resumeDto;
        private JobsDto jobsDto;
        private Integer state;
        private Timestamp createdAt;

        @Getter @Setter
        public static class ResumeDto {
            private Integer resumeId;
            private String title;
            private UserDto userDto;

            @Getter @Setter
            public static class UserDto{
                private Integer userId;
                private String name;
            }
        }

        @Getter @Setter
        public static class JobsDto {
            private Integer jobsId;
            private String title;
            private String position;
            private CompDto compDto;

            @Getter @Setter
            public static class CompDto{
                private Integer compId;
                private String compName;
            }

        }
    }
    
    @Getter
    @Setter
    public static class ApplyUserStatusDetailRespDto{
        private Integer state;
    }
    @Getter
    @Setter
    public static class ApllyStatusCompRespDto{
        private Integer applyId;
        private Integer resumeId;
        private Integer jobsId;
        private String title;
        private String position;
        private String career;
        private String education;
        private String name;
        private String birth;
        private Integer state;
        // private List<String> skillList;
    }

    @Getter
    @Setter
    public static class ApplyStatusUserRespDto{
        private Integer applyId; // apply_tb(1)
        private Integer resumeId; // apply_tb(1)
        private Integer jobsId; // apply_tb(1)
        private Integer state; // apply_tb(1)
        private String jobsTitle; // jobs_tb(2) -> title
        private String position; // jobs_tb(2)
        private String resumeTitle; // resume_tb -> title
        // private List<String> skillList;
    }

    @Getter
    @Setter
    public static class ApplytoCompRespDto{
        private Integer applyId;
        private Integer state;
    }

}
