package model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SingUpDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String rePassword;

}
