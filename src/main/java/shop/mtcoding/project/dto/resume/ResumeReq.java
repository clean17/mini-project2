package shop.mtcoding.project.dto.resume;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ResumeReq {
    @Getter
    @Setter
    @ToString
    public static class ResumeWriteReqDto {
        private Integer resumeId; // 리턴용
        private UserDto user;
        private String title;
        private String content;
        private String education;
        private String career;
        private String link;
        private Integer state;
        private Timestamp createdAt;
        private List<String> skillList;

        @Getter
        @Setter
        public static class UserDto {
            private Integer userId;
        }

    }

    @Getter
    @Setter
    @ToString
    public static class ResumeUpdateReqDto {
        private Integer resumeId; // 리턴용
        private Integer userId;
        private String title;
        private String content;
        private String education;
        private String career;
        private String link;
        private Integer state;
        private Timestamp createdAt;
        private List<String> skillList;
    }

    @Getter
    @Setter
    @ToString
    public static class ResumeCheckboxReqDto {
        private List<String> address;
        private List<String> skillList;
        private String career;
    }
}
