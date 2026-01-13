package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.LoginDTO;
import service.LoginFormService;
import service.impl.LoginFormServiceImpl;

import java.io.IOException;

public class LoginFormController {

    private final LoginFormService loginFormService = new LoginFormServiceImpl();

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSingUp;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    // 🔐 LOGIN BUTTON ACTION
    @FXML
    void btnLogin(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();

        // Email validation
        if (!email.endsWith("@gmail.com")) {
            showAlert("Error", "Email must end with @gmail.com");
            return;
        }

        LoginDTO dto = new LoginDTO(email, password);

        // Service layer handles BCrypt check
        if (loginFormService.login(dto)) {
            showAlert("Success", "Login Successful!");

            try {
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(
                        FXMLLoader.load(getClass().getResource("/view/dashBoard_Form.fxml"))
                ));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Dashboard load failed");
            }

        } else {
            showAlert("Error", "Invalid email or password");
        }
    }

    // 🔁 GO TO SIGN UP
    @FXML
    void btnSingUP(ActionEvent event) {
        try {
            Stage stage = (Stage) btnSingUp.getScene().getWindow();
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/singUp_Form.fxml"))
            ));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Sign Up page load failed");
        }
    }

    // 🔔 COMMON ALERT METHOD
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
