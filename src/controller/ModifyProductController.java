package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView1.setItems(Inventory.getAllParts());
        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        invLvlCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void sendProduct(Product product) {
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));

        associatedParts.addAll(product.getAllAssociatedParts());
        partTableView2.setItems(associatedParts);
        idCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        invLvlCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        nameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private TextField idTxt;

    @FXML
    private TableColumn<Part, Integer> idCol1;

    @FXML
    private TableColumn<Part, Integer> idCol2;

    @FXML
    private TableColumn<Part, Integer> invLvlCol1;

    @FXML
    private TableColumn<Part, Integer> invLvlCol2;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TableColumn<Part, String> nameCol1;

    @FXML
    private TableColumn<Part, String> nameCol2;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<Part> partTableView1;

    @FXML
    private TableView<Part> partTableView2;

    @FXML
    private TableColumn<Part, Double> priceCol1;

    @FXML
    private TableColumn<Part, Double> priceCol2;

    @FXML
    private TextField priceTxt;

    @FXML
    void onActionAddPart(ActionEvent event) {
        associatedParts.add(partTableView1.getSelectionModel().getSelectedItem());
    }

    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        if (Sanitization.displayConfirm()) {
            associatedParts.remove(partTableView2.getSelectionModel().getSelectedItem());
        } else {
            return;
        }
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
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

        if (Sanitization.getIsValid() == false) {
            return;
        }

        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == id) {
                Inventory.updateProduct(i, new Product(associatedParts, id, name, price, stock, min, max));
            }
        }

        onActionDisplayMain(event);
    }
}
