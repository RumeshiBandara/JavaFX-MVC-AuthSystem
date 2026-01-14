package service;

import model.dto.SingUpDTO;

public interface SingUpService {
    boolean register(SingUpDTO user);
    boolean isEmailExist(String email);
}
