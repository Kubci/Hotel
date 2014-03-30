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


    @Before
    public void setUp() throws SQLException, ClassNotFoundException {

        manager = new RoomManagerImpl();
        room = new Room(2, 3);
        room2 = new Room(3, 4);
        
        manager.storeRoom(room);
        manager.storeRoom(room2);
    }

    /**
     * Test of createRoom method, of class RoomManagerImpl.
     */
    @Test
    public void testStoreRoom() {
       Room newRoom = new Room(2,2);
        try {
            manager.storeRoom(newRoom);
            assertEquals(newRoom.getId(), manager.findRoom(newRoom.getId()).getId());
            assertEquals(newRoom.getFloor(), manager.findRoom(newRoom.getId()).getFloor());
            assertEquals(newRoom.getNumberOfBeds(), manager.findRoom(newRoom.getId()).getNumberOfBeds());
        } catch (SQLException ex) {
            fail();
        }
       
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

        assertEquals(null, manager.findRoom(room.getId()));

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

        

        assertEquals(5, manager.editRoom(room, 5).getNumberOfBeds());
    }

    
    @Test (expected = IllegalArgumentException.class)
    public void editRoomForIllegalArgumentException() {
            manager.editRoom(room, 0);
            manager.editRoom(room, -5);
            manager.editRoom(room, 100);       
    }

    @Test
    public void testFindRoom() {
        assertEquals(room.getId(), manager.findRoom(room.getId()).getId());
        assertEquals(room.getFloor(), manager.findRoom(room.getId()).getFloor());
        assertEquals(room.getNumberOfBeds(), manager.findRoom(room.getId()).getNumberOfBeds());
    
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindRoomForIllegalArgumentException(){
        manager.findRoom(-1);
        manager.findRoom(0);
    }

}
