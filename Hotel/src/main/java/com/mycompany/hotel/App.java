package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        logger.info("Start program");
        Room room = new Room(5, 2);
        RoomManager manager = null;

        try {

            manager = new RoomManagerImpl();
            Room room2 = manager.storeRoom(room);
            manager.editRoom(room2, 5);
            manager.findRoom(room.getId());
            //manager.deleteRoom(room2);
        } catch (ClassNotFoundException ex) {
            logger.warn("Class not found", ex);
        } catch (SQLException ex) {
            logger.warn("SQL ... ", ex);
        }

        Reservation reserv = new Reservation("Franc", "48468486", new Date(50000000), 5, 2);
        ReservationManager res = new ReservationManagerImpl();

        reserv.setId(res.storeReservation(reserv).getId());
        res.editReservation(reserv);
        res.findReservation(reserv.getId());
        //res.deleteReservation(reserv);

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);

    }
}
