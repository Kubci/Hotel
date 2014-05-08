/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hotel;

import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author Kubo
 */
public interface SuperManager {

    Reservation chceckIn(String responsiblePerson, String account, Date dateOfCheckIn, int duration, int numBeds) throws SQLException;

    void checkOut(Integer reservId);

    Reservation findReservation(Integer id);
    
}
