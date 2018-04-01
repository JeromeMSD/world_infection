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
    private List<DoctorPerson> dList = new ArrayList<>();
    private List<DeadPerson> mList = new ArrayList<>();
    private List<Person> globalList = new ArrayList<>();
    private int cpt = 0, deletedDeads = 0;
    private boolean running = false;
    private MainWindowController mwc;
    
    public Game(MainWindowController mwc) {
        this.mwc = mwc;
        
        this.mwc.cleanDisplay();
        this.city = new City(20, 20);
        generatePeople(5,1,1);
        this.mwc.display(city);
    }

    public Game(City city) {
        this.mwc.cleanDisplay();
        this.city = city;
        generatePeople(50,1,1);
        this.mwc.display(city);
    }
    
    public Game(MainWindowController mwc ,int nbH,int nbI,int nbD) {
        this.mwc = mwc;
        this.city = new City(20,20);
        generatePeople(nbH,nbI,nbD);
        this.mwc.cleanDisplay();
        this.mwc.display(city);
        this.mwc.setCpt(cpt, hList.size(), iList.size(), dList.size(), mList.size(), deletedDeads);
    }
    
    
    @Override
    public void run(){
        this.running = true;
        
        while(!hList.isEmpty() && !(iList.isEmpty() && mList.isEmpty()) ){
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
                    mwc.setCpt(cpt, hList.size(), iList.size(), dList.size(), mList.size(), deletedDeads);
                    mwc.cleanDisplay();
                    mwc.display(city);
                }
            });
            System.out.println("Tour numero "+ cpt +" terminé ! ");
        }
        
        if(hList.isEmpty()){
            Platform.runLater(() -> {
                mwc.humanityWin(false);
            });
            
            System.out.println("Infected killed everyone !");
 
        }else if(iList.isEmpty()){
            Platform.runLater(() -> {
                mwc.humanityWin(true);
            });
            System.out.println("Humanity survived to infection !");
        }else{
            System.out.println("Game stopped by user !");
        }
        
    }

    private void move() {
        for(Person p : globalList){
            p.move(city);
        }
    }
    
    private void rules(){
        
        rulesForHealthy();
        
        rulesForDoctor();
        
        rulesForInfected();
        
    }
    
    private void rulesForHealthy(){
        
        ArrayList<HealthyPerson> hL = new ArrayList<>();
        
        for(HealthyPerson h : hList){
            int x = h.curCase.x;
            int y = h.curCase.y;
            int infection = 0;
            
            for(int i = -1; i < 2; i++){
                for(int j = -1; j < 2; j++){
                    if( ((x+i > -1) && (x+i < city.getSizeX())) && ((y+j > -1) && (y+j < city.getSizeX())) && !city.getCase(x+i, y+j).isEmpty()){
                        if(city.getCase(x+i,y+j).getPerson().getClass().equals(InfectedPerson.class)){
                            infection = infection + 1;
                        }

                        if(city.getCase(x+i,y+j).getPerson().getClass().equals(DeadPerson.class)){
                            infection = infection + 3;
                        }
                    }
                }
            }
            /*
            if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x-1, y-1).isEmpty()){
                if(city.getCase(x-1, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x-1, y-1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
           
            if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y > -1) && (y < city.getSizeX())) && !city.getCase(x-1, y).isEmpty()){
                if(city.getCase(x-1, y).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x-1, y).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x > -1) && (x < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x, y-1).isEmpty()){
                if(city.getCase(x, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x, y-1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x-1, y+1).isEmpty()){
                if(city.getCase(x-1, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x-1, y+1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y > -1) && (y < city.getSizeX())) && !city.getCase(x+1, y).isEmpty()){
                if(city.getCase(x+1, y).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x+1, y).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x > -1) && (x < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x, y+1).isEmpty()){
                if(city.getCase(x, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x, y+1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x+1, y+1).isEmpty()){
                if(city.getCase(x+1, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x+1, y+1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x+1, y-1).isEmpty()){
                if(city.getCase(x+1, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x+1, y-1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            */
            
            
            if(infection >= 3){
                // TRANSFORMER HEALTHY EN INFECTED !
                hL.add(h);
                
                InfectedPerson inf = new InfectedPerson(h);
                iList.add(inf);
                globalList.add(inf);
                city.getCase(inf.curCase).setPerson(inf);
            }           
        }
       
        // Supprimer healthyPerson de la liste, ajouter infectedPerson dans la liste !
        hList.removeAll(hL);
        globalList.removeAll(hL);
        
    }
    
    private void rulesForDoctor(){
        
        ArrayList<DoctorPerson> dL = new ArrayList<>();
        ArrayList<InfectedPerson> iL = new ArrayList<>();
        
        for(DoctorPerson d : dList){
            int x = d.curCase.x;
            int y = d.curCase.y;
            int infection =0;
            
            for(int i = -1; i < 2; i++){
                for(int j=-1; j < 2; j++){
                    if( ((x+i > -1) && (x+i < city.getSizeX())) && ((y+j > -1) && (y+j < city.getSizeX())) && !city.getCase(x+i, y+j).isEmpty()){
                        if(city.getCase(x+i,y+j).getPerson().getClass().equals(InfectedPerson.class)){
                            infection ++;
                        }

                        if(city.getCase(x+i,y+j).getPerson().getClass().equals(DeadPerson.class)){
                            infection = infection + 2;
                            mList.remove( (DeadPerson) city.getCase(x+i, y+j).getPerson());
                            deletedDeads++;
                            city.getCase(x+i,y+i).setPerson(null);
                        }
                    }
                }
            }
            
            /*
            if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x-1, y-1).isEmpty()){
                if(city.getCase(x-1, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x-1, y-1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
           
            if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y > -1) && (y < city.getSizeX())) && !city.getCase(x-1, y).isEmpty()){
                if(city.getCase(x-1, y).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x-1, y).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x > -1) && (x < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x, y-1).isEmpty()){
                if(city.getCase(x, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x, y-1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x-1, y+1).isEmpty()){
                if(city.getCase(x-1, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x-1, y+1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y > -1) && (y < city.getSizeX())) && !city.getCase(x+1, y).isEmpty()){
                if(city.getCase(x+1, y).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x+1, y).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x > -1) && (x < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x, y+1).isEmpty()){
                if(city.getCase(x, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x, y+1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x+1, y+1).isEmpty()){
                if(city.getCase(x+1, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x+1, y+1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            
            if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x+1, y-1).isEmpty()){
                if(city.getCase(x+1, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                    infection ++;
                }
                
                if(city.getCase(x+1, y-1).getPerson().getClass().equals(DeadPerson.class)){
                    infection = infection+3;
                }
            }
            */
            
            if(infection>=5){
                // DOCTOR CONDAMNED !
                if (d.getDeadCounter() < 10){
                    //ALREADY CONDAMN
                    d.setDeadCounter(d.getDeadCounter()-3);
                }
                
                d.infect();
                
            }else{
                //Soigne infectés en contact direct
                if( ((x-1 > -1) && (x-1 < city.getSizeX())) && ((y > -1) && (y < city.getSizeX())) && !city.getCase(x-1, y).isEmpty()){
                    if(city.getCase(x-1, y).getPerson().getClass().equals(InfectedPerson.class)){
                        // TRANSFORMER INFECTED EN HEALTHY !
                        InfectedPerson inf = (InfectedPerson) city.getCase(x-1, y).getPerson();
                        iL.add(inf);

                        HealthyPerson pers = new HealthyPerson(inf);
                        hList.add(pers);
                        globalList.add(pers);
                        city.getCase(inf.curCase).setPerson(pers);
                        // Supprimer InfectedPerson de la liste, ajouter HealthyPerson dans la liste !
                    }
                }
                
                if( ((x > -1) && (x < city.getSizeX())) && ((y-1 > -1) && (y-1 < city.getSizeX())) && !city.getCase(x, y-1).isEmpty()){    
                    if(city.getCase(x, y-1).getPerson().getClass().equals(InfectedPerson.class)){
                        // TRANSFORMER INFECTED EN HEALTHY !
                        InfectedPerson inf = (InfectedPerson) city.getCase(x, y-1).getPerson();
                        iL.add(inf);

                        HealthyPerson pers = new HealthyPerson(inf);
                        hList.add(pers);
                        globalList.add(pers);
                        city.getCase(inf.curCase).setPerson(pers);
                        // Supprimer InfectedPerson de la liste, ajouter HealthyPerson dans la liste !
                    }
                }
                
                if( ((x+1 > -1) && (x+1 < city.getSizeX())) && ((y > -1) && (y < city.getSizeX())) && !city.getCase(x+1, y).isEmpty()){
                    if(city.getCase(x+1, y).getPerson().getClass().equals(InfectedPerson.class)){
                        // TRANSFORMER INFECTED EN HEALTHY !
                        InfectedPerson inf = (InfectedPerson) city.getCase(x+1, y).getPerson();
                        iL.add(inf);

                        HealthyPerson pers = new HealthyPerson(inf);
                        hList.add(pers);
                        globalList.add(pers);
                        city.getCase(inf.curCase).setPerson(pers);
                        // Supprimer InfectedPerson de la liste, ajouter HealthyPerson dans la liste !
                    }
                }
                
                if( ((x > -1) && (x < city.getSizeX())) && ((y+1 > -1) && (y+1 < city.getSizeX())) && !city.getCase(x, y+1).isEmpty()){    
                    if(city.getCase(x, y+1).getPerson().getClass().equals(InfectedPerson.class)){
                        // TRANSFORMER INFECTED EN HEALTHY !
                        InfectedPerson inf = (InfectedPerson) city.getCase(x, y+1).getPerson();
                        iL.add(inf);

                        HealthyPerson pers = new HealthyPerson(inf);
                        hList.add(pers);
                        globalList.add(pers);
                        city.getCase(inf.curCase).setPerson(pers);
                        // Supprimer InfectedPerson de la liste, ajouter HealthyPerson dans la liste !
                    }  
                }
                
            }
        
            if(d.getDeadCounter() <= 0){
                //SUPPRIMER DOCTOR DE LA LISTE
                dL.add(d);
                
                DeadPerson dead = new DeadPerson(d);
                mList.add(dead);
                city.getCase(d.curCase).setPerson(dead);
            }
        }
        dList.removeAll(dL);
        globalList.removeAll(dL);
        iList.removeAll(iL);
        globalList.removeAll(iL);
        
    }
    
    private void rulesForInfected(){
        ArrayList<InfectedPerson> iL = new ArrayList<>();
        
        for(InfectedPerson i : iList){
            if(i.getMouvementLeft() <= 0){
                iL.add(i);

                DeadPerson deadPers = new DeadPerson(i);
                mList.add(deadPers);
                city.getCase(i.curCase).setPerson(deadPers);
                //globalList.add(deadPers);
            }
        }

        iList.removeAll(iL);
        globalList.removeAll(iL);
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

    private void generatePeople(int nbH, int nbI, int nbM) {
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
        
        for(int i = 0; i < nbM; i++){
            DoctorPerson doc = new DoctorPerson();
            
            do{
                x = mt.nextInt(city.getSizeX());
                y = mt.nextInt(city.getSizeY());
            }while(!city.isEmpty(x,y));
            
            doc.curCase = city.getCase(x, y);
            city.getCase(x, y).setPerson(doc);
            
            globalList.add(doc);
            dList.add(doc);
        }
        
    }
    
    
    

    public void stop() {
        this.running = false;
    }
    
    public void resume(){
        this.running = true;
    }
    
    private boolean isRunning() {
        return this.running;
    }


}
