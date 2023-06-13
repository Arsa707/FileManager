module com.filemanager.filemanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.filemanager.filemanager to javafx.fxml;
    exports com.filemanager.filemanager;
}