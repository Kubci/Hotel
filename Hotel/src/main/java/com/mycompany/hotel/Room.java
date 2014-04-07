package com.mycompany.hotel;

/**
 *
 * @author Kubo
 */
public class Room {
    
    private Integer id = null;
    private int floor;
    private int numberOfBeds;
    private Integer idRes = null;

        public Room() {
    }
    
    public Integer getIdRes() {
        return idRes;
    }

    public void setIdRes(Integer idRes) {
        this.idRes = idRes;
    }



    public Room(int floor, int numberOfBeds) {
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
        return "Room{" + "id=" + id + ", floor=" + floor + ", numberOfBeds=" + numberOfBeds + ", idRes=" + idRes + '}';
    }
}
