/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world_infection;

/**
 *
 * @author jeromem
 */
public class Case {
    public int x;
    public int y;
    private Boolean exit;
    private Person person;

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
        if(person == null)
            return true;
        return false;
    }

    void setPerson(Person p) {
        this.person = p;
    }
    
    
}
