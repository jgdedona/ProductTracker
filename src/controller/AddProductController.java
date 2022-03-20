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

public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView1.setItems(Inventory.getAllParts());
        idCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        invLvlCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView2.setItems(associatedParts);
        partIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        invLvlCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    private TableColumn<Part, Integer> idCol1;

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
    private TextField nameTxt;

    @FXML
    private TableColumn<Part, Integer> partIdCol2;

    @FXML
    private TableColumn<Part, String> partNameCol1;

    @FXML
    private TableColumn<Part, String> partNameCol2;

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
    void onActionSearchOrFilterPart(ActionEvent event) {
        if (partSearchTxt != null) {
            try {
                partTableView1.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(partSearchTxt.getText())));
            } catch (NumberFormatException e) {
                partTableView1.setItems(Inventory.lookupPart(partSearchTxt.getText()));
            }
        }
    }

    @FXML
    void onActionAddPartToProduct(ActionEvent event) {
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
        if ((!(associatedParts.isEmpty()))) {
            Sanitization.displayConfirm();
            associatedParts.remove(partTableView2.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        int id = Inventory.getAllProductsIndex();
        String name = nameTxt.getText();

        Sanitization.setIsValidTrue();

        Sanitization.sanitizeName(name);
        double price = Sanitization.sanitizePrice(priceTxt);
        int stock = Sanitization.sanitizeStock(invTxt);
        int min = Sanitization.sanitizeMin(minTxt);
        int max = Sanitization.sanitizeMax(maxTxt);
        Sanitization.maxGreaterThanMin(min, max);

        if (Sanitization.getIsValid() == false) {
            return;
        }

        Inventory.addProduct(new Product(associatedParts, id, name, price, stock, min, max));

        Inventory.incrementProductsIndex();

        onActionDisplayMain(event);
    }

}
