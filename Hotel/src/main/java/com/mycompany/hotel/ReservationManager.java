package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kubo
 */
public interface ReservationManager{
    Logger logger = LoggerFactory.getLogger(ReservationManagerImpl.class);

    void deleteReservation(Reservation reservation);

    void editReservation(Reservation reservation);

    ArrayList<Reservation> findAllReservation();

    Reservation findReservation(int id);

    Reservation storeReservation(Reservation reservation) throws SQLException;
}
