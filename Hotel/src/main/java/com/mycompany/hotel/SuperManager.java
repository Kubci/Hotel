package com.mycompany.hotel;

import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author Kubo
 */
public interface SuperManager {

    Reservation chceckIn(String responsiblePerson, String account, Date DateOfCheckIn, int duration, int NOBed) throws SQLException;

    void checkOut(Integer reservId);

    Reservation findReservation(String responsiblePerson);    
    
     public Room findRoom(Integer id);
}
