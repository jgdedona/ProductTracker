package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class contains static data objects that hold all the existing parts and products.*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int allPartsIndex = 5;
    private static int allProductsIndex = 4;

    /** Adds a new Part object to allParts.
     * @param newPart A new Part object to be added. */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds a new Product object to allProducts.
     * @param newProduct A new Product object to be added. */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Searches allParts for a part matching the provided ID.
     * @param partId The part ID to be used for matching. */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        Sanitization.displayAlert(13);
        return null;
    }

    /**
     * Searches allParts for any parts that contain the provided string in the part name.
     * @param partName The string to be used for matching.*/
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                filteredPartList.add(part);
            }
        }
        if (filteredPartList.isEmpty()) {
            Sanitization.displayAlert(13);
            return allParts;
        } else {
            return filteredPartList;
        }
    }

    /** Searches allProducts for a Product matching the provided ID.
     * @param productId The product ID to be used for matching. */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        Sanitization.displayAlert(13);
        return null;
    }

    /**
     * Searches allProducts for any products that contain the provided string in the product name.
     * @param productName The string to be used for matching.*/
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                filteredProductList.add(product);
            }
        }
        if (filteredProductList.isEmpty()) {
            Sanitization.displayAlert(13);
            return allProducts;
        } else {
            return filteredProductList;
        }
    }

    /**
     * Updates an existing Part object within allParts.
     * @param index The index of the part to be updated.
     * @param selectedPart The Part object attributes that will be used for the update operation. */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates an existing Product object within allParts.
     * @param index The index of the product to be updated.
     * @param selectedProduct The Product object attributes that will be used for the update operation. */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes an existing Part object within allParts.
     * @param selectedPart The Part object attributes that will be used for the delete operation.
     * @return boolean*/
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes an existing Product object within allParts.
     * @param selectedProduct The Product object attributes that will be used for the delete operation.
     * @return boolean */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Increments the allPartsIndex by 1.
     * This is used to determine the index for any newly added parts.*/
    public static void incrementPartsIndex() { allPartsIndex++; }

    /**
     * Increments the allProductsIndex by 1.
     * This is used to determine the index for any newly added products.*/
    public static void incrementProductsIndex() { allProductsIndex++; }

    public static int getAllPartsIndex() { return allPartsIndex; }

    public static int getAllProductsIndex() { return allProductsIndex; }




}
