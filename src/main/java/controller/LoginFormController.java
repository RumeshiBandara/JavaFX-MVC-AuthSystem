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

     LoginFormService loginFormService = new LoginFormServiceImpl();

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;


    @FXML
    void btnLogin(ActionEvent event) {

        String email = txtEmail.getText().trim();
        String password = txtPassword.getText();


        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Please enter email and password");
            return;
        }


        if (!email.endsWith("@gmail.com")) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Email must end with @gmail.com");
            return;
        }

        LoginDTO dto = new LoginDTO(email, password);


        if (loginFormService.login(dto)) {

            showAlert(Alert.AlertType.INFORMATION,
                    "Success", "Login Successful!");

            try {
                URL url = getClass().getResource("/view/dashBoard_Form.fxml");
                if (url == null) {
                    showAlert(Alert.AlertType.ERROR,
                            "Error", "Dashboard FXML not found");
                    return;
                }

                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(url)));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,
                        "Error", "Dashboard load failed");
            }

        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Error", "Invalid email or password");
        }
    }


    @FXML
    void btnSingUpOnAction(ActionEvent event) {
        try {
            URL url = getClass().getResource("/view/singUp_Form.fxml");
            if (url == null) {
                showAlert(Alert.AlertType.ERROR,
                        "Error", "Sign Up FXML not found");
                return;
            }

            Stage stage = (Stage) btnSignUp.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(url)));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,
                    "Error", "Sign Up page load failed");
        }
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);


        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/alert.css").toExternalForm()
        );

        alert.showAndWait();
    }
}
