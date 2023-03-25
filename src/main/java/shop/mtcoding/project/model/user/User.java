package shop.mtcoding.project.model.user;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Integer userId;
    private String email;
    private String password;
    private String name;
    private String birth;
    private String tel;
    private String photo;
    private String Address;
    private Timestamp createdAt;

    @Builder
    public User(Integer userId, String email, String password, String name, String birth, String tel, String photo,
            String address, Timestamp createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.tel = tel;
        this.photo = photo;
        Address = address;
        this.createdAt = createdAt;
    }

    
}
