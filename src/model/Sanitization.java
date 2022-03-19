package model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

public abstract class Sanitization {
    private static boolean isValid = true;

    public static void setIsValidTrue() {
        isValid = true;
    }

    public static void setIsValidFalse() {
        isValid = false;
    }

    public static void sanitizeName(String name) {
        if (name.isEmpty()) {
            displayAlert(1);
            setIsValidFalse();
        }
    }

    public static double sanitizePrice(TextField priceTxt) {
        try {
            return Double.parseDouble(priceTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(3);
            setIsValidFalse();
            return 0.0;
        }
    }

    public static int sanitizeStock(TextField invTxt) {
        try {
            return Integer.parseInt(invTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(2);
            setIsValidFalse();
            return 0;
        }
    }

    public static int sanitizeMin(TextField minTxt) {
        try {
            return Integer.parseInt(minTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(5);
            setIsValidFalse();
            return 0;
        }
    }

    public static int sanitizeMax(TextField maxTxt) {
        try {
            return Integer.parseInt(maxTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(4);
            setIsValidFalse();
            return 0;
        }
    }

    public static void maxGreaterThanMin(int min, int max) {
        if (min == -1 || max == -1) { // GOTTA FIX THIS SO -1 returns on invalid min or max
            return;
        } else if (max < min) {
            displayAlert(6);
            setIsValidFalse();
            return;
        } // May need another else statement. Not sure.
    }

    public static int sanitizeMachineId(TextField machineIdTxt) {
        try {
            return Integer.parseInt(machineIdTxt.getText());
        } catch (NumberFormatException e) {
            displayAlert(7);
            setIsValidFalse();
            return 0;
        }
    }

    public static String sanitizeCompanyName(TextField companyNameTxt) {
        String companyName = companyNameTxt.getText();
        if (companyName.isEmpty()) {
            displayAlert(8);
            setIsValidFalse();
            return "";
        } else {
            return companyName;
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
                alert.setContentText("Inventory must be an integer");
                alert.showAndWait();
                break;
            case 3:
                alert.setContentText("Price must be a double");
                alert.showAndWait();
                break;
            case 4:
                alert.setContentText("Max must be an integer");
                alert.showAndWait();
                break;
            case 5:
                alert.setContentText("Min must be an integer");
                alert.showAndWait();
                break;
            case 6:
                alert.setContentText("Max must be greater than min");
                alert.showAndWait();
                break;
            case 7:
                alert.setContentText("Machine ID must be an integer");
                alert.showAndWait();
                break;
            case 8:
                alert.setContentText("There is no data present in the Company Name field");
                alert.showAndWait();
                break;
        }
    }
}
