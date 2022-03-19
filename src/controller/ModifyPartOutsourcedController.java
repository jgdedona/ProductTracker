package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartOutsourcedController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing!");
    }

    @FXML
    private TextField companyNameTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private RadioButton outsourceRBtn;

    @FXML
    private TextField priceTxt;

    @FXML
    void onActionDisplayMain(ActionEvent event) {

    }

    @FXML
    void onActionSavePart(ActionEvent event) {

    }
}
