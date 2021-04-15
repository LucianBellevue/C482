package Part;

import Main.MainPanelController;
import Resource.InHouse;
import Resource.Inventory;
import Resource.Outsourced;
import Resource.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Control class for the ModPartPanel.
 * @author Diego Medina
 */
public class ModPartPanelControl implements Initializable {

    /**
     * shows alert status for events.
     * @param alertNum alert status type.
     */
    private void alertStatus(int alertNum) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch(alertNum) {
            case 1:
                alert.setTitle("ERROR");
                alert.setHeaderText("There was an error modifying part");
                alert.setContentText("Invalid or empty value");
                alert.showAndWait();
            case 2:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid value");
                alert.setContentText("Field can only contain numbers");
                alert.showAndWait();
            case 3:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid value");
                alert.setContentText("Minimum value must be between 0 and maximum");
                alert.showAndWait();
            case 4:
                alert.setTitle("ERROR");
                alert.setHeaderText("Invalid value");
                alert.setContentText("Invalid or empty value");
                alert.showAndWait();
                break;
        }
    }

    /**
     * reloads MainPanel.
     * @param event for the return to screen events in controller.
     * @throws IOException from FXMLLoader
     */
    private void mainScreenEvent(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../Main/MainPanel.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Check the minimum value is valid.
     * @param min the minimum value.
     * @param max the maximum value.
     * @return boolean for minimum value.
     */
    private boolean checkMin(int min, int max) {
        boolean realMin = true;
        if(min<=0||min>=max) {
            realMin = false;
            alertStatus(3);
        }
        return realMin;
    }
    /**
     * Checks inventory level is valid.
     * @param min the minimum level.
     * @param max the max level.
     * @param stock the inventory level.
     * @return boolean for inventory level.
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
     * returns to MainPanel upon display confirmation.
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
     * Saves selected part into the inventory and reloads the MainPanel.
     * Alert Status for invalid input.
     * @param event save button event.
     * @throws IOException from FXMLLoader.
     */
    @FXML void saveButtonEvent(ActionEvent event) throws IOException {
        try {
            int id = chosenPart.getId();
            String name = namePartField.getText();
            Double price = Double.parseDouble(pricePartField.getText());
            int stock = Integer.parseInt(stockPartField.getText());
            int min = Integer.parseInt(minPartField.getText());
            int max = Integer.parseInt(maxPartField.getText());
            int machineId;
            String companyName;
            boolean addPartConfirm = false;
            if(checkMin(min,max)&&checkStock(min,max,stock)) {
                if(inHouseSwitch.isSelected()) {
                    try{
                        machineId = Integer.parseInt(idPartField.getText());
                        InHouse newInHousePart = new InHouse(id,name,price,stock,min,max,machineId);
                        Inventory.addPart(newInHousePart);
                        addPartConfirm = true;
                    }catch (Exception e) {
                        alertStatus(2);
                    }
                }
                if(outsourcedSwitch.isSelected()) {
                    companyName = idPartField.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id,name,price,stock,min,max,companyName);
                    Inventory.addPart(newOutsourcedPart);
                    addPartConfirm = true;
                }
                if(addPartConfirm) {
                    Inventory.deletePart(chosenPart);
                    mainScreenEvent(event);
                }
            }
        }catch (Exception e) {
            alertStatus(1);
        }
    }

    /**
     * sets the machineCompLabel to Machine ID.
     * @param event inHouse radio button event.
     */
    @FXML void inHouseSwitchEvent(ActionEvent event) {
        machineCompLabel.setText("Machine ID");
    }

    /**
     * sets the machineCompLabel to Company Name.
     * @param event outsourced radio button event.
     */
    @FXML void outsourcedSwitchEvent(ActionEvent event) {
        machineCompLabel.setText("Company Name");
    }

    /**
     * the part chosen in MainPanel.
     */
    private Part chosenPart;

    /**
     * the machine ID and company name label.
     */
    @FXML private Label machineCompLabel;

    /**
     * the in house switch.
     */
    @FXML private RadioButton inHouseSwitch;

    /**
     * the outsourced switch.
     */
    @FXML private RadioButton outsourcedSwitch;

    /**
     * The toggle for the radio buttons.
     */
    @FXML private ToggleGroup partGroup;

    /**
     *  The id part field.
     */
    @FXML private TextField idPartField;

    /**
     * the name part field.
     */
    @FXML private TextField namePartField;

    /**
     * the price part field.
     */
    @FXML private TextField pricePartField;

    /**
     * the inventory part field.
     */
    @FXML private TextField stockPartField;

    /**
     * the maximum part field.
     */
    @FXML private TextField maxPartField;

    /**
     * the minimum part field.
     */
    @FXML private TextField minPartField;

    /**
     * the machine ID and company name field.
     */
    @FXML private TextField machineCompField;

    /**
     * Initializes the ModPartPanelControl and fills data for view.
     * @param location the path used for root object.
     * @param resources resources to localize root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenPart = MainPanelController.getPartToMod();
        if(chosenPart instanceof InHouse) {
            inHouseSwitch.setSelected(true);
            machineCompLabel.setText("Machine ID");
            machineCompField.setText(String.valueOf(((InHouse)chosenPart).getMachineId()));
        }
        if(chosenPart instanceof Outsourced) {
            outsourcedSwitch.setSelected(true);
            machineCompLabel.setText("Company Name");
            machineCompField.setText(((Outsourced) chosenPart).getCompanyName());
        }
        idPartField.setText(String.valueOf(chosenPart.getId()));
        namePartField.setText(chosenPart.getName());
        stockPartField.setText(String.valueOf(chosenPart.getStock()));
        pricePartField.setText(String.valueOf(chosenPart.getPrice()));
        minPartField.setText(String.valueOf(chosenPart.getMin()));
        maxPartField.setText(String.valueOf(chosenPart.getMax()));

    }
}
