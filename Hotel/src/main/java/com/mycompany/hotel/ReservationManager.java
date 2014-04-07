/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.Set;
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

    Set<Reservation> findAllReservation();

    Reservation findReservation(int id);

    Reservation storeReservation(Reservation reservation) throws SQLException;
    
}
