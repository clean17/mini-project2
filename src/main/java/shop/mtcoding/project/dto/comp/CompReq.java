package shop.mtcoding.project.dto.comp;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CompReq {

    @Getter
    @Setter
    public static class CompJoinReqDto {
        private Integer compId;
        @NotBlank(message = "이메일은 필수 입력 값입니다/")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다/")
        private String password;
        @NotBlank(message = "회사이름은 필수 입력 값입니다/")
        private String compName;
        @NotBlank(message = "대표자명은 필수 입력 값입니다/")
        private String representativeName;
        @NotBlank(message = "회사번호는 필수 입력 값입니다/")
        private String businessNumber;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class CompLoginReqDto {
        private Integer compId;
        private String email;
        private String password;
        private String rememberEmail;
    }

    @Getter
    @Setter
    public static class CompUpdateReqDto {
        private Integer compId;
        private String password;
        private String compName;
        private String representativeName;
        private String businessNumber;
    }

    @Getter
    @Setter
    public static class CompPasswordReqDto {
        private Integer compId;
        private String password;
    }

}
