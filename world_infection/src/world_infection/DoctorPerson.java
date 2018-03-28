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
public class DoctorPerson extends Person{

    public DoctorPerson() {
        super(1, 6, 0.5, 1);
    }

    @Override
    public void move(City city) {
        int x,y,newX,newY;
        
        int unit = mt.nextInt(this.maxMove);
        
        if(unit == 0)
            unit = 1;
        
        do{
            x = mt.nextInt(unit);
            
            if(mt.nextBoolean()){
                newX = this.curCase.x-x;
            }else{
                newX = this.curCase.x+x;
            }
            
        }while(newX < 0 || newX > city.getSizeX()-1);
        
        do{
            y = mt.nextInt(unit);

            if(mt.nextBoolean()){
                newY = this.curCase.y-y;
            }else{
                newY = this.curCase.y+y;
            }
        }while(newY < 0 || newY > city.getSizeY()-1);
        
        
        Case c  = city.getCase(newX, newY);
        
        this.curCase.setPerson(null);
        
        c.setPerson(this);
        this.curCase = c;

    }   
    
    @Override
    public String toString(){
        return "Healthy person";
    }
    
    
}
