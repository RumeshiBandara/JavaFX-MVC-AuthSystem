package service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
            BCrypt.Result result = BCrypt.verifyer().verify(loginDTO.getPassword().toCharArray(), storedHash);
            return result.verified;
        }
        return false;

    }
}
