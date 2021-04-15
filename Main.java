package Main;

import Resource.InHouse;
import Resource.Inventory;
import Resource.Outsourced;
import Resource.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The Inventory Management System program is an application for managing an inventory of parts and products.
 *
 * A future version of the program would be to add a function that enables
 * you to view duplicate or multiple products and their corresponding parts.
 * (ie. Bulk inventory compartment).
 *
 * @author Diego Medina
 */

public class Main extends Application {


    /**
     * The start method creates and loads the FXML stage and initial scene.
     *
     * @param primaryStage the primary panel.
     * @throws Exception from FXMLLoader.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Main/MainPanel.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 820, 325));
        primaryStage.show();
    }

    /**
     * the main entry point of the program application.
     * the method initiates sample data for the application and launches.
     * @param args Sample.
     */
    public static void main(String[] args) {

        //Sample Parts.
        int partId = Inventory.getFreshPartId();
        InHouse Brakes = new InHouse(partId,"Brakes",12.99,15,1,20,111);
        partId = Inventory.getFreshPartId();
        InHouse Tire = new InHouse(partId,"Tire",14.99,15,1,20,112);
        partId = Inventory.getFreshPartId();
        Outsourced Rim = new Outsourced(partId,"Rim",56.99,15,1,20,"Super Bikes");
        partId = Inventory.getFreshPartId();
        Outsourced Seat = new Outsourced(partId,"Seat",10.98,12,1,20,"RaceMax");
        Inventory.addPart(Brakes);
        Inventory.addPart(Tire);
        Inventory.addPart(Rim);
        Inventory.addPart(Seat);

        //Sample Product.
        int productId = Inventory.getFreshProductId();
        Product bike = new Product(productId,"Giant Bike",299.99,5,1,20);
        productId = Inventory.getFreshProductId();
        bike.addRelatedPart(Brakes);
        bike.addRelatedPart(Tire);
        bike.addRelatedPart(Rim);
        Product unicycle = new Product(productId,"Unicycle",156.89,6,1,30);
        unicycle.addRelatedPart(Tire);
        unicycle.addRelatedPart(Seat);
        Inventory.addProduct(bike);
        Inventory.addProduct(unicycle);

        launch(args);

    }
}
