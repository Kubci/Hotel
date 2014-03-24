package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Room room = new Room(5, 2);
        RoomManager manager = null;
       
        
        try {
            manager = new RoomManagerImpl();
            Room room2 = manager.storeRoom(room);
            manager.editRoom(room2, 5);
            manager.findRoom(room.getId());
            manager.deleteRoom(room2);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
