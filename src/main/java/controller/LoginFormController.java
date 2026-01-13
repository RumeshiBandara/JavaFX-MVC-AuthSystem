package controller;

import javafx.fxml.Initializable;
import service.LoginFormService;
import service.impl.LoginFormServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    LoginFormService loginFormService = new LoginFormServiceImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
