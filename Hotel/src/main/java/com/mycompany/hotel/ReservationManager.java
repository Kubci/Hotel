package com.mycompany.hotel;

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
