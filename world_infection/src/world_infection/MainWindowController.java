/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author jeromem
 */
public class MainWindowController implements Initializable {

    @FXML
    private Canvas canvas;
    
    
    @FXML
    public void reset(){
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.rect(1, 1, 2, 2);
        g.setFill(Color.RED);
        //TEST EN COURS 
    }
    
    @FXML
    public void play(){
        Game g = new Game();
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
