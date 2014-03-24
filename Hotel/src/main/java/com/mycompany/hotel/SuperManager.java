package com.mycompany.hotel;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Kubo
 */
public interface SuperManager {

    Reservation chceckIn(Integer id, String responsiblePerson, List<String> otherPersons, String account, 
            Date DateOfCheckIn, int duration, int NOBed);

    void checkOut(int id);

    void assignRoomToReservation(Reservation reservation);

    Reservation findReservation(String reponsiblePerson);
    
}
