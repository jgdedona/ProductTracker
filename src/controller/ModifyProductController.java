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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing!");
    }

    @FXML
    private TableColumn<?, ?> idCol1;

    @FXML
    private TableColumn<?, ?> idCol2;

    @FXML
    private TableColumn<?, ?> invLvlCol1;

    @FXML
    private TableColumn<?, ?> invLvlCol2;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TableColumn<?, ?> nameCol1;

    @FXML
    private TableColumn<?, ?> nameCol2;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<?> partTableView1;

    @FXML
    private TableView<?> partTableView2;

    @FXML
    private TableColumn<?, ?> priceCol1;

    @FXML
    private TableColumn<?, ?> priceCol2;

    @FXML
    private TextField priceTxt;

    @FXML
    void onActionAddPart(ActionEvent event) {

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

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }
}
