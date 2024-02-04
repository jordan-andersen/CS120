package main;

/*
Objectives for Today:

By the end of today, you will:
  * Identify how a GUI application's entrypoint differs from a console application.
  * Describe how Java code can be organized into 'packages'
  * Understand how Java 'modules' allow us to further encapsulate our packages to make them private.
  * Practice running our very first JavaFX GUI application.
 */

/*
Vocabulary for the Day

Graphical User Interface - A graphical user interface (or GUI [GOO-ee]) is a visual representation of a program that
a user can interact with using a keyboard, mouse, or other input devices.

Package - In Java, a package is any folder that lives inside the source code's root folder. Classes written in the same
package are automatically imported to one another, but classes from other packages require explicit imports.

Module - In Java, a module is a group of packages that we've given a name. Modules allow us to make 'encapsulate' code,
hiding how it works from any other code outside the module. To declare a module, we add a module-info.java file as a
sibling to the root package of our module.

 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;

public class HelloGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL first_scene = HelloGUI.class.getResource("HelloGUI.fxml");


        FXMLLoader loaded_files = new FXMLLoader();
        loaded_files.setLocation(first_scene);
        AnchorPane root_control = loaded_files.load();


        Scene scene = new Scene(root_control);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
