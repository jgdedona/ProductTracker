package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddPartController provides the control logic supporting the AddPart.fxml scene.*/
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * The intialize method sets the starting state for the scene. The in-house radio button
     * is selected by default.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRBtn.setSelected(true);
    }

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
    private Button onActionDisplayMain;

    @FXML
    private Button onActionSavePart;

    @FXML
    private RadioButton outsourceRBtn;

    @FXML
    private TextField priceTxt;

    @FXML
    private javafx.scene.control.Label machineIdOrCompanyNameLabel;

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
     * Creates a new Part object and adds it to Inventory.allParts utilizing the text field entries.
     * Functions are called from the Sanitization class to sanitize entered data.
     * @param event
     * @throws IOException
     *
     * RUNTIME ERROR:
     * When this function was initially created, it did not utilize the setIsValidTrue function
     * in the beginning. If the static Sanitization.isValid variable was set to false prior to the
     * function being called, it was impossible to create a new Part object because of this.
     * This error was corrected by always setting the Sanitization.isValid variable to true
     * at the beginning of the function. This ensured that new objects could be created
     * when all the entered text field data was valid. */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = Inventory.getAllPartsIndex();
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
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }
        } else {
            String companyName = machineIdOrCompanyNameTxt.getText();
            Sanitization.sanitizeCompanyName(companyName);
            if (Sanitization.getIsValid()) {
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
        }

        if (Sanitization.getIsValid() == false) {
            return;
        }

        Inventory.incrementPartsIndex();

        onActionDisplayMain(event);
    }

}
