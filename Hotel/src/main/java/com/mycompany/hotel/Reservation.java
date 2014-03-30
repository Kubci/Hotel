package com.mycompany.hotel;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author Kubo
 */
public class Reservation {
    
    private static final Logger logger = LoggerFactory.getLogger(Reservation.class);
    
    private Integer id;
    private String responsiblePerson;
    private List<String> otherPersons;
    private String account;
    private Date DateOfCheckIn;
    private int duration;
    private int NOBed;
    private int idRoom;

    public Reservation(String responsiblePerson, List<String> otherPersons, String account,
            Date DateOfCheckIn, int duration, int NOBed) {
        
        logger.info("Creating reservation {} ", id);
        
     //   this.id = id;
        this.responsiblePerson = responsiblePerson;
        this.otherPersons = otherPersons;
        this.account = account;
        this.DateOfCheckIn = DateOfCheckIn;
        this.duration = duration;
        this.NOBed = NOBed;
    }

    Reservation() {

    }

    public Integer getId() {
        return id;
    }
    
    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public List<String> getOtherPersons() {
        return otherPersons;
    }

    public void setOtherPersons(List<String> otherPersons) {
        this.otherPersons = otherPersons;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getDateOfCheckIn() {
        return DateOfCheckIn;
    }

    public void setDateOfCheckIn(Date DateOfCheckIn) {
        this.DateOfCheckIn = DateOfCheckIn;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNOBed() {
        return NOBed;
    }

    public void setNOBed(int NOBed) {
        this.NOBed = NOBed;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", responsiblePerson=" + responsiblePerson + ", otherPersons=" + otherPersons + ", account=" + account + ", DateOfCheckIn=" + DateOfCheckIn + ", duration=" + duration + ", NOBed=" + NOBed + ", idRoom=" + idRoom + '}';
    }
    
    
}   
