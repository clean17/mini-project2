package shop.mtcoding.project.dto.apply;



import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ApplyReq {
    
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ApplyReqDto{
        @NotNull(message = "이력서 아이디가 없습니다/")
        private Integer resumeId;
        @NotNull(message = "공고 아이디가 없습니다/")
        private Integer jobsId;
        @NotNull(message = "유저 아이디가 없습니다/")
        private Integer userId;
        @NotNull(message = "이력서 아이디가 없습니다/")
        private Integer applyId;

        @Builder
        public ApplyReqDto(@NotNull(message = "이력서 아이디가 없습니다/") Integer resumeId,
                @NotNull(message = "공고 아이디가 없습니다/") Integer jobsId, @NotNull(message = "유저 아이디가 없습니다/") Integer userId,
                @NotNull(message = "이력서 아이디가 없습니다/") Integer applyId) {
            this.resumeId = resumeId;
            this.jobsId = jobsId;
            this.userId = userId;
            this.applyId = applyId;
        }
    }

    

    @NoArgsConstructor
    @Getter
    @Setter
    public static class ApplyUpdateReqDto{
        @NotNull(message = "지원아이디가 필요합니다/")
        private Integer applyId;
        @NotNull(message = "기업아이디가 필요합니다/")
        private Integer compId;
        @NotNull(message = "상태정보가 필요합니다/")
        private Integer state;

        @Builder
        public ApplyUpdateReqDto(@NotNull(message = "지원아이디가 필요합니다/") Integer applyId,
                @NotNull(message = "기업아이디가 필요합니다/") Integer compId, @NotNull(message = "상태정보가 필요합니다/") Integer state) {
            this.applyId = applyId;
            this.compId = compId;
            this.state = state;
        }
    }
    
}
