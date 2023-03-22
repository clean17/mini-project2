package shop.mtcoding.project.dto.apply;



import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ApplyReq {
    
    @Getter
    @Setter
    public static class ApplyReqDto{
        @NotNull(message = "이력서 아이디가 없습니다/")
        private Integer resumeId;
        @NotNull(message = "공고 아이디가 없습니다/")
        private Integer jobsId;
        @NotNull(message = "유저 아이디가 없습니다/")
        private Integer userId;
        @NotNull(message = "이력서 아이디가 없습니다/")
        private Integer applyId;
    }

    

    @Getter
    @Setter
    public static class ApplyUpdateReqDto{
        @NotNull(message = "지원아이디가 필요합니다/")
        private Integer applyId;
        @NotNull(message = "기업아이디가 필요합니다/")
        private Integer compId;
        @NotNull(message = "상태정보가 필요합니다/")
        private Integer state;
    }
    
}
