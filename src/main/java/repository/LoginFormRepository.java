package repository;

public interface LoginFormRepository {
    String getPasswordHashByEmail(String email);
}
