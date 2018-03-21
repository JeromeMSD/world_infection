/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 *
 * @author jeromem
 */
public class Game {
    
    private City city;
    private MersenneTwister mt = new MersenneTwister();
    private Pane pane;
    private List<HealthyPerson> hList = new ArrayList<>();
    private List<InfectedPerson> iList = new ArrayList<>();
    private List<Person> globalList = new ArrayList<>();
    private int cpt = 0;
    
    
    public Game(Pane pane) {
        this.pane = pane;
        this.city = new City(16, 16);
        generatePeople(20,1);
    }

    public Game(City city) {
        this.city = city;
        generatePeople(50,1);
    }
    
    public Game(City city,int nbH,int nbI) {
        this.city = city;
        generatePeople(nbH,nbI);
    }
    
    public void play(){
        while(iList.isEmpty() || hList.isEmpty()){
            move();
            rules();
            display();
            cpt++;
            System.out.println("Tour numero "+ cpt +" terminé ! ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println("Failed to sleep");
            }
        }
        
        if(iList.isEmpty()){
            System.out.println("Humanity survived to infection !");
        }else{
            System.out.println("Infected killed everyone !");
        } 
            
    }

    private void move() {
        for(Person p : globalList)
            p.move(city);
    }

    private void rules(){
        for(InfectedPerson i : iList){
            int x = i.curCase.x;
            int y = i.curCase.y;
            
            //if(!city.getCase(x-1, y-1).isEmpty() && city.getCase(x-1, y-1).getPerson().getClass().equals(HealthyPerson))
              
        }
    }
    
    private void display(){
        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int i,j;
        
        for(i = 0; i < city.getSizeX(); i++){
            for(j = 0; j < city.getSizeY(); j++){
                city.getCase(i, j).displayOn(gc);
            }
        }
        
        pane.getChildren().add(canvas);
    }
    
    public int getRemainingHealthy(){
        return this.hList.size();
    } 
    
    public int getRemainingInfected(){
        return this.iList.size();
    } 


    private void generatePeople(int nbH, int nbI) {
        int x,y;
        for(int i = 0; i < nbH; i++){
            HealthyPerson h = new HealthyPerson();
            do{
                x = mt.nextInt(city.getSizeX());
                y = mt.nextInt(city.getSizeY());
            }while(city.isEmpty(x,y));
            
            h.curCase = city.getCase(x, y);
            city.getCase(x, y).setPerson(h);
            
            globalList.add(h);
            hList.add(h);
        }
        
        for(int i = 0; i < nbI; i++){
            InfectedPerson in = new InfectedPerson();
            
            do{
                x = mt.nextInt(city.getSizeX());
                y = mt.nextInt(city.getSizeY());
            }while(city.isEmpty(x,y));
            
            in.curCase = city.getCase(x, y);
            city.getCase(x, y).setPerson(in);
            
            globalList.add(in);
            iList.add(in);
        }
        
    }
    
}
