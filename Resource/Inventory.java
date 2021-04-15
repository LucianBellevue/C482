package Resource;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Object class named inventory which provides data for Parts and Products.
 * @author Diego Medina
 */
public class Inventory {

    /**
     * List of all the parts in the inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Unique Part IDs variable.
     */
    private static int partId = 0;

    /**
     * Gets the list of allParts.
     * @return a list of parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Adds part to Inventory.
     * @param newPart The added part object.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Provides a new part ID.
     * @return unique ID.
     */
    public static int getFreshPartId() {
        return ++partId;
    }

    /**
     * Searches part list by ID.
     * @param partId the ID of the part.
     * @return the searched object IF found, it will return NULL if it is not.
     */
    public static Part lookupPart(int partId) {
        Part foundPart = null;
        for (Part part : allParts) {
            if (part.getId() == partId){
                foundPart = part;
            }
        }
        return foundPart;
    }

    /**
     * Searches part list by Name.
     * @param partName the name of the part.
     * @return the list of found parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Updates the part by replacing it on the list of parts.
     * @param index index of part to be updated.
     * @param chosenPart part to be updated.
     */
    public static void updatePart(int index, Part chosenPart) {
        allParts.set(index, chosenPart);
    }

    /**
     * Deletes part from list.
     * @param chosenPart object to be deleted.
     * @return boolean if object deleted.
     */
    public static boolean deletePart(Part chosenPart) {
        if (allParts.contains(chosenPart)) {
            allParts.remove(chosenPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * List of all the products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Unique Product IDs variable.
     */
    private static int productId = 0;

    /**
     * Gets the list of allProducts.
     * @return a list of products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Adds product to Inventory.
     * @param newProduct The added product object.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Provides a new product ID.
     * @return Unique ID.
     */
    public static int getFreshProductId() {
        return ++productId;
    }

    /**
     * Searches product list by ID.
     * @param productId the ID of the product.
     * @return the searched object IF found, it will return NULL if it is not.
     */
    public static Product lookupProduct(int productId) {
        Product foundProduct = null;
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    /**
     * Searches product by name.
     * @param productName The name of the product.
     * @return The list of found products.
     */
    public static ObservableList<Product> lookupProducts(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (Product product: allProducts) {
            if (product.getName().equals(productName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * Updates the product by replacing it on the list of products.
     * @param index index of product being updated.
     * @param chosenProduct product to be updated.
     */
    public static void updateProduct(int index, Product chosenProduct) {
        allProducts.set(index,chosenProduct);
    }

    /**
     * Deletes product from the list.
     * @param chosenProduct object to be deleted.
     * @return boolean if object was deleted.
     */
    public static boolean deleteProduct(Product chosenProduct) {
        if (allProducts.contains(chosenProduct)) {
            allProducts.remove(chosenProduct);
            return true;
        }
        else {
            return false;
        }
    }
}
