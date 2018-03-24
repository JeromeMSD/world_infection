/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author jeromem
 */
public class Case {
    public int x;
    public int y;
    private Boolean exit;
    private Person person = null;

    public Case(int x, int y, Boolean exit, Person person) {
        this.x = x;
        this.y = y;
        this.exit = exit;
        this.person = person;
    }
    
    public Case(int x, int y, Boolean exit) {
        this.x = x;
        this.y = y;
        this.exit = exit;
    }

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.exit = false;
    }

    public Person getPerson(){
        return person;
    }
    
    public boolean isExit(){
        return exit;
    }

    public Boolean isEmpty() {
        return person == null;
    }

    public void setPerson(Person p) {
        this.person = p;
    }
    
    public GraphicsContext displayOn(GraphicsContext gc){
        if (person.getClass().equals(HealthyPerson.class))
            gc.setFill(Color.BLUE);
        else if(person.getClass().equals(InfectedPerson.class)){
            gc.setFill(Color.RED);
        }
        gc.fillRect(x, y, 2, 2);
        
        return gc;
    }
    
    @Override
    public String toString(){
        String str;
        if (person == null) {
            str = "NULL";
        }else {
            str = person.toString();
        }
        return "["+ x +"]["+ y +"] -> Sortie:"+ exit.toString() +" | "+ str;
    }
}
