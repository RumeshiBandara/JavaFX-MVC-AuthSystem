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
import java.net.URL;

public class LoginFormController {

    private final LoginFormService loginFormService = new LoginFormServiceImpl();

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    // 🔵 LOGIN BUTTON
    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();

        if (!email.endsWith("@gmail.com")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email must end with @gmail.com");
            return;
        }

        LoginDTO dto = new LoginDTO(email, password);

        if (loginFormService.login(dto)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Login Successful!");

            try {
                URL url = getClass().getResource("/view/dashBoard_Form.fxml");
                if (url == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Dashboard FXML not found");
                    return;
                }

                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(url)));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Dashboard load failed");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email or password");
        }
    }

    // 🔁 GO TO SIGN UP
    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        try {
            URL url = getClass().getResource("/view/singUp_Form.fxml");
            if (url == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "SignUp FXML not found");
                return;
            }

            Stage stage = (Stage) btnSignUp.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(url)));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "SignUp page load failed");
        }
    }

    // 🎨 THEMED ALERT METHOD
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        URL cssURL = getClass().getResource("/css/alert.css");
        if (cssURL != null) {
            alert.getDialogPane().getStylesheets().add(cssURL.toExternalForm());
        }

        alert.showAndWait();
    }
}
