package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.List;

public class FirstScene {
    public AnchorPane Pane;
    @FXML
    private Label HeadingText;
    @FXML
    private ColorPicker BackgroundColor;
    @FXML
    private Label FirstText;
    @FXML
    private ColorPicker TextColor;
    @FXML
    private Label SecondText;


    public void pickedBackground(ActionEvent actionEvent) {
        Color color = BackgroundColor.getValue();
        Background background = new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
        Pane.setBackground(background);
    }


    public void pickedForeground(ActionEvent actionEvent) {
        List<Label> labels = List.of(HeadingText, FirstText, SecondText);
        for ( Label text : labels ) {
            text.setTextFill(TextColor.getValue());
        }
    }
}
