/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hotel;

/**
 *
 * @author Kubo
 */
public class Room {
    
    private Integer id = null;
    private int floor;
    private int numberOfBeds;

    public Room() {
    }

    public Room(int id, int floor, int numberOfBeds) {
        this.id = id;
        this.floor = floor;
        this.numberOfBeds = numberOfBeds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", floor=" + floor + ", numberOfBeds=" + numberOfBeds + '}';
    }
    
    
}
