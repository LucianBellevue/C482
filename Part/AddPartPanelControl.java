package Part;

import Resource.InHouse;
import Resource.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Resource.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Control class that provides logic for the add part panel of the program application.
 *
 * @author Diego Medina
 */
public class AddPartPanelControl implements Initializable {

    /**
     * Displays confirmation and loads the MainPanelController.
     * @param event Cancel button action.
     * @throws IOException from FXMLLoader.
     */
    @FXML void cancelButton(ActionEvent event) throws IOException {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Notice");
      alert.setContentText("Cancel changes and return to main screen?");
      Optional<ButtonType> outcome = alert.showAndWait();
      if (outcome.isPresent() && outcome.get() == ButtonType.OK) {
          mainScreenEvent(event);
      }
    }

    /**
     * Adds a new part to the inventory and also loads the MainPanel.
     * @param event Save Button.
     * @throws IOException from FXMLLoader
     */
    @FXML void saveAction(ActionEvent event) throws IOException {
        try{
            int id = 0;
            String name = namePartField.getText();
            Double price = Double.parseDouble(pricePartField.getText());
            int stock = Integer.parseInt(stockPartField.getText());
            int min = Integer.parseInt(minPartField.getText());
            int max = Integer.parseInt(maxPartField.getText());
            int machineId;
            String companyName;
            boolean addPartConfirm = false;
            if(name.isEmpty()) {alertStatus(5);}
            else {
                if (inHouseSwitch.isSelected()) {
                    try {
                        machineId = Integer.parseInt(machineCompPartField.getText());
                        InHouse newIHPart = new InHouse(id,name,price,stock,min,max,machineId);
                        newIHPart.setId(Inventory.getFreshPartId());
                        Inventory.addPart(newIHPart);
                        addPartConfirm = true;
                    } catch (Exception e) {alertStatus(2);}
                }
                if (outsourcedSwitch.isSelected()) {
                    companyName = machineCompPartField.getText();
                    Outsourced newOPart = new Outsourced(id,name,price,stock,min,max,companyName);
                    newOPart.setId(Inventory.getFreshPartId());
                    Inventory.addPart(newOPart);
                    addPartConfirm = true;
                }
                if(addPartConfirm) {mainScreenEvent(event);}
            }
        }catch(Exception e) {alertStatus(1);}
    }

    /**
     * Toggle switch for the radio button to inHouse form.
     */
    @FXML private RadioButton inHouseSwitch;

    /**
     * Toggle switch for the radio button to outsourced form.
     */
    @FXML private RadioButton outsourcedSwitch;

    /**
     * The part toggle data.
     */
    @FXML private ToggleGroup partToggle;

    /**
     * This is both the machine ID and company name text field.
     */
    @FXML private TextField machineCompPartField;

    /**
     * Min text field.
     */
    @FXML private TextField minPartField;

    /**
     * Max text field.
     */
    @FXML private TextField maxPartField;

    /**
     * Price text field.
     */
    @FXML private TextField pricePartField;

    /**
     * Inventory text field.
     */
    @FXML private TextField stockPartField;

    /**
     * Name text field.
     */
    @FXML private TextField namePartField;

    /**
     * ID text field.
     */
    @FXML private TextField idPartField;

    /**
     * the machine ID and company name label.
     */
    @FXML private Label machineCompLabel;

    /**
     * Switches machineCompPartField label to Machine ID.
     * @param event Switch to In House event.
     */
    @FXML void inHouseSwitchEvent(ActionEvent event) {
        machineCompLabel.setText("Machine ID");
    }

    /**
     * Switches machineCompPartField label to Company Name.
     * @param event Switch to Outsourced event.
     */
    @FXML void outsourcedSwitchEvent(ActionEvent event) {
        machineCompLabel.setText("Company Name");
    }

    /**
     * checks the minimum inventory value is greater than 0 and less than the maximum.
     * @param min the minimum.
     * @param max the maximum.
     * @return checking boolean.
     */
    private boolean realMinimum(int min, int max) {
        boolean checkMin = true;
        if (min <= 0 || min >= max) {
            checkMin = false;
            alertStatus(3);
        }
        return checkMin;
    }

    /**
     * checks the inventory level,
     * @param min the minimum value of inventory.
     * @param max the maximum value of inventory.
     * @param stock the inventory level.
     * @return checking boolean.
     */
    private boolean checkInventory(int min, int max, int stock) {
        boolean checkStock = true;
        if(stock<min||stock>max) {
            checkStock = false;
            alertStatus(4);
        }
        return checkStock;
    }

    /**
     * Shows alert status.
     * @param alertNumber the alert message.
     */
    private void alertStatus(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch(alertNumber) {
            case 1:
                alert.setTitle("ERROR");
                alert.setHeaderText("Action error adding part");
                alert.setContentText("Invalid value or Blank statement");
                alert.show();
                break;
            case 2:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Can only use numbers");
                alert.show();
                break;
            case 3:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Must be greater than 0 and less than the maximum");
                alert.show();
                break;
            case 4:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid Value");
                alert.setContentText("Inventory must be between the minimum and maximum level");
                alert.show();
                break;
            case 5:
                alert.setTitle("ERROR");
                alert.setHeaderText("Empty Value");
                alert.setContentText("Must include name.");
                alert.show();
                break;
        }
    }

    /**
     * Loads MainPanelController.
     * @param event used in save button and cancel button actions.
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
     * initializes the controller and sets the in house screen as default view.
     * @param location used for the path of object.
     * @param resources localizes root.
     */
    @Override public void initialize(URL location, ResourceBundle resources) {
        inHouseSwitch.setSelected(true);
    }
}
