package shop.mtcoding.project.dto.suggest;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SuggestReq {
    
    @Getter
    @Setter
    @ToString
    public static class SuggestReqDto{
        private Integer suggestId;
        @NotEmpty(message = "이력서 아이디가 없습니다/")
        private Integer resumeId;
        @NotEmpty(message = "공고 아이디가 없습니다/")
        private Integer jobsId;
        @NotEmpty(message = "회사 아이디가 없습니다/")
        private Integer compId;
        private Integer compScrapId;
    }

    @Getter
    @Setter
    public static class SuggestUpdateReqDto{
        @NotEmpty(message = "제안아이디가 필요합니다/")
        private Integer suggestId;
        @NotEmpty(message = "유저아이디가 필요합니다/")
        private Integer userId;
        @NotEmpty(message = "상태정보가 필요합니다/")
        private Integer state;
    }
}
