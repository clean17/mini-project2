package shop.mtcoding.project.dto.user;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResp {

    @Getter
    @Setter
    public static class UserUpdatePhotoOutDto {
        private Integer userId;
        private String photo;

        @Builder
        public UserUpdatePhotoOutDto(Integer userId, String photo) {
            this.userId = userId;
            this.photo = photo;
        }

    }

    @Getter
    @Setter
    public static class UserLoginRespDto {
        private Integer userId;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class UserDataRespDto {
        private Integer userId;
        private String email;
        private String name;
        private String birth;
        private String tel;
        private String photo;
        private String address;

    }

    @Getter
    @Setter
    public static class UserUpdateRespDto {
        private Integer userId;
        private String email;
        private String password;
        private String name;
        private String birth;
        private String tel;
        private String address;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class UserDeleteRespDto {
        private Integer userId;
    }

    @Getter
    @Setter
    public static class UserSkillAndInterestDto {
        private List<String> skillList;
        private List<String> interestList;
    }

}
