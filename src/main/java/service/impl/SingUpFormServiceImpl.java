package service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import model.dto.SingUpDTO;
import repository.SingUpFormRepository;
import repository.impl.SingUpFormRepositoryImpl;
import service.SingUpService;

public class SingUpFormServiceImpl implements SingUpService {

    private final SingUpFormRepository singUpFormRepository =
            new SingUpFormRepositoryImpl();

    @Override
    public boolean register(SingUpDTO user) {
        try {
            // 🔐 Hash password before saving
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

            return singUpFormRepository.saveUser(user);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isEmailExist(String email) {
        return singUpFormRepository.isEmailExist(email);
    }

    private String hashPassword(String password) {
        return BCrypt.withDefaults()
                .hashToString(12, password.toCharArray());
    }
}
