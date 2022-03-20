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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private javafx.scene.control.Label machineIdOrCompanyNameLabel;

    @FXML
    private TextField machineIdOrCompanyNameTxt;

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
    void onActionSavePart(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        double price = 0.0;
        int stock = 0;
        int min = 0;
        int max = 0;

        Sanitization.setIsValidTrue();

        Sanitization.sanitizeName(name);
        price = Sanitization.sanitizePrice(priceTxt);
        stock = Sanitization.sanitizeStock(invTxt);
        min = Sanitization.sanitizeMin(minTxt);
        max = Sanitization.sanitizeMax(maxTxt);
        Sanitization.maxGreaterThanMin(min, max);

        if (inHouseRBtn.isSelected()) {
            int machineId = 0;
            machineId = Sanitization.sanitizeMachineId(machineIdOrCompanyNameTxt);
            if (Sanitization.getIsValid()) {
                for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                    if (Inventory.getAllParts().get(i).getId() == id) {
                        Inventory.updatePart(i, new InHouse(id, name, price, stock, min, max, machineId));
                    }
                };
            }
        } else {
            String companyName = machineIdOrCompanyNameTxt.getText();
            Sanitization.sanitizeCompanyName(companyName);
            if (Sanitization.getIsValid()) {
                for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                    if (Inventory.getAllParts().get(i).getId() == id) {
                        Inventory.updatePart(i, new Outsourced(id, name, price, stock, min, max, companyName));
                    }
                }
            }
        }

        if (Sanitization.getIsValid() == false) {
            return;
        }

        onActionDisplayMain(event);
    }

    public void sendPart (Part part) {
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            inHouseRBtn.setSelected(true);
            machineIdOrCompanyNameTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            outsourceRBtn.setSelected(true);
            machineIdOrCompanyNameTxt.setText(((Outsourced) part).getCompanyName());
        }
    }

}
