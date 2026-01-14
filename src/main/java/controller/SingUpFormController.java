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
import model.dto.SingUpDTO;
import service.SingUpService;
import service.impl.SingUpFormServiceImpl;

import java.io.IOException;
import java.net.URL;

public class SingUpFormController {

    SingUpService singUpService = new SingUpFormServiceImpl();

    @FXML
    private Button btnSingUp;

    @FXML
    private Button btnBackToLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRePassword;

    // 🔵 SIGN UP BUTTON
    @FXML
    void btnSingUpOnAction(ActionEvent event) {

        String firstName = txtFirstName.getText().trim();
        String lastName  = txtLastName.getText().trim();
        String email     = txtEmail.getText().trim();
        String password  = txtPassword.getText();
        String rePassword = txtRePassword.getText();


        if (firstName.isEmpty() || lastName.isEmpty()
                || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required");
            return;
        }


        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Email must be a valid @gmail.com address");
            return;
        }


        if (!password.equals(rePassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match");
            return;
        }


        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*]).{8,}$")) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Password must contain:\n" +
                            "- At least 8 characters\n" +
                            "- Uppercase letter\n" +
                            "- Lowercase letter\n" +
                            "- Special character");
            return;
        }


        if (singUpService.isEmailExist(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email already registered");
            return;
        }


        SingUpDTO dto = new SingUpDTO(firstName, lastName, email, password, rePassword);


        if (singUpService.register(dto)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful!");

            try {
                URL url = getClass().getResource("/view/dashBoard_Form.fxml");
                if (url == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Dashboard FXML not found");
                    return;
                }

                Stage stage = (Stage) btnSingUp.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(url)));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Dashboard load failed");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Registration failed");
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
