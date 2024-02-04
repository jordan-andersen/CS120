module Main.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens gui;
    opens data;
    exports gui;
    exports data;
}