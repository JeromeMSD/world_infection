/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author jeromem
 */
public class World_infection extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        fl.setController(new MainWindowController());
        Parent root = fl.load();
                
        Scene scene = new Scene(root, 800, 800);

        stage.setOnCloseRequest((WindowEvent t) -> {
            System.exit(0);
        });
        
        stage.setTitle("World Infection");
        stage.setScene(scene);
        stage.show();
    }
    
}
