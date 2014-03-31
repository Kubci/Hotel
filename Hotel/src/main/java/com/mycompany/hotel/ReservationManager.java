package com.mycompany.hotel;

import java.sql.SQLException;

/**
 *
 * @author Kubo
 */
public interface ReservationManager {

    Reservation storeReservation(Reservation reservation) throws SQLException;

    void deleteReservation(Reservation reservation);

    void editReservation(Reservation reservation);

    Reservation findReservation(int id);
    
}
