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
    
    public InfectedPerson(HealthyPerson h){
        super(1,3,1.0,0.4);
        this.curCase = h.curCase;
    }

    @Override
    public void move(City city) {
        int x,y,newX,newY;
        int unit = mt.nextInt(this.maxMove);

        if(unit == 0)
            unit = this.minMove;
        
        if (mouvementLeft > 0){
        
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
        
        mouvementLeft--;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public int getMouvementLeft(){
        return this.mouvementLeft;
    }
    
    @Override
    public String toString(){
        return "Infected person";
    }
    
}
