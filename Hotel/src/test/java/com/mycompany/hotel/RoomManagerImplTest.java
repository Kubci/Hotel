/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotel;

import java.sql.SQLException;
import static junit.framework.Assert.assertEquals; // this is deprecated
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kubo
 */
public class RoomManagerImplTest {

    public RoomManagerImplTest() {
    }

    RoomManager manager;
    Room room;
    Room room2;
    Reservation reservation;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        manager = new RoomManagerImpl();
        room = new Room(2, 3);
        room2 = new Room(3, 4);
        reservation = new Reservation();
        
        manager.storeRoom(room);
        manager.storeRoom(room2);
    }

    /**
     * Test of createRoom method, of class RoomManagerImpl.
     */
    @Test
    public void testStoreRoom() {
        assertEquals(room, manager.findRoom(1));
    }
    
    /*@Test (expected = IllegalArgumentException.class)
    public void testStoreRoomForIllegalArgument(){
            manager.storeRoom(null);
            manager.storeRoom(room);
    }*/

    /**
     * Test of deleteRoom method, of class RoomManagerImpl.
     */
    @Test
    public void testDeleteRoom() {
        manager.deleteRoom(room);

        assertEquals(null, manager.findRoom(1));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteRoomForIllegalArgument() {
        manager.deleteRoom(null);
        manager.deleteRoom(room);
        
    }

    /**
     * Test of editRoom method, of class RoomManagerImpl.
     */
    @Test
    public void testEditRoom() {

        manager.editRoom(room, 5);

        assertEquals(5, room.getNumberOfBeds());
    }

    
    @Test (expected = IllegalArgumentException.class)
    public void editRoomForIllegalArgumentException() {
            manager.editRoom(room, 0);
            manager.editRoom(room, -5);
            manager.editRoom(room, 100);       
    }

    @Test
    public void testFindRoom() {
        assertEquals(room, manager.findRoom(1));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindRoomForIllegalArgumentException(){
        manager.findRoom(-1);
        manager.findRoom(1540);
        manager.findRoom(0);
    }

}
