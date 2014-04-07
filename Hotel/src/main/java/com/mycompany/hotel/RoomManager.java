package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kubo
 */
public interface RoomManager {
    Logger logger = LoggerFactory.getLogger(ReservationManagerImpl.class);

    void deleteRoom(Room room);

    Room editRoom(Room room, Integer resId);

    Set<Room> findAllRooms();

    Room findRoom(int id);

    void generateRooms() throws SQLException;

    Room storeRoom(Room room) throws SQLException;
}
