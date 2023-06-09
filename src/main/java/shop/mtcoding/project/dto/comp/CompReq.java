package shop.mtcoding.project.dto.comp;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CompReq {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
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
        @NotBlank(message = "이메일은 필수 입력 값입니다/")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다/")
        private String password;
        private String rememberEmail;
    }

    @Getter
    @Setter
    public static class CompUpdateReqDto {
        private Integer compId;
        @NotBlank(message = "비밀번호를 입력해 주세요/")
        private String password;
        @NotBlank(message = "회사명을 입력해 주세요/")
        private String compName;
        @NotBlank(message = "대표자명을 입력해 주세요/")
        private String representativeName;
        @NotBlank(message = "사업자번호를 입력해 주세요/")
        private String businessNumber;
    }

    @Getter
    @Setter
    public static class CompUpdatePhotoReqDto {
        private Integer compId;
        private String photo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CompPasswordReqDto {
        private Integer compId;
        private String password;
        
        @Builder
        public CompPasswordReqDto(Integer compId, String password) {
            this.compId = compId;
            this.password = password;
        }
        
    }

}
