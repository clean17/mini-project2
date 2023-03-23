package shop.mtcoding.project.dto.scrap;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.project.dto.scrap.UserScrapResp.UserScrapRespDto;

public class UserScrapResp {


    @Getter @Setter
    public static class UserScrapOutDto{
        private Integer userScrapId;
        private UserDto userDto;
        private JobsDto jobsDto;
        private Timestamp createdAt;

        @Getter @Setter
        public static class UserDto{
            private Integer userId;
            private String name;
        }

        @Getter @Setter
        public static class JobsDto{
            private Integer jobsId;
            private String title;
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
    public static class UserScrapRespDto {
        private Integer userScrapId; // user_scrap_tb(1)
        private Integer jobsId; // user_scrap_tb(1)
        private CompDto Comp; // comp_tb(2)
        private JobsDto Jobs; // jobs_tb(3)
        private List<String> skillList; // required_skill_tb(4)
        private Long leftTime;

        // 회사
        @Getter
        @Setter
        public static class CompDto {
            private String compName;
        }

        // 직업
        @Getter
        @Setter
        public static class JobsDto {
            private String title;
            private String position;
            private String career;
            private Timestamp endDate;
        }
    }


  

    @Getter
    @Setter
    public static class UserScrapIdRespDto{
        private Integer userScrapId;
    }
}
