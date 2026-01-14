package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {

    @FXML
    private Button btnLogOut;

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogOut.getScene().getWindow();


            Parent root = FXMLLoader.load(getClass().getResource("/view/login_Form.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
