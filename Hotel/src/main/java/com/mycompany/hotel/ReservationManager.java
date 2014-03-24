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
public interface ReservationManager {

    void storeReservation(Reservation reservation);

    void deleteReservation(Reservation reservation);

    void editReservation(Reservation reservation);

    Reservation findReservation(int id);
    
}
