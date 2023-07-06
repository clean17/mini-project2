package shop.mtcoding.project.dto.apply;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class ApplyResp {

    @Getter
    @Setter
    public static class ApplyOutDto {
        private Integer applyId;
        private ResumeDto resumeDto;
        private JobsDto jobsDto;
        private Integer state;
        private Timestamp createdAt;

        @Getter
        @Setter
        public static class ResumeDto {
            private Integer resumeId;
            private String title;
            private UserDto userDto;

            @Getter
            @Setter
            public static class UserDto {
                private Integer userId;
                private String name;
            }
        }

        @Getter
        @Setter
        public static class JobsDto {
            private Integer jobsId;
            private String title;
            private String position;
            private CompDto compDto;

            @Getter
            @Setter
            public static class CompDto {
                private Integer compId;
                private String compName;
            }

        }
    }

    @Getter
    @Setter
    public static class ApplyUserStatusDetailRespDto {
        private Integer state;
    }

    @Getter
    @Setter
    public static class ApllyStatusCompRespDto {
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
    }

    @Getter
    @Setter
    public static class ApplyStatusUserRespDto {
        private Integer applyId; 
        private Integer resumeId; 
        private Integer jobsId;
        private Integer state;
        private String jobsTitle; 
        private String position; 
        private String resumeTitle;
    }

    @Getter
    @Setter
    public static class ApplytoCompRespDto {
        private Integer applyId;
        private Integer state;
    }

}
