package shop.mtcoding.project.dto.scrap;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class CompScrapResp {

    @Getter @Setter
    public static class CompScrapOutDto{
        private Integer compScrapId;
        private CompDto compDto;
        private ResumeDto resumeDto;
        private Timestamp createdAt;

        @Getter @Setter
        public static class CompDto{
            private Integer compId;
            private String compName;
        }

        @Getter @Setter
        public static class ResumeDto {
            private Integer resumeId;
            private String title;
            private UserDto userDto;

            @Getter @Setter
            public static class UserDto {
                private Integer userId;
                private String name;
            }
        }
    }

    @Getter
    @Setter
    public static class CompScrapResumeRespDto{
        private Integer compscrapId;
        private Integer resumeId;
        private String name;
        private String birth;
        private String title;
        private String career;
        private String education;
        private List<String> skillList;
        private String address;
    }
}
