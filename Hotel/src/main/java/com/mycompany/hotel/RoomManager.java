/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.hotel;

import java.sql.SQLException;

/**
 *
 * @author Kubo
 */
public interface RoomManager {

    void deleteRoom(Room room);

    Room editRoom(Room room, int numbeOfBeds);

    Room findRoom(int id);

    void storeRoom(Room room) throws SQLException;
    
}
