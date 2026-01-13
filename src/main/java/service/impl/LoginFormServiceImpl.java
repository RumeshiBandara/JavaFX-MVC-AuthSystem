package service.impl;

import model.dto.LoginDTO;
import repository.LoginFormRepository;
import repository.impl.LoginFormRepositoryImpl;
import service.LoginFormService;

public class LoginFormServiceImpl implements LoginFormService {
    LoginFormRepository loginFormRepository = new LoginFormRepositoryImpl();

    @Override
    public boolean login(LoginDTO loginDTO) {
        String storedHash = loginFormRepository.getPasswordHashByEmail(loginDTO.getEmail());
        if (storedHash != null) {
            /*return BCrypt.checkpw(loginDTO.getPassword(), storedHash);*/
        }
        return false;

    }
}
