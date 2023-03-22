package shop.mtcoding.project.dto.user;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserReq {

    @Getter
    @Setter
    public static class UserJoinReqDto {
        private Integer userId;
        @NotBlank(message = "이메일은 필수 입력 값입니다/")
        private String email;
        // @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
        // message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요/")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다/")
        private String password;
        @NotBlank(message = "이름은 필수 입력 값입니다/")
        private String name;
        @NotBlank(message = "생년월일은 필수 입력 값입니다/")
        private String birth;
        @NotBlank(message = "전화번호는 필수 입력 값입니다/")
        private String tel;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    @ToString
    public static class UserLoginReqDto {
        private Integer userId;
        @NotBlank(message = "이메일은 필수 입력 값입니다/")
        private String email;
        // @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
        // message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요/")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다/")
        private String password;
        private String rememberEmail;
    }

    @Getter
    @Setter
    @ToString
    public static class UserUpdateReqDto {
        private Integer userId;
        @NotBlank(message = "비밀번호를 입력해 주세요/")
        private String password;
        @NotBlank(message = "이름을 입력해 주세요/")
        private String name;
        @NotBlank(message = "생년월일을 입력해 주세요/")
        private String birth;
        @NotBlank(message = "전화번호를 입력해 주세요/")
        private String tel;
        @NotBlank(message = "주소를 입력해 주세요/")
        private String address;
    }

    @Getter
    @Setter
    public static class UserUpdatePhotoReqDto {
        private Integer userId;
        private String email;
        private String password;
        private String name;
        private String birth;
        private String tel;
        private String photo;
        private String address;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class UserPasswordReqDto {
        private Integer userId;
        private String password;
    }
}
