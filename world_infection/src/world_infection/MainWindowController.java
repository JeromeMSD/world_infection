/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private Spinner doctorNumber;
    
    @FXML
    private Label remainingH;
    @FXML
    private Label remainingI;
    @FXML
    private Label remainingD;
    @FXML
    private Label remainingM;
    @FXML
    private Label cleanedM;
    
    private Game g;
    private Thread gameThread;
    
    @FXML
    public void reset(){
        g = new Game(this,(Integer) heathlyPersonneNumber.getValue(), (Integer) infectedPersonneNumber.getValue(), (Integer) doctorNumber.getValue());
        gameThread = new Thread(g);
    }
    
    @FXML
    public void play(){
        try{
            if(play.getText().equals("Play")){
                gameThread.start();
                play.setText("Stop");
            }else if(play.getText().equals("Resume")){
                play.setText("Stop");
                g.resume();
                gameThread = new Thread(g);
                gameThread.start();
            }else{
                play.setText("Resume");
                g.stop();
            }
        }catch(NullPointerException e){
            Stage stage = new Stage();
        stage.setTitle("Connection");
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        
        Text scenetitle = new Text("Action impossible !");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setId("title");
        
        
        Label question = new Label("Pour lancer la simlation vous devez tout d'abord initialiser un Jeu ! ");
        question.setWrapText(true);
        grid.add(question, 0, 1,3,3);

        Button btn = new Button("Ok");
        grid.add(btn, 3, 5);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                    stage.close();
            }
        });

        
        Scene scene = new Scene(grid, 400, 200);
        scene.getStylesheets().add(MainWindowController.class.getResource("/css/main.css").toExternalForm());
        stage.setScene(scene);
        
        stage.showAndWait();
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

    
    public void humanityWin(Boolean b){
        Stage stage = new Stage();
        stage.setTitle("Game Over");
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Label scenetitle;
        
        if(b){
            scenetitle = new Label("Humanity survived !");
        }else{
            scenetitle = new Label("Infected killed everyone !");
        }
        scenetitle.setWrapText(true);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 3, 3);
        scenetitle.setId("title");
        
        Button btn = new Button("Ok");
        grid.add(btn, 2, 4);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                    stage.close();
            }
        });

        
        Scene scene = new Scene(grid, 400, 200);
        scene.getStylesheets().add(MainWindowController.class.getResource("/css/main.css").toExternalForm());
        stage.setScene(scene);
        
        stage.showAndWait();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setCpt(int roundCpt, int remainingHealthy, int remainingInfected, int remainingDoctor, int remainingDead, int cleanedDead) {
        counter.setText(roundCpt+"");
        remainingH.setText(remainingHealthy+"");
        remainingI.setText(remainingInfected+"");
        remainingD.setText(remainingDoctor+"");
        remainingM.setText(remainingDead+"");
        cleanedM.setText(cleanedDead+"");
        
    }
    
}
