package com.mycompany.hotel;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        
        logger.info("Start program. Logs are imported just for demonstration.");
        Room room = new Room(5, 2);
        RoomManager manager = null;

        try {
            
            manager = new RoomManagerImpl();
            Room room2 = manager.storeRoom(room);
            manager.editRoom(room2, 5);
            manager.findRoom(room.getId());
            manager.deleteRoom(room2);
        } catch (ClassNotFoundException ex) {
            logger.warn("Class not found", ex);
        } catch (SQLException ex) {
            logger.warn("SQL ... ", ex);
        }
        
    }
}
