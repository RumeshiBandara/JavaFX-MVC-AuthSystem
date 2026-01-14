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

    @FXML
    void btnSingUpOnAction(ActionEvent event){

        String firstName = txtFirstName.getText().trim();
        String lastName  = txtLastName.getText().trim();
        String email     = txtEmail.getText().trim();
        String password  = txtPassword.getText();
        String rePassword    = txtRePassword.getText();


        if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            showAlert("Error", "Email must be a valid @gmail.com address");
            return;
        }


        if (!password.equals(rePassword)) {
            showAlert("Error", "Passwords do not match");
            return;
        }


        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*]).{8,}$")) {
            showAlert("Error",
                    "Password must have:\n" +
                            "- At least 8 characters\n" +
                            "- Uppercase letter\n" +
                            "- Lowercase letter\n" +
                            "- Special character");
            return;
        }


        if (singUpService.isEmailExist(email)) {
            showAlert("Error", "Email already registered");
            return;
        }


        SingUpDTO dto = new SingUpDTO(
                firstName,
                lastName,
                email,
                password,
                rePassword
        );


        if (singUpService.register(dto)) {
            showAlert("Success", "Registration successful!");


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
            showAlert("Error", "Registration failed");
        }
    }


    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
