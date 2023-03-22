package shop.mtcoding.project.dto.jobs;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class JobsReq {
    
    @Getter
    @Setter
    @ToString
    public static class JobsCheckBoxReqDto{
        private List<String> address;
        private List<String> skill;
        private List<String> position;
        private String career;
        private String keyword;
    }

    @Getter
    @Setter
    @ToString
    public static class JobsSearchReqDto{ // 이거 언제씀 ?
        private String address;
        private String skill;
        private String position;
        private String career;
    }

    @Getter
    @Setter
    @ToString
    public static class JobsWriteReqDto{
        private Integer jobsId; // 임시저장에 필요
        @NotNull(message = "회사계정이 필요합니다/")
        private Integer compId;
        @NotEmpty(message = "회사명이 필요합니다/")
        private String compName;
        @NotEmpty(message = "대표자명이 필요합니다/")
        private String representativeName;
        private String homepage;
        private String photo;
        @NotEmpty(message = "공고 제목이 필요합니다/")
        private String title;
        private String content;
        @NotEmpty(message = "학력정보가 필요합니다/")
        private String education;
        @NotEmpty(message = "경력정보가 필요합니다/")
        private String career;
        @NotEmpty(message = "직무정보가 필요합니다/")
        private String position;
        @NotEmpty(message = "근무주소가 필요합니다/")
        private String address;
        @NotNull(message = "마감일을 선택해주세요/")
        private Timestamp endDate;
        @NotEmpty(message = "접수방법이 필요합니다/")
        private String receipt;
        @NotEmpty(message = "기술을 선택해주세요/")
        @Size(min = 1)
        private List<String> skillList;
    }
    
    @Getter
    @Setter
    @ToString
    public static class JobsUpdateReqDto{
        @NotNull(message = "공고번호가 필요합니다/")
        private Integer jobsId; // 임시저장에 필요
        @NotNull(message = "회사계정이 필요합니다/")
        private Integer compId;
        @NotEmpty(message = "회사명이 필요합니다/")
        private String compName;
        @NotEmpty(message = "대표자명이 필요합니다/")
        private String representativeName;
        private String homepage;
        private String photo;
        @NotEmpty(message = "공고 제목이 필요합니다/")
        private String title;
        private String content;
        @NotEmpty(message = "학력정보가 필요합니다/")
        private String education;
        @NotEmpty(message = "경력정보가 필요합니다/")
        private String career;
        @NotEmpty(message = "직무정보가 필요합니다/")
        private String position;
        @NotEmpty(message = "근무주소가 필요합니다/")
        private String address;
        @NotNull(message = "마감일을 선택해주세요/")
        private Timestamp endDate;
        @NotEmpty(message = "접수방법이 필요합니다/")
        private String receipt;
        @NotEmpty(message = "기술을 선택해주세요/")
        @Size(min = 1)
        private List<String> skillList;
    }
}
