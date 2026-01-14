package service;

import model.dto.LoginDTO;

public interface LoginFormService {
    boolean login(LoginDTO loginDTO);
}
