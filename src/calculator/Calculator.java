
package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;


public class Calculator extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Calculator_View.fxml"));
        
        Scene scene = new Scene(root);
             
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("HidaSoft Calculator v.0.3.");
        Image icon = new Image(getClass().getResourceAsStream("/resource/calc_icon.jpg")) ;
        stage.getIcons().add(icon);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
