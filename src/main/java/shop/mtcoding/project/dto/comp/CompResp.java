package shop.mtcoding.project.dto.comp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CompResp {

    @Getter
    @Setter
    public static class CompUpdatePhotoReqDto {
        private Integer compId;
        private String photo;

        @Builder
        public CompUpdatePhotoReqDto(Integer compId, String photo) {
            this.compId = compId;
            this.photo = photo;
        }
    }

    @Getter
    @Setter
    public static class CompUpdateRespDto {
        private Integer compId;
        private String password;
        private String compName;
        private String representativeName;
        private String businessNumber;
    }

    @Getter
    @Setter
    public static class CompLoginRespDto {
        private Integer compId;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class CompWriteJobsRespDto {
        private String compName;
        private String representativeName;
        private String photo;
        private String homepage;
    }
}
