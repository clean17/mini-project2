package shop.mtcoding.project.dto.scrap;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
        private Integer userScrapId;
        private Integer jobsId;
        private String compName;
        private String title;
        private String position;
        private String career;
        private List<String> skillList;
        private Long leftTime;
        private Timestamp endDate;
    }

    @Getter
    @Setter
    public static class UserScrapIdRespDto{
        private Integer userScrapId;
    }
}
