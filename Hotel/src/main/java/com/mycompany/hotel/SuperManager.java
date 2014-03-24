/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hotel;

import com.mycompany.hotel.Reservation;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kubo
 */
public interface SuperManager {

    Reservation chceckIn(Integer id, String responsiblePerson, List<String> otherPersons, String account, Date DateOfCheckIn, int duration, int NOBed);

    void checkOut(int id);

    void assignRoomToReservation(Reservation reservation);

    Reservation findReservation(String reponsiblePerson);
    
}
