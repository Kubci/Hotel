package com.mycompany.hotel;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        logger.info("Start program");
        /*
        SuperManager sm = new SuperManagerImpl();
        sm.chceckIn("dzaba", "123456", new Date(50000), 7, 4);
        
        ReservationManager rm = new ReservationManagerImpl();
        rm.storeReservation(new Reservation("sdfghj", "123", new Date(123456), 5, 2));
       */
        RoomManager rom = new RoomManagerImpl();
       System.out.println(rom.findRoom(2));
       rom.editRoom(rom.findRoom(2), new Integer(12365));
       System.out.println(rom.findRoom(2));
       
    }
}
