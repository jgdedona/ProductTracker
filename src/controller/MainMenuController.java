package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import model.Sanitization;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The MainMenuController provides the control logic supporting the MainMenu.fxml scene.*/
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * The intialize method sets the starting state for the scene. Both table views are populated with
     * the appropriate data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTableView.setItems(Inventory.getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    private TableView<Product> prodTableView;

    @FXML
    private TextField productSearchTxt;

    /**
     * Implements search functionality for the prodTableView.
     * @param event */
    @FXML
    void onActionSearchOrFilterProduct(ActionEvent event) {
        if (productSearchTxt != null) {
            try {
                prodTableView.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(productSearchTxt.getText())));
            } catch (NumberFormatException e) {
                prodTableView.setItems(Inventory.lookupProduct(productSearchTxt.getText()));
            }
        }
    }

    /**
     * Implements search functionality for the partsTableView.
     * @param event */
    @FXML
    void onActionSearchOrFilterPart(ActionEvent event) {
        if (partSearchTxt != null) {
            try {
                partsTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(partSearchTxt.getText())));
            } catch (NumberFormatException e) {
                partsTableView.setItems(Inventory.lookupPart(partSearchTxt.getText()));
            }
        }
    }

    /**
     * Deletes the selected part from Inventory.allParts.
     * Deleted parts are also removed from the table view.
     * @param event */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        if ((!(partsTableView.getSelectionModel().isEmpty()))) {
            Sanitization.displayConfirm();
            Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        } else {
            Sanitization.displayAlert(12);
        }
    }

    /**
     * Deletes the selected part from Inventory.allProducts.
     * Deleted products are also removed from the table view.
     * @param event */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        try {
            if (!(prodTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty())) {
                Sanitization.displayAlert(11);
                return;
            } else {
                if (Sanitization.displayConfirm()) {
                    Inventory.deleteProduct(prodTableView.getSelectionModel().getSelectedItem());
                }
            }
        } catch (NullPointerException e) {
            Sanitization.displayAlert(12);
        }
    }

    /**
     * Loads and sets the AddPart.fxml scene when the add button is selected.
     * @param event
     * @throws IOException*/
    @FXML
    void onActionDisplayAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads and sets the AddProduct.fxml scene when the add button is selected.
     * @param event
     * @throws IOException*/
    @FXML
    void onActionDisplayAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads and sets the ModifyPart.fxml scene when the add button is selected.
     * The scene is automatically filled with data from the selected part.
     * @param event
     * @throws IOException*/
    @FXML
    void onActionDisplayModifyPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        loader.load();

        ModifyPartController MPController = loader.getController();
        try {
            MPController.sendPart(partsTableView.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            Sanitization.displayAlert(9);
            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads and sets the ModifyProduct.fxml scene when the add button is selected.
     * The scene is automatically filled with data from the selected part.
     * @param event
     * @throws IOException*/
    @FXML
    void onActionDisplayModifyProduct(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController MPrController = loader.getController();

        try {
            MPrController.sendProduct(prodTableView.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            Sanitization.displayAlert(10);
            return;
        }
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
}

