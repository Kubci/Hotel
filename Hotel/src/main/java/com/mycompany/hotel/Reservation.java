package com.mycompany.hotel;

import java.sql.Date;
import java.util.Objects;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author Kubo
 */
public class Reservation {
    
    private Integer id;
    private String responsiblePerson;
    private String account;
    private Date dateOfCheckIn;
    private int duration;
    private int nOBed;
    private int idRoom;

    public Reservation() {};
    
    public Reservation(String responsiblePerson, String account,
            Date DateOfCheckIn, int duration, int NOBed) {
        
        this.responsiblePerson = responsiblePerson;
        this.account = account;
        this.dateOfCheckIn = DateOfCheckIn;
        this.duration = duration;
        this.nOBed = NOBed;
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

    public int getNOBed() {
        return nOBed;
    }

    public void setNOBed(int nOBed) {
        this.nOBed = nOBed;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getDateOfCheckIn() {
        return dateOfCheckIn;
    }

    public void setDateOfCheckIn(Date DateOfCheckIn) {
        this.dateOfCheckIn = DateOfCheckIn;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", responsiblePerson=" + responsiblePerson + ", account=" + account + ", DateOfCheckIn=" + dateOfCheckIn + ", duration=" + duration + ", NOBed=" + nOBed + ", idRoom=" + idRoom + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}   
