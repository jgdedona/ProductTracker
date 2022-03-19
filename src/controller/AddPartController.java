package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

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

    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSetTextCompanyName(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Company Name");
    }

    @FXML
    void onActionSetTextMachineId(ActionEvent event) {
        machineIdOrCompanyNameLabel.setText("Machine ID");
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int id = Inventory.getAllPartsIndex();
        String name = nameTxt.getText();
        double price = 0.0;
        int stock = 0;
        int min = 0;
        int max = 0;
        boolean partAdded = true;

        if (name.isEmpty()) {
            displayAlert(1);
            partAdded = false;
        }

        try {
            price = Double.parseDouble(priceTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(3);
            partAdded = false;
        }

        try {
            stock = Integer.parseInt(invTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(2);
            partAdded = false;
        }

        try {
            min = Integer.parseInt(minTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(5);
            partAdded = false;
        }

        try {
            max = Integer.parseInt(maxTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(4);
            partAdded = false;
        }

        if (max < min) {
            displayAlert(6);
            partAdded = false;
        }

        if (inHouseRBtn.isSelected()) {
            int machineId = 0;
            try {
                machineId = Integer.parseInt(machineIdOrCompanyNameTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            } catch (NumberFormatException e) {
                displayAlert(7);
                partAdded = false;
            }
        } else {
            String companyName = machineIdOrCompanyNameTxt.getText();
            if (companyName.isEmpty()) {
                displayAlert(8);
                partAdded = false;
            } else {
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

        }

        if (partAdded == false) {
            return;
        }

        Inventory.incrementPartsIndex();

        onActionDisplayMain(event);
//        I CAN DEFINITELY REFACTOR THIS
    }

//    Maybe move this to a new class?
    public static void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        switch (alertType) {
            case 1:
                alert.setContentText("There is no data present in the Name field");
                alert.showAndWait();
                break;
            case 2:
                alert.setContentText("Inventory must be an integer");
                alert.showAndWait();
                break;
            case 3:
                alert.setContentText("Price must be a double");
                alert.showAndWait();
                break;
            case 4:
                alert.setContentText("Max must be an integer");
                alert.showAndWait();
                break;
            case 5:
                alert.setContentText("Min must be an integer");
                alert.showAndWait();
                break;
            case 6:
                alert.setContentText("Max must be greater than min");
                alert.showAndWait();
                break;
            case 7:
                alert.setContentText("Machine ID must be an integer");
                alert.showAndWait();
                break;
            case 8:
                alert.setContentText("There is no data present in the Company Name field");
                alert.showAndWait();
                break;
        }
    }

}
