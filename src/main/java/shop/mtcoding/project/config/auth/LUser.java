package shop.mtcoding.project.config.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LUser {
    private Integer id;
    private String email;
    private String role;

    @Builder
    public LUser(Integer id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
