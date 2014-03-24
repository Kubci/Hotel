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

    Room storeRoom(Room room) throws SQLException;
    
}
