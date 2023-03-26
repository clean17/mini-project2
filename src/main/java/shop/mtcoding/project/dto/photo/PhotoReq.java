package shop.mtcoding.project.dto.photo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class PhotoReq {
    
    @Getter @NoArgsConstructor
    public static class PhotoUpdateDto{
        private String fileName;
        private String photo;

        @Builder
        public PhotoUpdateDto(String fileName, String photo) {
            this.fileName = fileName;
            this.photo = photo;
        }
    }
}
