package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
/**
 * The Inventory Management program manages an inventory of parts
 * and products. The program tracks which parts have been assigned to
 * which products and a variety of relevant characteristics for each
 * part and product.
 *
 * FUTURE ENHANCEMENT:
 * This program can be enhanced through the addition of a Sales class that keeps track
 * of the sale of products throughout the month. This would present an opportunity to
 * integrate the inventory management functionality with sales tracking data.
 * In addition to the Sales class, the GUI would need to be updated to support
 * sales tracking functionality. For example, a 'Sold' button could be implemented
 * on the Main Menu that updates static variables in the Sales class and updates
 * the appropriate static data objects in the Inventory class. This would also
 * require the completed program to be connected to a database for persistent
 * storage. */

public class Main extends Application {

    /**
     * The start method creates and sets the primary stage.
     * @param primaryStage
     * @throws Exception*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     * The main method is the starting point for the program. Test data is initialized inside this method.
     * @param args*/
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
