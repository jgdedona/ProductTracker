package model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public abstract class Sanitization {
    private static boolean isValid = true;

    public static void setIsValidTrue() {
        isValid = true;
    }

    public static void setIsValidFalse() {
        isValid = false;
    }

    public static boolean getIsValid() {
        return isValid;
    }

    public static void sanitizeName(String name) {
        if (name.isEmpty()) {
            displayAlert(1);
            setIsValidFalse();
        }
    }

    public static double sanitizePrice(TextField priceTxt) {
        try {
            double price = 0.0;
            price = Double.parseDouble(priceTxt.getText());
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

    public static int sanitizeStock(TextField invTxt) {
        try {
            int inventory = 0;
            inventory = Integer.parseInt(invTxt.getText());
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

    public static int sanitizeMin(TextField minTxt) {
        try {
            int min = 0;
            min = Integer.parseInt(minTxt.getText());
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

    public static int sanitizeMax(TextField maxTxt) {
        try {
            int max = 0;
            max = Integer.parseInt(maxTxt.getText());
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

    public static void maxGreaterThanMin(int min, int max) {
        if (min == -1 || max == -1) {
            return;
        } else if (max < min) {
            displayAlert(6);
            setIsValidFalse();
            return;
        } // May need another else statement. Not sure.
    }

    public static int sanitizeMachineId(TextField machineIdTxt) {
        try {
            int machineId = 0;
            machineId = Integer.parseInt(machineIdTxt.getText());
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

    public static void sanitizeCompanyName(String companyName) {
        if (companyName.isEmpty()) {
            displayAlert(8);
            setIsValidFalse();
        }
    }


    public static void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");

        switch (alertType) {
            case 1:
                alert.setContentText("There is no data present in the Name field");
                alert.showAndWait();
                break;
            case 2:
                alert.setContentText("Inventory must be a positive integer");
                alert.showAndWait();
                break;
            case 3:
                alert.setContentText("Price must be a positive double");
                alert.showAndWait();
                break;
            case 4:
                alert.setContentText("Max must be a positive integer");
                alert.showAndWait();
                break;
            case 5:
                alert.setContentText("Min must be a positive integer");
                alert.showAndWait();
                break;
            case 6:
                alert.setContentText("Max must be greater than min");
                alert.showAndWait();
                break;
            case 7:
                alert.setContentText("Machine ID must be a positive integer");
                alert.showAndWait();
                break;
            case 8:
                alert.setContentText("There is no data present in the Company Name field");
                alert.showAndWait();
                break;
        }
    }
}
