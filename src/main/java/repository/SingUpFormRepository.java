package repository;

import model.dto.SingUpDTO;

public interface SingUpFormRepository {
    boolean saveUser(SingUpDTO user);
    boolean isEmailExist(String email);
}
