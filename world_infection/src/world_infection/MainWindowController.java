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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author jeromem
 */
public class MainWindowController implements Initializable {

    @FXML
    private Pane pane;
    
    
    @FXML
    public void reset(){
        System.err.println("Reset pressed");
        final Canvas c = new Canvas(500,500);
        GraphicsContext gc = c.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(75,75,100,100);
        
        pane.getChildren().add(c);
    }
    
    @FXML
    public void play(){
        Game g = new Game(pane);
    }
        
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
