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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author jeromem
 */
public class MainWindowController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label counter;
    @FXML
    private Button play;
    @FXML
    private Spinner infectedPersonneNumber;
    @FXML
    private Spinner heathlyPersonneNumber;
    
    private Game g;
    private Thread gameThread;
    
    @FXML
    public void reset(){
        g = new Game(this);
        gameThread = new Thread(g);
    }
    
    @FXML
    public void play(){
        if(play.getText().equals("Play")){
            gameThread.start();
            play.setText("Stop");
        }else{
            play.setText("Play");
            g.stop();
        }
    }
    
        
    public void display(City city){
        Canvas canvas = new Canvas(city.getSizeX()*100, city.getSizeY()*100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int i,j;
        
        
        for(i = 0; i < city.getSizeX(); i++){
            for(j = 0; j < city.getSizeY(); j++){
                if(!city.isEmpty(i, j))
                    city.displayOn(gc);
            }
        }
        
        pane.getChildren().add(canvas);
    }
        
    
    public void cleanDisplay(){
       while(!pane.getChildren().isEmpty())
            pane.getChildren().remove(0);
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setCpt(int cpt) {
        counter.setText(cpt+"");
    }
    
}
