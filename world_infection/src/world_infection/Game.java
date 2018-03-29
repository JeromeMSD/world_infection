/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author jeromem
 */
public class Game implements Runnable{
    
    private City city;
    private MersenneTwister mt = new MersenneTwister();
    private List<HealthyPerson> hList = new ArrayList<>();
    private List<InfectedPerson> iList = new ArrayList<>();
    private List<Person> globalList = new ArrayList<>();
    private int cpt = 0;
    private boolean running = false;
    private MainWindowController mwc;
    
    public Game(MainWindowController mwc) {
        this.mwc = mwc;
        
        this.mwc.cleanDisplay();
        this.city = new City(20, 20);
        generatePeople(5,1);
        this.mwc.display(city);
    }

    public Game(City city) {
        this.mwc.cleanDisplay();
        this.city = city;
        generatePeople(50,1);
        this.mwc.display(city);
    }
    
    public Game(MainWindowController mwc ,int nbH,int nbI) {
        this.mwc = mwc;
        this.city = new City(nbH,nbH);
        generatePeople(nbH,nbI);
        this.mwc.cleanDisplay();
        this.mwc.display(city);
    }
    
    
    public void run(){
        this.running = true;
        while(!iList.isEmpty() || !hList.isEmpty()){
            if(!this.isRunning())
                break;
            move();
            rules();
            displayText();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            
            cpt++;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mwc.setCpt(cpt);
                    mwc.cleanDisplay();
                    mwc.display(city);
                }
            });
            System.out.println("Tour numero "+ cpt +" terminé ! ");
        }
        
        if(iList.isEmpty()){
            System.out.println("Humanity survived to infection !");
        }else if(hList.isEmpty()){
            System.out.println("Infected killed everyone !");
        }else{
            System.out.println("Game stopped by user !");
        }
        
    }

    private void move() {
        for(Person p : globalList){
            p.move(city);
        }
    }
    
    // LES REGLES C4EST LA MICHEL !
    private void rules(){
        for(HealthyPerson i : hList){
            int x = i.curCase.x;
            int y = i.curCase.y;
            int infection =0;
            /*
            if(city.getCase(x-1, y-1).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x-1, y).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x, y-1).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x-1, y+1).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x+1, y).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x, y+1).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x+1, y+1).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            if(city.getCase(x+1, y-1).getPerson().getClass().equals(InfectedPerson)){
                infection ++;
            }
            
            if(infection>=3){
                // TRANSFORMER HEALTHY EN INFECTED !
                infectedPerson inf = New InfectedPersonne(i);
                // Supprimer healthyPerson de la liste, ajouter infectedPerson dans la liste !
            }
             
        }
        /*
        for(InfectedPerson i : List){
            
        }
        */
    }
    
    private void displayText(){
        System.out.println(city.displayText());
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
            }while(!city.isEmpty(x,y));
            
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
            }while(!city.isEmpty(x,y));
            
            in.curCase = city.getCase(x, y);
            city.getCase(x, y).setPerson(in);
            
            globalList.add(in);
            iList.add(in);
        }
        
    }

    public void stop() {
        this.running = false;
    }

    private boolean isRunning() {
        return this.running;
    }


}
