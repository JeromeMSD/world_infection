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
public class City {
    private final int sizeX;
    private final int sizeY;
    
    private Case [][] tab;
    
    
    public City(int sizeX, int sizeY) {
        int i,j;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        tab = new Case[sizeX][sizeY];
        
        for(i = 0; i < sizeX; i++){
            for(j = 0; j < sizeY; j++){
                this.tab[i][j] = new Case(i, j);
                System.out.println("tab["+i+"]["+j+"] initialised => "+ tab[i][j].toString());
            }
        }    
    }
    
    
    boolean isEmpty(int x, int y) {
        return tab[x][y].isEmpty();
    }
    
    int getSizeX() {
        return sizeX;
    }

    int getSizeY() {
        return sizeY;
    }

    Case getCase(int x, int y) {
        return this.tab[x][y];
    }

    public void print(){
        int i,j;
        for(i = 0; i < sizeX; i++){
            for(j = 0; j < sizeY; j++){
                System.out.println("tab["+i+"]["+j+"] initialised => "+ tab[i][j].toString());
            }
        } 
    }
}
