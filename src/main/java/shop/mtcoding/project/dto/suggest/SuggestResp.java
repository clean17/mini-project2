package shop.mtcoding.project.dto.suggest;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class SuggestResp {
    
    @Getter @Setter
    public static class SuggestResumeOutDto{
        private Integer suggestId;
        private JobsDto jobsDto;
        private ResumeDto resumeDto;
        private Integer state;
        private Timestamp createdAt;

        @Getter @Setter
        public static class JobsDto {
            private Integer jobsId;
            private String title;
            private String position;
        }

        @Getter @Setter
        public static class ResumeDto{
            private Integer resumeId;
            private UserDto userDto;
            private String title;

            @Getter @Setter
            public static class UserDto{
                private Integer userId;
                private String name;
            }
        }
    }
    @Getter
    @Setter
    public static class SuggestToUserRespDto {
        private Integer suggestId;
        private Integer jobsId;
        private Integer resumeId;
        private String compName;
        private String title;
        private String position;
        private String name;
        private Integer state;
    }

    @Getter
    @Setter
    public static class SuggestToCompRespDto {
        private Integer suggestId;
        private Integer compId;
        private Integer jobsId;
        private Integer resumeId;
        private String title;
        private String position;
        private String name;
        private Integer state;
    }

    @Getter
    @Setter
    public static class SuggestToCompRespIdDto {
        private Integer suggestId;
        private Integer state;
    }
}
