module com.example.friendslib {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.rmi;
    requires java.sql;
    requires jbcrypt;

    opens com.example.friendslib to javafx.fxml;
    exports com.example.friendslib;
}