package Product;

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
 * Control that provides logic for the Add product panel of the program.
 * @author Diego Medina
 */
public class AddProductPanelControl implements Initializable {

    /**
     * Returns to MainPanel.
     * @param event method loads the main screen.
     * @throws IOException from FXMLLoader.
     */
    private void mainScreenEvent(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../Main/MainPanel.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *Shows confirmation to cancel, returns to MainPanel if confirmed.
     * @param event Cancel Button.
     * @throws IOException from FXMLLoader.
     */
    @FXML void cancelButtonEvent(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notice");
        alert.setContentText("Cancel and return to Main Screen?");
        Optional<ButtonType> outcome = alert.showAndWait();
        if(outcome.isPresent() && outcome.get() == ButtonType.OK) {mainScreenEvent(event);}
    }

    /**
     * Shows an alert message for actions.
     * @param alertNum the alert status type.
     */
    private void alertStatus(int alertNum) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertNotice = new Alert(Alert.AlertType.INFORMATION);
        switch(alertNum) {
            case 1:
                alert.setTitle("ERROR");
                alert.setHeaderText("There was an error adding the product");
                alert.setContentText("Invalid or no value.");
                alert.showAndWait();
                break;
            case 2:
                alertNotice.setTitle("Notice");
                alertNotice.setHeaderText("Part not found");
                alertNotice.showAndWait();
                break;
            case 3:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Minimum needs to be a number between 0 and the maximum.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Inventory must be within the parameters of min and max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("ERROR");
                alert.setHeaderText("No part selected");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("ERROR");
                alert.setHeaderText("Empty Value");
                alert.setContentText("Name field cannot be empty");
                alert.showAndWait();
                break;
        }
    }

    /**
     * The column for the related ID part in the table.
     */
    @FXML private TableColumn<Part,Integer> relatedPartColumnId;

    /**
     * The column for the related Name part in the table.
     */
    @FXML private TableColumn<Part,String> relatedPartColumnName;

    /**
     * The column for the related Stock part in the table.
     */
    @FXML private TableColumn<Part,Integer> relatedPartColumnStock;

    /**
     * The column for the related Price part in the table.
     */
    @FXML private TableColumn<Part,Double> relatedPartColumnPrice;

    /**
     * The part table view.
     */
    @FXML private TableView<Part> partFieldView;

    /**
     * The related parts view.
     */
    @FXML private TableView<Part> relatedPartFieldView;

    /**
     *  The column for the ID part in the table.
     */
    @FXML private TableColumn<Part,Integer> partColumnId;

    /**
     * The column for the Name part in the table.
     */
    @FXML private TableColumn<Part,String> partColumnName;

    /**
     * The column for the Stock part in the table.
     */
    @FXML private TableColumn<Part,Integer> partColumnStock;

    /**
     * The column for the Price part in the table.
     */
    @FXML private TableColumn<Part,Double> partColumnPrice;

    /**
     * Search box for part.
     */
    @FXML private TextField partSearchField;

    /**
     * TextField ID for products.
     */
    @FXML private TextField idProductField;

    /**
     * TextField name for products.
     */
    @FXML private TextField nameProductField;

    /**
     * TextField inventory for products.
     */
    @FXML private TextField stockProductField;

    /**
     * TextField price for products.
     */
    @FXML private TextField priceProductField;

    /**
     * TextField minimum for products.
     */
    @FXML private TextField minProductField;

    /**
     * TextField maximum for products.
     */
    @FXML private TextField maxProductField;

    /**
     * The list containing the parts related to the product.
     */
    private ObservableList<Part> relatedPart = FXCollections.observableArrayList();

    /**
     * Starts search based on keyword value of parts in the TextField.
     * Reloads with found parts.
     * @param event Search event for the TextField in parts.
     */
    @FXML void partSearchEvent(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String searchWord = partSearchField.getText();
        for(Part part : allParts) {
            if(String.valueOf(part.getId()).contains(searchWord)||part.getName().contains(searchWord)) {
                foundParts.add(part);
            }
        }
        partFieldView.setItems(foundParts);
        if(foundParts.size()==0) {alertStatus(1);}
    }

    /**
     * reloads the Table View for parts to show all parts available when the field is empty.
     * @param event Key Pressed event.
     */
    @FXML void partSearchKey(KeyEvent event) {
        if(partSearchField.getText().isEmpty()) {
            partFieldView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Adds selected part to the related parts table and shows error if no part is selected.
     * @param event Add button.
     */
    @FXML void addButtonEvent(ActionEvent event) {
        Part chosenPart = partFieldView.getSelectionModel().getSelectedItem();
        if(chosenPart == null) {alertStatus(5);}
        else {
            relatedPart.add(chosenPart);
            relatedPartFieldView.setItems(relatedPart);
        }
    }

    /**
     * Checks the minimum value. Notice alert if minimum value is invalid.
     * @param min the minimum value.
     * @param max the maximum value.
     * @return boolean for minimum.
     */
    private boolean checkMin(int min,int max) {
        boolean realMin = true;
        if (min<=0||min>=max) {
            realMin = false;
            alertStatus(3);
        }
        return realMin;
    }

    /**
     * Checks the stock value. Notice alert if stock value is invalid.
     * @param min the minimum value.
     * @param max the maximum value.
     * @param stock the inventory value.
     * @return boolean for inventory value.
     */
    private boolean checkStock(int min, int max, int stock) {
        boolean realStock = true;
        if(stock<min||stock>max) {
            realStock = false;
            alertStatus(4);
        }
        return realStock;
    }

    /**
     * Saves new product to inventory and reloads the MainPanelControl.
     * Notice for empty or invalid values.
     * @param event Save Button.
     * @throws IOException from the FXMLLoader.
     */
    @FXML void saveButtonEvent(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = nameProductField.getText();
            Double price = Double.parseDouble(priceProductField.getText());
            int stock = Integer.parseInt(stockProductField.getText());
            int min = Integer.parseInt(minProductField.getText());
            int max = Integer.parseInt(maxProductField.getText());
            if(name.isEmpty()) {alertStatus(6);}
            else {
                if(checkMin(min,max) && checkStock(min,max,stock)) {
                    Product newProduct = new Product(id,name,price,stock,min,max);
                    for(Part part: relatedPart) {
                        newProduct.addRelatedPart(part);
                    }
                    newProduct.setId(Inventory.getFreshProductId());
                    Inventory.addProduct(newProduct);
                    mainScreenEvent(event);
                }
            }
        } catch (Exception e) {alertStatus(1);}
    }

    /**
     * Removes selected part, displays confirm notice before removal.
     * @param event Remove button event.
     */
    @FXML void removeButtonEvent(ActionEvent event) {
        Part chosenPart = relatedPartFieldView.getSelectionModel().getSelectedItem();
        if(chosenPart == null) {alertStatus(5);}
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Notice");
            alert.setContentText("Remove selected part?");
            Optional<ButtonType> outcome = alert.showAndWait();
            if(outcome.isPresent() && outcome.get()==ButtonType.OK) {
                relatedPart.remove(chosenPart);
                relatedPartFieldView.setItems(relatedPart);
            }
        }
    }

    /**
     * Initializes the controller and table views.
     * @param location used for the path of the root object.
     * @param resources source used to localize root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partFieldView.setItems(Inventory.getAllParts());
        relatedPartColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        relatedPartColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        relatedPartColumnStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        relatedPartColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
