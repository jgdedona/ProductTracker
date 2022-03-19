package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartOutsourcedController implements Initializable {

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRBtn.setSelected(true);
    }

    @FXML
    private javafx.scene.control.Label machineIdOrCompanyNameLabel;

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
    void onActionSetTextCompanyName(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Company Name");
    }

    @FXML
    void onActionSetTextMachineId(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Machine ID");
    }

    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSavePart(ActionEvent event) {

    }
}
