package Main;


import Resource.Inventory;
import Resource.Part;
import Resource.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class which provides logic for the main panel in the application.
 *
 * If no part is selected when user clicks on the modify button, a runtime error will occur.
 * The errors is caused by a null value being passed to the initialize method.
 *
 * @author Diego Medina
 */
public class MainPanelController implements Initializable {

    /**
     * Exits Main Program.
     *
     * @param event exit Action for button.
     */
    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Part object that is selected by user in the Part Table View.
     */
    private static Part partToMod;

    /**
     * gets part selected by user.
     * @return a part, returns null if nothing is selected.
     */
    public static Part getPartToMod() { return partToMod;}

    /**
     * Table View for the Part section.
     */
    @FXML private TableView<Part> partTableView;

    /**
     * Text field for search box in parts.
     */
    @FXML private TextField partSearchBox;

    /**
     * The Parts table ID column.
     */
    @FXML private TableColumn<Part,Integer> partColumnId;

    /**
     * The Parts table Name column.
     */
    @FXML private TableColumn<Part,String> partColumnName;

    /**
     * The Parts table Stock column.
     */
    @FXML private TableColumn<Part,Integer> partColumnStock;

    /**
     * The Parts table Price column.
     */
    @FXML private TableColumn<Part,Double> partColumnPrice;

    /**
     * The view for the Product section.
     */
    @FXML private TableView<Product> productTableView;

    /**
     * Product object that is selected by user in the Product Table View.
     */
    public static Product productToMod;

    /**
     * gets the product selected by user.
     * @return a product, returns null if nothing is selected.
     */
    public static Product getProductToMod() {return productToMod;}

    /**
     * The Products table ID column.
     */
    @FXML private TableColumn<Product,Integer> productColumnId;

    /**
     * The Products table Name column.
     */
    @FXML private TableColumn<Product,String> productColumnName;

    /**
     * The Products table Stock column.
     */
    @FXML private TableColumn<Product,Integer> productColumnStock;

    /**
     * The Products table Price column.
     */
    @FXML private TableColumn<Product,Double> productColumnPrice;

    /**
     * The field for search box in products.
     */
    @FXML private TextField productSearchBox;

    /**
     * Loads the AddPartControl.
     * @param event Add action button.
     * @throws IOException from FXMLLoader.
     */
    @FXML void addPartButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../Part/AddPartPanel.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Removes the part selected by user.
     * Displays an error if no part is selected.
     * Displays confirmation alert before removing part.
     * @param event Delete action.
     */
    @FXML
    void removePartButton(ActionEvent event) {
        Part chosenPart = partTableView.getSelectionModel().getSelectedItem();
        if (chosenPart == null) {
            alertStatus(3);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete part?");
            Optional<ButtonType> outcome = alert.showAndWait();

            if(outcome.isPresent() && outcome.get() == ButtonType.OK) {
                Inventory.deletePart(chosenPart);
            }
        }
    }

    /**
     * Loads ModPartPanelControl.
     * Error if no part is selected.
     * @param event modPartButton.
     * @throws IOException from FXMLLoader.
     */
    @FXML void modPartButton(ActionEvent event) throws IOException {
        partToMod = partTableView.getSelectionModel().getSelectedItem();
        if(partToMod == null) {
            alertStatus(3);
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("../Part/ModPartPanel.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    /**
     * Searches parts in table view based on input by Name or ID.
     * Reloads tableview with search outcome.
     * @param event Search action.
     */
    @FXML void partSearchEventButton(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String searchWord = partSearchBox.getText();
        for (Part part: allParts) {
            if(String.valueOf(part.getId()).contains(searchWord) || part.getName().contains(searchWord)) {
                foundParts.add(part);
            }
        }
        partTableView.setItems(foundParts);
        if(foundParts.size() == 0) {alertStatus(1);}
    }

    /**
     * Resets table view when search box is empty.
     * @param event Search key is empty.
     */
    @FXML
    void partSearchKeyReset(KeyEvent event) {
        if(partSearchBox.getText().isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Loads the AddProductControl.
     * @param event Add action button.
     * @throws IOException from FXMLLoader
     */
    @FXML void addProductButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../Product/AddProductPanel.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Loads panel ModProductPanel.
     * @param event Modify event button.
     * @throws IOException from FXMLLoader.
     */
    @FXML void productModEvent(ActionEvent event) throws IOException {

        productToMod = productTableView.getSelectionModel().getSelectedItem();

        if(productToMod == null) {
            alertStatus(4);
        }
        else {
            Parent parent = FXMLLoader.load(getClass().getResource("../Product/ModifyProductView.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    /**
     * Removes selected product.
     * Displays error if no product has been selected.
     * Displays confirmation for selected product to be removed.
     * @param event delete button.
     */
    @FXML void removeProductButton(ActionEvent event) {
        Product chosenProduct = productTableView.getSelectionModel().getSelectedItem();
        if(chosenProduct == null) {alertStatus(4);}
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Notice");
            alert.setContentText("Are you sure you want to delete product?");
            Optional<ButtonType> outcome = alert.showAndWait();
            if(outcome.isPresent() && outcome.get() == ButtonType.OK) {
                ObservableList<Part> relatedParts = chosenProduct.getAllRelatedParts();
                if(relatedParts.size() >=1) {alertStatus(5);}
                else {
                    Inventory.deleteProduct(chosenProduct);
                }
            }
        }
    }

    /**
     * Resets table view when search box is empty.
     * @param event Search key is empty.
     */
    @FXML
    void productSearchKeyReset(KeyEvent event) {
        if(productSearchBox.getText().isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Searches products in table view based on input by Name or ID.
     * Reloads tableview with search outcome.
     * @param event Search action.
     */
    @FXML void productSearchEventButton(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        String searchWord = productSearchBox.getText();
        for(Product product: allProducts) {
            if(String.valueOf(product.getId()).contains(searchWord) || product.getName().contains(searchWord)) {
                foundProduct.add(product);
            }
        }
        productTableView.setItems(foundProduct);
        if(foundProduct.size() == 0) {
            alertStatus(2);
        }
    }

    /**
     * Shows alert status.
     * @param alertStatusType shows different alert status.
     */
    private void alertStatus(int alertStatusType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert errorAlert= new Alert(Alert.AlertType.ERROR);
        switch(alertStatusType) {
            case 1:
                alert.setTitle("Notice");
                alert.setHeaderText("Part could not be found");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Notice");
                alert.setHeaderText("Product could not be found");
                alert.showAndWait();
                break;
            case 3:
                errorAlert.setTitle("ERROR");
                errorAlert.setHeaderText("No part selected");
                errorAlert.showAndWait();
                break;
            case 4:
                errorAlert.setTitle("ERROR");
                errorAlert.setHeaderText("No product selected");
                errorAlert.showAndWait();
                break;
            case 5:
                errorAlert.setTitle("ERROR");
                errorAlert.setHeaderText("Parts Related to Product");
                errorAlert.setContentText("Parts must be removed from product before product is removed");
                errorAlert.showAndWait();
                break;
        }
    }

    /**
     * Populates tableview and initializes the controller.
     * @param location Path used for the root object.
     * @param resources Used to localize root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Part Table View
        partTableView.setItems(Inventory.getAllParts());
        partColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Product Table View
        productTableView.setItems(Inventory.getAllProducts());
        productColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));



    }
}
