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

/**
 * The ModifyPartController provides the control logic supporting the ModifyPart.fxml scene.*/
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * The intialize method sets the starting state for the scene.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.*/
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

    /**
     * Sets the text to 'Company Name' in the machine ID/company name label.
     * Triggered when the outsourced radio button is selected. */
    @FXML
    void onActionSetTextCompanyName(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Company Name");
    }

    /**
     * Sets the text to 'Machine ID' in the machine ID/company name label.
     * Triggered when the in-house radio button is selected. */
    @FXML
    void onActionSetTextMachineId(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Machine ID");
    }

    /**
     * Displays the Main Menu scene from MainMenu.fxml when the cancel button is selected.
     * @param event
     * @throws IOException*/
    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Updates an existing Part object in Inventory.allParts utilizing the text field entries.
     * Functions are called from the Sanitization class to sanitize entered data.
     * @param event
     * @throws IOException*/
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();

        Sanitization.setIsValidTrue();

        Sanitization.sanitizeName(name);
        double price = Sanitization.sanitizePrice(priceTxt);
        int stock = Sanitization.sanitizeStock(invTxt);
        int min = Sanitization.sanitizeMin(minTxt);
        int max = Sanitization.sanitizeMax(maxTxt);
        Sanitization.maxGreaterThanMin(min, max);

        if (inHouseRBtn.isSelected()) {
            int machineId = 0;
            machineId = Sanitization.sanitizeMachineId(machineIdOrCompanyNameTxt);
            if (Sanitization.getIsValid()) {
                for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                    if (Inventory.getAllParts().get(i).getId() == id) {
                        Inventory.updatePart(i, new InHouse(id, name, price, stock, min, max, machineId));
                    }
                }
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

    /**
     * The sendPart function is used to auto-populate the text fields.
     * This function is called in the MainMenuController to pass the selected part
     * values to the ModifyPartController when the modify button is pressed.
     * @param part A Part object. This is derived from Inventory.getAllParts when the function is called. */
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
