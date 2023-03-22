package shop.mtcoding.project.dto.resume;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ResumeResp {

    @Getter
    @Setter
    public static class ResumeIdRespDto{
        private Integer resumeId;
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeDetailRespDto {
        private Integer resumeId;
        private Integer applyId;
        private String photo;
        private String name;
        private String title;
        private String birth;
        private String education;
        private String career;
        private List<String> skillList;
        private String compScrapId;
        private String address;
        private String content;
        private String link;
        private Integer suggestState;
        private Integer applyState;
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

        @Getter @Setter
        public static class UserDto{
            private Integer userId;
            private String name;
        }
    
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeSaveRespDto {
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
        private Timestamp createdAt;
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
        private Timestamp createdAt;
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
        private Integer userId;
        private String photo;
        private String name;
        private String title;
        private String address;
        private String career;
        private Integer state;
        private List<String> skillList;
    }


}
