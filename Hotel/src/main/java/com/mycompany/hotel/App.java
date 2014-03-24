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
        Room room = new Room(10, 5, 2);
        RoomManager manager = null;
       /* try {
            manager = new RoomManagerImpl();
            manager.storeRoom(room);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        try {
            manager = new RoomManagerImpl();
            manager.editRoom(room, 7);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
