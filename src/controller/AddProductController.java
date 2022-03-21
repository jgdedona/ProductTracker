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

/**
 * The AddProductController provides the control logic supporting the AddProduct.fxml scene.*/
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The intialize method sets the starting state for the scene. Both table views are populated with
     * the appropriate data.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.*/
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

    /** Implements the functionality of the search bar. */
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

    /** Adds parts selected in partTableView1 to associated Parts.
     * All parts added to associatedParts are populated in partTableView2.
     * @param event */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) {
        associatedParts.add(partTableView1.getSelectionModel().getSelectedItem());
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
     * Removes parts from associatedParts.
     * All removed parts are also removed from partTableView2.
     * @param event */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        if ((!(associatedParts.isEmpty()))) {
            Sanitization.displayConfirm();
            associatedParts.remove(partTableView2.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Creates a new Product object and adds it to Inventory.allProducts utilizing the text field and table entries.
     * Functions are called from the Sanitization class to sanitize entered data.
     * @param event
     * @throws IOException*/
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
        Sanitization.InvBetweenMaxAndMin(stock, min, max);

        if (Sanitization.getIsValid() == false) {
            return;
        }

        Inventory.addProduct(new Product(associatedParts, id, name, price, stock, min, max));

        Inventory.incrementProductsIndex();

        onActionDisplayMain(event);
    }

}
