module annafudiana.comiczoneapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens annafudiana.comiczoneapp to javafx.fxml;
    exports annafudiana.comiczoneapp;
}