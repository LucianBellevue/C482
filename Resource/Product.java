package Resource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Object named Product and associated parts.
 *
 * @author Diego Medina
 */
public class Product {

    /**
     * A list of parts related to the products.
     */
    private ObservableList<Part> relatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for instance of a product.
     *
     * @param id    product ID.
     * @param name  product name.
     * @param price product price.
     * @param stock product inventory level.
     * @param min   product minimum inventory.
     * @param max   product maximum inventory.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Product ID.
     */
    private int id;

    /**
     * Product Name.
     */
    private String name;

    /**
     * Product Price.
     */
    private double price;

    /**
     * Product inventory level.
     */
    private int stock;

    /**
     * minimum level of Product.
     */
    private int min;

    /**
     * maximum level of Product.
     */
    private int max;

    /**
     * gets id of product.
     *
     * @return the ID.
     */
    public int getId() {
        return id;
    }

    /**
     * sets id of product.
     *
     * @param id the ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets name of product.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * sets name of product.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets price of product.
     *
     * @return the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets price of product.
     *
     * @param price the price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * gets stock of product.
     *
     * @return the stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets stock of product.
     *
     * @param stock the stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * gets minimum stock of product.
     *
     * @return minimum stock.
     */
    public int getMin() {
        return min;
    }

    /**
     * sets minimum stock of product.
     *
     * @param min minimum stock.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * gets maximum stock of product.
     *
     * @return maximum stock.
     */
    public int getMax() {
        return max;
    }

    /**
     * sets maximum stock of product.
     *
     * @param max maximum stock.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Removes part from the related parts list of the product.
     *
     * @param chosenRelatedPart the part to be removed.
     * @return a boolean IF removed.
     */
    public boolean removeRelatedPart(Part chosenRelatedPart) {
        if (relatedParts.contains(chosenRelatedPart)) {
            relatedParts.remove(chosenRelatedPart);
            return true;
        } else
            return false;
    }

    /**
     * gets the list of parts for the product.
     *
     * @return List of all parts.
     */
    public ObservableList<Part> getAllRelatedParts() {
        return relatedParts;
    }

    /**
     * adds part to the related part list of product.
     * @param part the part being added.
     */
    public void addRelatedPart(Part part) {
        relatedParts.add(part);
    }
}
