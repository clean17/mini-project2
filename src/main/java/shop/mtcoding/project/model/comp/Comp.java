package shop.mtcoding.project.model.comp;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Comp {
    private Integer compId;
    private String email;
    private String password;
    private String compName;
    private String representativeName;
    private String businessNumber;
    private String photo;
    private String homepage;
    private Timestamp createdAt;

    @Builder
    public Comp(Integer compId, String email, String password, String compName, String representativeName,
            String businessNumber, String photo, String homepage, Timestamp createdAt) {
        this.compId = compId;
        this.email = email;
        this.password = password;
        this.compName = compName;
        this.representativeName = representativeName;
        this.businessNumber = businessNumber;
        this.photo = photo;
        this.homepage = homepage;
        this.createdAt = createdAt;
    }
}
