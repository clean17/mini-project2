package shop.mtcoding.project.dto.interest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class InterestReq {
    
    @Getter
    @Setter
    @ToString
    public static class InterestChangeReqDto{
        private Integer interestId;
        private Integer userId;
        private List<String> interestList;
    }

}
