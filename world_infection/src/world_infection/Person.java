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
public abstract class Person {
    protected int minMove;
    protected int maxMove;
    protected double moveProbability;
    protected double exitCity;
    protected Case curCase;
    protected MersenneTwister mt = new MersenneTwister();

    public Person(int minMove, int maxMove, double moveProbability, double exitCity) {
        this.minMove = minMove;
        this.maxMove = maxMove;
        this.moveProbability = moveProbability;
        this.exitCity = exitCity;
    }

    public abstract void move(City city);
    
}
