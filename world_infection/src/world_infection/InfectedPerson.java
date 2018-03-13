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
public class InfectedPerson extends Person{

    private int mouvementLeft = 20;
    
    public InfectedPerson() {
        super(1,3,1.0,0.4);
    }

    @Override
    public void move(City city) {
        int unit=mt.nextInt(this.maxMove)+1;
        int x,y;
        do{
            x = mt.next(unit);
            y = mt.next(unit);

            if(mt.nextBoolean()){
                x = curCase.x-x;
            }
            if(mt.nextBoolean()){
                y = curCase.y-y;
            }
            
        }while(city.isEmpty(x,y) && ( x < city.getSizeX() && x >= 0) && ( y < city.getSizeY() && y >= 0 ));
        
        Case c  = city.getCase(x,y);
        c.setPerson(this);
        this.curCase = c;
        
        mouvementLeft--;
    }
    
    
    
}
