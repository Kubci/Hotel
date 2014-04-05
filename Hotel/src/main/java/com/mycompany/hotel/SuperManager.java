/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hotel;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Kubo
 */
public interface SuperManager {

    Reservation chceckIn(String responsiblePerson, String account, Date DateOfCheckIn, int duration, int NOBed) throws SQLException;

    void checkOut(Integer reservId);

    Reservation findReservation(String responsiblePerson);
    
}
