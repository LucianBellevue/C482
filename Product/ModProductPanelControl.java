package Product;



import Main.MainPanelController;
import Resource.Inventory;
import Resource.Part;
import Resource.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Control for the ModProductPanel which provides logic for the screen.
 * @author Diego Medina
 */
public class ModProductPanelControl implements Initializable {

    /**
     * The list of parts related to the product.
     */
    private ObservableList<Part> relatedParts = FXCollections.observableArrayList();

    /**
     * The chosen product that was selected in the MainPanel.
     */
    Product chosenProduct;

    /**
     * checks the value of the minimum within max and 0.
     * @param min the minimum.
     * @param max the maximum.
     * @return return boolean for minimum.
     */
    private boolean checkMin(int min, int max) {
        boolean realMin = true;

        if(min <= 0 || min >= max) {
            realMin = false;
            alertStatus(3);
        }
        return realMin;
    }

    /**
     * checks the value of the inventory is between minimum and maximum parameters.
     * @param min the minimum value.
     * @param max the maximum value.
     * @param stock the inventory value.
     * @return boolean for inventory check value.
     */
    private boolean checkStock(int min, int max, int stock) {
        boolean realStock = true;

        if(stock <= min || stock >= max) {
            realStock = false;
            alertStatus(4);
        }
        return realStock;
    }

    /**
     * Shows Alert status for event types.
     * @param alertNum alert status type.
     */
    private void alertStatus(int alertNum) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertNum) {
            case 1:
                alert.setTitle("ERROR");
                alert.setHeaderText("Modifying Error");
                alert.setContentText("Empty or Invalid Value");
                alert.showAndWait();
                break;
            case 2:
                alertInfo.setTitle("Notice");
                alertInfo.setHeaderText("No part found");
                alertInfo.showAndWait();
                break;
            case 3:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid value");
                alert.setContentText("Minimum must be a number between 0 and Max number");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid value");
                alert.setContentText("Inventory value must be a number between min and max");
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
                alert.setContentText("Name field cannot be left empty.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Returns to MainPanel.
     * @param event reloads main panel event based method.
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
     * The view for all the parts.
     */
    @FXML private TableView<Part> partFieldView;

    /**
     * The id column for partFieldView.
     */
    @FXML private TableColumn<Part,Integer> idPartColumn;

    /**
     * The name column for partFieldView.
     */
    @FXML private TableColumn<Part,String> namePartColumn;

    /**
     * The inventory column for partFieldView.
     */
    @FXML private TableColumn<Part,Integer> stockPartColumn;

    /**
     *  The price column for partFieldColumn.
     */
    @FXML private TableColumn<Part,Double> pricePartColumn;

    /**
     * The search field for parts.
     */
    @FXML private TextField partSearchField;

    /**
     * Table view for all related parts to the product.
     */
    @FXML private TableView<Part> relatedPartsField;

    /**
     * The ID column for relatedPartsField.
     */
    @FXML private TableColumn<Part, Integer> idRelatedPartColumn;

    /**
     * The name column for relatedPartsField.
     */
    @FXML private TableColumn<Part,String> nameRelatedPartColumn;

    /**
     * The inventory column for relatedPartsField.
     */
    @FXML private  TableColumn<Part,Integer> stockRelatedPartColumn;

    /**
     * The price column for relatedPartsField.
     */
    @FXML private TableColumn<Part,Double> priceRelatedPartColumn;

    /**
     * The Id for product in TextField.
     */
    @FXML private TextField idProductField;

    /**
     * The name for product in TextField.
     */
    @FXML private TextField nameProductField;

    /**
     * The price for product in TextField.
     */
    @FXML private TextField priceProductField;

    /**
     * The inventory for product in TextField.
     */
    @FXML private TextField stockProductField;

    /**
     * The minimum for product in TextField.
     */
    @FXML private TextField minProductField;

    /**
     * The maximum for product in TextField.
     */
    @FXML private TextField maxProductField;

    /**
     * Adds the selected part to the related parts table. Alert message if no part is selected.
     * @param event Add button event.
     */
    @FXML void addButtonEvent(ActionEvent event) {
        Part chosenPart = partFieldView.getSelectionModel().getSelectedItem();
        if(chosenPart==null) {
            alertStatus(5);
        }else {
            relatedParts.add(chosenPart);
            relatedPartsField.setItems(relatedParts);
        }
    }

    /**
     * Removed selected part upon confirmation notice and displays an error notice if no part is selected.
     * @param event remove button event.
     */
    @FXML void removeButtonEvent(ActionEvent event) {
        Part chosenPart = relatedPartsField.getSelectionModel().getSelectedItem();
        if(chosenPart==null) {
            alertStatus(5);
        }else {
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Notice");
            alert.setContentText("Remove selected part?");
            Optional<ButtonType> outcome = alert.showAndWait();
            if(outcome.isPresent()&&outcome.get()==ButtonType.OK) {
                relatedParts.remove(chosenPart);
                relatedPartsField.setItems(relatedParts);
            }
        }
    }

    /**
     * Cancels modification and reloads MainPanel upon confirmation.
     * @param event cancel button event.
     * @throws IOException from FXMLLoader.
     */
    @FXML void cancelButtonEvent(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notice");
        alert.setContentText("Cancel changes and return to main screen?");
        Optional<ButtonType> outcome = alert.showAndWait();
        if(outcome.isPresent()&&outcome.get()==ButtonType.OK) {
            mainScreenEvent(event);
        }
    }

    /**
     * Saves selected product change in the Inventory and reloads MainPanel.
     * @param event save button event.
     * @throws IOException from FXMLLoader.
     */
    @FXML void saveButtonEvent(ActionEvent event) throws IOException {
        try{
            int id = chosenProduct.getId();
            String name = nameProductField.getText();
            Double price = Double.parseDouble(priceProductField.getText());
            int stock = Integer.parseInt(stockProductField.getText());
            int max = Integer.parseInt(maxProductField.getText());
            int min = Integer.parseInt(minProductField.getText());
            if(name.isEmpty()) {
                alertStatus(6);
            }else{
                if(checkMin(min,max) && checkStock(min,max,stock)) {
                    Product newProduct = new Product(id,name,price,stock,min,max);
                    for(Part part:relatedParts) {
                        newProduct.addRelatedPart(part);
                    }
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(chosenProduct);
                    mainScreenEvent(event);
                }
            }
        }catch (Exception e) {
            alertStatus(1);
        }
    }

    /**
     * Begins a search for parts based on value and returns found parts in table view.
     * @param event Search button event.
     */
    @FXML void searchButtonEvent(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String searchWord = partSearchField.getText();
        for(Part part:allParts) {
            if(String.valueOf(part.getId()).contains(searchWord) || part.getName().contains(searchWord)) {
                foundParts.add(part);
            }
        }
        partFieldView.setItems(foundParts);
        if(foundParts.size()==0) {
            alertStatus(1);
        }
    }

    /**
     * reloads the table view when the search field is empty.
     * @param keyEvent search field event.
     */
    @FXML void searchKey(KeyEvent keyEvent) {
        if(partSearchField.getText().isEmpty()) {
            partFieldView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Initializes controller and provides data for the table view with selected products from MainPanel.
     * @param location used for the path of the root.
     * @param resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenProduct = MainPanelController.getProductToMod();
        relatedParts = chosenProduct.getAllRelatedParts();
        idPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePartColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partFieldView.setItems(Inventory.getAllParts());

        idRelatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameRelatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockRelatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceRelatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        relatedPartsField.setItems(relatedParts);

        idProductField.setText(String.valueOf(chosenProduct.getId()));
        nameProductField.setText(chosenProduct.getName());
        stockProductField.setText(String.valueOf(chosenProduct.getStock()));
        priceProductField.setText(String.valueOf(chosenProduct.getPrice()));
        minProductField.setText(String.valueOf(chosenProduct.getMin()));
        maxProductField.setText(String.valueOf(chosenProduct.getMax()));
    }
}
