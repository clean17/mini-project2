package shop.mtcoding.project.dto.jobs;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class JobsResp {

    @Getter @Setter
    public static class JobsMainOutDto{
        private Integer jobsId;
        private String title;
        private String career;
        private String education;
        private String position;
        private String address;
        private Timestamp endDate;
        private Long leftTime;
        private List<String> skillList;
        private CompDto compDto;
        private UserScrapDto userScrapDto;

        @Getter @Setter
        public static class CompDto{
            private String compName;
            private String photo;
        }

        @Getter @Setter
        public static class UserScrapDto{
            private Integer userScrapId;
        }
    }

    @Getter
    public static class JobsSearchkeyOutDto {
        private String keyword;
        private List<JobsSearchOutDto> jobsSearchOutDto;

        @Builder
        public JobsSearchkeyOutDto(String keyword, List<JobsSearchOutDto> jobsSearchOutDto) {
            this.keyword = keyword;
            this.jobsSearchOutDto = jobsSearchOutDto;
        }
        
    }

    @Getter
    @Setter
    public static class JobsSearchOutDto {
        private Integer jobsId;
        private String title;
        private String career;
        private String education;
        private String position;
        private String address;
        private Timestamp endDate;
        private Long leftTime;
        private UserScrapOutDto userScrapOutDto;
        private List<String> skillList;
        private CompOutDto compOutDto;

        @Getter
        @Setter
        public static class UserScrapOutDto {
            private Integer userScrapId;
        }

        @Getter
        @Setter
        public static class CompOutDto {
            private String compName;
            private String photo;
        }
    }

    @Getter
    @Setter
    public static class JobsIdRespDto {
        private Integer jobsId;
    }

    @Getter
    @Setter
    @ToString
    public static class JobsMainRespDto {
        private Integer jobsId;
        private String compName;
        private String photo;
        private String title;
        private String career;
        private String education;
        private String position;
        private List<String> skillList;
        private String address;
        private Integer userScrapId;
        private Long leftTime;
        private Timestamp endDate;
    }

    @Getter
    @Setter
    @ToString
    public static class JobsMainRecommendRespDto {
        private Integer jobsId;
        private String compName;
        private String photo;
        private String title;
        private String career;
        private String education;
        private String position;
        private List<String> skillList;
        private String address;
        private Integer userScrapId;
        private Long leftTime;
        private Timestamp endDate;
    }



    @Getter
    @Setter
    @ToString
    public static class JobsMatchRespDto {
        private Integer jobsId;
        private String compName;
        private String photo;
        private String title;
        private String career;
        private String education;
        private String position;
        private List<String> skillList;
        private String address;
        private Integer userScrapId;
        private Long leftTime;
        private Timestamp endDate;
    }

    // @Getter
    // @Setter
    // @ToString
    // public static class JobsSearchRespDto {
    //     private Integer jobsId;
    //     private String title;
    //     private String career;
    //     private String education;
    //     private String position;
    //     private String address;
    //     private List<String> skillList;
    //     private String compName;
    //     private String photo;
    //     private Integer userScrapId;
    //     private Timestamp endDate;
    //     private Long leftTime;
    // }

    @Getter
    @Setter
    public static class JobsCheckOutDto{
        private Integer jobsId;
        private String title;
        private String career;
        private String education;
        private String position;
        private String address;
        private Timestamp endDate;
        private Long leftTime;
        private CompDto compDto;
        private UserScrapDto userScrapDto;
        private List<String> skillList;
        
        @Getter
        @Setter
        public static class CompDto{
            private String compName;
            private String photo;
        }
        
        @Getter
        @Setter
        public static class UserScrapDto{
            private Integer userScrapId;
        }
    }

    // @Getter
    // @Setter
    // @ToString
    // public static class JobsDetailRespDto {
    //     private Integer jobsId;
    //     private String title;
    //     private String content;
    //     private String career;
    //     private String education;
    //     private String receipt;
    //     private String position;
    //     private String address;
    //     private String homepage;
    //     private Timestamp endDate;
    //     private List<String> skillList;

    //     private Integer compId;
    //     private String representativeName;
    //     private String compName;
    //     private String photo;

    //     private Integer state;

    //     private Long leftTime;
    //     private String formatEndDate;

    //     private Integer userScrapId;
    // }

    @Getter @Setter
    public static class JobsDetailOutDto{
        private Integer jobsId;
        private String title;
        private String content;
        private String career;
        private String education;
        private String receipt;
        private String position;
        private String address;
        private String homepage;
        private Timestamp endDate;
        private Long leftTime;
        private String formatEndDate;
        private List<String> skillList;
        private CompDto compDto;
        private ApplyDto applyDto;
        private UserScrapDto userScrapDto;

        @Getter @Setter
        public static class CompDto{
            private Integer compId;
            private String representativeName;
            private String compName;
            private String photo;           
        }

        @Getter @Setter
        public static class ApplyDto {
            private Integer state;            
        }

        @Getter @Setter
        public static class UserScrapDto{
            private Integer userScrapId;
        }
    }

    @Getter
    @Setter
    public static class JobsRequiredSkill {
        private String skill;
    }

    @Getter
    @Setter
    public static class JobsUpdateRespDto {
        private Integer jobsId;
        private String photo;
        private String compName;
        private String representativeName;
        private String establishmentDate;
        private Integer employees;
        private String homepage;
        private String title;
        private String content;
        private String education;
        private String career;
        private String position;
        private List<String> skillList;
        private String address;
        private Timestamp endDate;
        private String receipt;
        private Integer userScrapId;
    }

    @Getter
    @Setter
    public static class JobsManageJobsRespDto {
        private Integer num;
        private Integer jobsId;
        private String title;
        private String position;
        private String career;
        private Timestamp endDate;
        private List<String> skillList;
        private Long leftTime;
    }

    @Getter
    @Setter
    @ToString
    public static class JobsSuggestRespDto {
        private Integer jobsId;
        private String title;
        private String position;
        // private List<String> skillList;
        private Timestamp endDate;
        private Long leftTime;
    }

}
