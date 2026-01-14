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

    private final SingUpService singUpService = new SingUpFormServiceImpl();



    @FXML
    private Button btnSingUp;

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

        // 1️⃣ Empty check
        if (firstName.isEmpty() || lastName.isEmpty()
                || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required");
            return;
        }

        // 2️⃣ Email validation
        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Email must be a valid @gmail.com address");
            return;
        }

        // 3️⃣ Password match
        if (!password.equals(rePassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match");
            return;
        }

        // 4️⃣ Password strength
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*]).{8,}$")) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Password must contain:\n" +
                            "- At least 8 characters\n" +
                            "- Uppercase letter\n" +
                            "- Lowercase letter\n" +
                            "- Special character");
            return;
        }

        // 5️⃣ Email exists
        if (singUpService.isEmailExist(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email already registered");
            return;
        }

        // 6️⃣ DTO (CORRECT)
        SingUpDTO dto = new SingUpDTO(
                firstName,
                lastName,
                email,
                password,
                rePassword
        );

        // 7️⃣ Register
        if (singUpService.register(dto)) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Success", "Registration successful!");

            try {
                Stage stage = (Stage) btnSingUp.getScene().getWindow();
                stage.setScene(new Scene(
                        FXMLLoader.load(getClass().getResource("/view/dashBoard_Form.fxml"))
                ));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Error", "Registration failed");
        }
    }

    // 🔙 BACK TO LOGIN BUTTON (FIXED)

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
