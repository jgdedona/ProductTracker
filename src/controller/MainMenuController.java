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

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

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

    @FXML
    void onActionDeletePart(ActionEvent event) {
        if ((!(partsTableView.getSelectionModel().isEmpty()))) {
            Sanitization.displayConfirm();
            Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        } else {
            Sanitization.displayAlert(12);
        }
    }

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

    @FXML
    void onActionDisplayAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDisplayAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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

