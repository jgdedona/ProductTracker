package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * The Sanitization class is used to sanitize any input that will be used to create or update Part and Product objects.*/
public abstract class Sanitization {
    private static boolean isValid = true;

    /**
     * Sets the static isValid variable to true.
     * This is used to determine if an addition or update should take place. */
    public static void setIsValidTrue() {
        isValid = true;
    }

    /**
     * Sets the static isValid variable to false.
     * This is used to determine if an addition or update should take place. */
    public static void setIsValidFalse() { isValid = false; }

    public static boolean getIsValid() {
        return isValid;
    }

    /**
     * Determines if a name string is empty and displays an alert if so.
     * @param name The name string to be checked. */
    public static void sanitizeName(String name) {
        if (name.isEmpty()) {
            displayAlert(1);
            setIsValidFalse();
        }
    }

    /**
     * Determines if a price text field entry is a valid double and displays an alert if not.
     * @param priceTxt The price text field to be checked.
     * @return double */
    public static double sanitizePrice(TextField priceTxt) {
        try {
            double price = Double.parseDouble(priceTxt.getText());
            if (price >= 0) {
                return price;
            } else {
                setIsValidFalse();
                displayAlert(3);
                return 0.0;
            }
        } catch (NumberFormatException e) {
            displayAlert(3);
            setIsValidFalse();
            return 0.0;
        }
    }

    /**
     * Determines if a stock text field entry is a valid int and displays an alert if not.
     * @param invTxt The stock text field to be checked.
     * @return int */
    public static int sanitizeStock(TextField invTxt) {
        try {
            int inventory = Integer.parseInt(invTxt.getText());
            if (inventory >= 0) {
                return inventory;
            } else {
                setIsValidFalse();
                displayAlert(2);
                return 0;
            }
        } catch (NumberFormatException e) {
            displayAlert(2);
            setIsValidFalse();
            return 0;
        }
    }

    /**
     * Determines if a min text field entry is a valid int and displays an alert if not.
     * @param minTxt The min text field to be checked.
     * @return int */
    public static int sanitizeMin(TextField minTxt) {
        try {
            int min = Integer.parseInt(minTxt.getText());
            if (min >= 0) {
                return min;
            } else {
                setIsValidFalse();
                displayAlert(5);
                return -1;
            }
        } catch (NumberFormatException e) {
            displayAlert(5);
            setIsValidFalse();
            return -1;
        }
    }

    /**
     * Determines if a max text field entry is a valid int and displays an alert if not.
     * @param maxTxt The max text field to be checked.
     * @return int: -1 is returned for invalid entries. This is utilized in the maxGreaterThanMin function below. */
    public static int sanitizeMax(TextField maxTxt) {
        try {
            int max = Integer.parseInt(maxTxt.getText());
            if (max >= 0) {
                return max;
            } else {
                setIsValidFalse();
                displayAlert(4);
                return -1;
            }
        } catch (NumberFormatException e) {
            displayAlert(4);
            setIsValidFalse();
            return -1;
        }
    }

    /**
     * Determines if the max field is greater or equal to the min field.
     * @param min The minimum value
     * @param max The maximum value */
    public static void maxGreaterThanMin(int min, int max) {
        if (min == -1 || max == -1) {
            return;
        } else if (max < min) {
            displayAlert(6);
            setIsValidFalse();
            return;
        }
    }

    public static void InvBetweenMaxAndMin(int stock, int min, int max) {
        if (stock < min || stock > max) {
            displayAlert(14);
            setIsValidFalse();
        }
    }

    /**
     * Determines if a machineID text field entry is a valid int and displays an alert if not.
     * @param machineIdTxt The machine ID text field to be checked.
     * @return int */
    public static int sanitizeMachineId(TextField machineIdTxt) {
        try {
            int machineId = Integer.parseInt(machineIdTxt.getText());
            if (machineId >= 0) {
                return machineId;
            } else {
                setIsValidFalse();
                displayAlert(7);
                return 0;
            }
        } catch (NumberFormatException e) {
            displayAlert(7);
            setIsValidFalse();
            return 0;
        }
    }

    /**
     * Determines if a name string is empty and displays an alert if so.
     * @param companyName The name string to be checked. */
    public static void sanitizeCompanyName(String companyName) {
        if (companyName.isEmpty()) {
            displayAlert(8);
            setIsValidFalse();
        }
    }

    /**
     * Displays a confirmation alert.
     * Used whenever a Part or Product object is deleted.
     * @return boolean*/
    public static boolean displayConfirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Deletion of any parts, products, and associated parts is permanent.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Defines all of the alert types used by the sanitization functions.
     * @param alertType Used to determine which case to call. */
    public static void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        switch (alertType) {
            case 1:
                alert.setHeaderText("Invalid Name");
                alert.setContentText("There is no data present in the Name field");
                alert.showAndWait();
                break;
            case 2:
                alert.setHeaderText("Invalid Inventory Entry");
                alert.setContentText("Inventory must be a positive integer");
                alert.showAndWait();
                break;
            case 3:
                alert.setHeaderText("Invalid Price Entry");
                alert.setContentText("Price must be a positive double");
                alert.showAndWait();
                break;
            case 4:
                alert.setHeaderText("Invalid Max Entry");
                alert.setContentText("Max must be a positive integer");
                alert.showAndWait();
                break;
            case 5:
                alert.setHeaderText("Invalid Min Entry");
                alert.setContentText("Min must be a positive integer");
                alert.showAndWait();
                break;
            case 6:
                alert.setHeaderText("Invalid Max and Min Entries");
                alert.setContentText("Max must be greater than min");
                alert.showAndWait();
                break;
            case 7:
                alert.setHeaderText("Invalid Machine ID Entry");
                alert.setContentText("Machine ID must be a positive integer");
                alert.showAndWait();
                break;
            case 8:
                alert.setHeaderText("Invalid Company Name");
                alert.setContentText("There is no data present in the Company Name field");
                alert.showAndWait();
                break;
            case 9:
                alert.setHeaderText("No Part Selected");
                alert.setContentText("You must select a part to modify");
                alert.showAndWait();
                break;
            case 10:
                alert.setHeaderText("No Product Selected");
                alert.setContentText("You must select a product to modify");
                alert.showAndWait();
                break;
            case 11:
                alert.setHeaderText("Product Deletion Failed");
                alert.setContentText("You cannot delete a product until you have removed all of its associated parts");
                alert.showAndWait();
                break;
            case 12:
                alert.setHeaderText("No Part or Product Selected");
                alert.setContentText("Please select a part or product to delete");
                alert.showAndWait();
                break;
            case 13:
                alert.setHeaderText("Part or Product Not Found");
                alert.setContentText("There were no matching results for your search");
                alert.showAndWait();
                break;
            case 14:
                alert.setHeaderText("Stock outside of minimum and maximum bounds");
                alert.setContentText("Inv must be an integer between the supplied min and max values");
                alert.showAndWait();
                break;
        }
    }
}
