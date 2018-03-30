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
public class DeadPerson extends Person{

    public DeadPerson() {
        super(0, 0, 0, 0);
    }

    public DeadPerson(InfectedPerson i){
        super(0, 0, 0, 0);
        this.curCase = i.curCase;
    }
    
    public DeadPerson(DoctorPerson d){
        super(0, 0, 0, 0);
        this.curCase = d.curCase;
    }
    
    @Override
    public void move(City city) {
        System.out.println("Les morts ne bouge pas..");
    }
    
}
