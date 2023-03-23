package shop.mtcoding.project.dto.resume;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ResumeResp {

    @Getter
    @Setter
    public static class ResumeMatchPageOutDto {
        private Integer compId;
        private List<String> skillList; // 기업 스킬
        
        private List<ResumeMatchDto> resumeDto;

        @Getter
        @Setter
        public static class ResumeMatchDto {
            private Integer resumeId;
            private String title;
            private String address;
            private String education;
            private String career;
            private Integer state;
            private List<String> skillList; // 이력서 스킬
            private UserDto userDto;
            private CompScrapDto compScrapDto;

            @Getter
            @Setter
            public static class UserDto {
                private Integer userId;
                private String photo;
                private String name;
            }

            @Getter
            @Setter
            public static class CompScrapDto {
                private Integer compScrapId;
            }
        }
    }

    @Getter
    @Setter
    public static class ResumePublicOutDto {
        private Integer resumeId;
        private String title;
        private String career;
        private Integer state;
        private List<String> skillList;
        private UserDto usetDto;
        private CompScrapDto compScrapDto;

        @Getter
        @Setter
        public static class UserDto {
            private Integer userId;
            private String photo;
            private String name;
            private String address;
        }

        @Getter
        @Setter
        public static class CompScrapDto {
            private Integer compScrapId;
        }
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
        private CompScrapDto compScrapDto;

        @Getter
        @Setter
        public static class UserDto {
            private Integer userId;
            private String name;
            private String photo;
        }

        @Getter
        @Setter
        public static class CompScrapDto {
            private Integer compScrapId;
        }
    }

    @Getter
    @Setter
    public static class ResumeIdRespDto {
        private Integer resumeId;
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeDetailRespDto {
        private Integer resumeId;
        private String title;
        private String content;
        private String education;
        private String career;
        private String link;
        private List<String> skillList;

        private UserDto user;

        private CompScrapDto compScrap;

        private ApplyDto apply;
        
        private SuggestDto suggest;

        // 유저
        @Getter
        @Setter
        public static class UserDto {
            private String userId;
            private String photo;
            private String name;
            private String birth;
            private String address;
        }

        // 회사스크랩
        @Getter
        @Setter
        public static class CompScrapDto {
            private String compScrapId;
        }

        // 지원
        @Getter
        @Setter
        public static class ApplyDto {
            private Integer applyId;
            private Integer applyState;
        }

        // 제안
        @Getter
        @Setter
        public static class SuggestDto {
            private Integer suggestId;
            private Integer suggestState;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeManageRespDto {
        private Integer resumeId;
        private UserDto user;
        private String title;
        private String education;
        private String career;
        private String address;
        private List<String> skillList;

        @Getter
        @Setter
        public static class UserDto {
            private Integer userId;
            private String name;
        }

    }

    @Getter
    @Setter
    @ToString
    public static class ResumeSaveRespDto {
        private Integer resumeId;
        private UserDto user;
        private String title;
        private String content;
        private String education;
        private String career;
        private String link;
        private Integer state;
        private List<String> skillList;

        @Getter
        @Setter
        public static class UserDto {
            private Integer userId;
            private String email;
            private String password;
            private String name;
            private String birth;
            private String tel;
            private String photo;
            private String Address;
        }
    }

    @Getter
    @Setter
    public static class ResumeUpdateRespDto {
        private Integer resumeId;
        private Integer userId;
        private String photo;
        private String name;
        private String tel;
        private String email;
        private String address;
        private String birth;
        private String title;
        private String content;
        private String education;
        private String career;
        private String link;
        private Integer state;
        private List<String> skillList;
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeReadRespDto {
        private Integer resumeId;
        private Integer userId;
        private String photo;
        private String name;
        private String title;
        private String address;
        private String career;
        private Integer state;
        private List<String> skillList;
        private Integer compScrapId;
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeMatchRespDto {
        private Integer resumeId;
        private String photo;
        private String name;
        private String title;
        private String address;
        private String education;
        private String career;
        private Integer state;
        private List<String> skillList;
        private Integer compScrapId;
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeSearchRespDto {
        private Integer resumeId;
        private String title;
        private String career;
        private Integer state;
        private List<String> skillList;
        private UserDto user;

        @Getter
        @Setter
        public static class UserDto {
            private Integer userId;
            private String name;
            private String photo;
            private String address;
        }
    }

}
