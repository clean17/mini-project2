package shop.mtcoding.project.dto.interest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class InterestResp {
    
    @Getter
    @Setter
    @ToString
    public static class InterestChangeRespDto{
        // private Integer userId;
        private String interestCt;
    }

    @Getter
    @Setter
    public static class InterestChangeOutDto{
        private UserDto userDto;
        private List<String> interestList;

        @Getter @Setter
        public static class UserDto {
            private Integer userId;
            private String name;
        }
    }
}
