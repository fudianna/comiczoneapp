package annafudiana.comiczoneapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Rental extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Rentalstage) throws IOException {
        FXMLLoader RentalfxmlLoader = new FXMLLoader(Rental.class.getResource("RentalView.fxml"));
        Scene Rentalscene = new Scene(RentalfxmlLoader.load(), 870, 811);
        Rentalstage.setTitle("Comic Zone");
        Rentalstage.setScene(Rentalscene);
        Rentalstage.show();
    }
}
