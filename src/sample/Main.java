package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Part[] parts = {
                new InHouse(1, "Chain", 4.99, 5, 1, 10, 27),
                new InHouse( 2, "Spokes", 19.99, 5, 1, 10, 132),
                new Outsourced(3, "Rims", 29.99, 4, 1, 10, "Bike Parts Inc."),
                new Outsourced(4, "Wheels",39.99, 5, 1, 10, "Bike Parts Inc."),
        };

        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        associatedParts.addAll(parts);

        Product[] products = {
                new Product(associatedParts, 1, "Red Bike", 99.99, 2, 1, 10),
                new Product(associatedParts, 2, "Blue Bike", 159.99, 3, 1, 10),
                new Product(associatedParts, 3, "Green Bike", 199.99, 1, 1, 10)
        };

        for (int i = 0; i < parts.length; i++) {
            Inventory.addPart(parts[i]);
        }

        for (int i = 0; i < products.length; i++) {
            Inventory.addProduct(products[i]);
        }

        launch(args);
    }
}
