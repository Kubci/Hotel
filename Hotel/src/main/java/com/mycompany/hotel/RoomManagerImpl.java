/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotel;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kubo
 */
public class RoomManagerImpl implements RoomManager {

    public static final Logger LOGGER = Logger.getLogger(RoomManagerImpl.class.getName());

    String url = "jdbc:mysql://localhost:3306/pv168?useUnicode=true";

    public RoomManagerImpl() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

    }

    @Override
    public void storeRoom(Room room) throws SQLException {

        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getId() != null) {
            throw new IllegalArgumentException("room id is already set");
        }
        if (room.getFloor() < 0) {
            throw new IllegalArgumentException("wrong floor");
        }
        if (room.getNumberOfBeds() < 0) {
            throw new IllegalArgumentException("negative no. of beds");
        }

        PreparedStatement st = null;
        try {
            Connection conn = DriverManager.getConnection(url, "root", "dzames");
            st = conn.prepareStatement(
                    "INSERT INTO Rooms (floor, numberOfBeds) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, room.getFloor());
            st.setInt(2, room.getNumberOfBeds());

            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows "
                        + "inserted when trying to insert room" + room);
            }

            ResultSet keyRS = st.getGeneratedKeys();
            room.setId(getKey(keyRS, room));

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting room " + room + ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private int getKey(ResultSet keyRS, Room room) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert grave " + room
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            int result = keyRS.getInt(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert grave " + room
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert grave " + room
                    + " - no key found");
        }
    }

    @Override
    public void deleteRoom(Room room) {
        if (room.getId() == null) {
            throw new IllegalArgumentException("room is not stored");
        }
        int id = room.getId();

        PreparedStatement st = null;
        try {
            Connection conn = DriverManager.getConnection(url, "root", "dzames");
            st = conn.prepareStatement("DELETE FROM Rooms WHERE id = ?");
            st.setInt(1, id);
            int executeUpdate = st.executeUpdate();
            if (executeUpdate == 0) {
                throw new ServiceFailureException("room to delet is not in database");
            }
            if (executeUpdate != 0) {
                throw new ServiceFailureException("something is wrong in database, multiple rows with same id");
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when deleting grave with id " + id, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public Room editRoom(Room room, int numbeOfBeds) {
        if (room.getId() == null) {
            throw new IllegalArgumentException("room is not stored");
        }
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (numbeOfBeds <= 0) {
            throw new IllegalArgumentException("wrong argument numberOfBeds");
        }
        int id = room.getId();

        PreparedStatement st = null;
        try {
            Connection conn = DriverManager.getConnection(url, "root", "dzames");
            st = conn.prepareStatement("UPDATE Rooms SET numberOfBeds = ? " + " WHERE id = ?");
            st.setInt(1, numbeOfBeds);
            st.setInt(2, room.getId());
            
            st.executeUpdate();
           
            return new Room();
        } catch (SQLException ex) {
            throw new ServiceFailureException(
                    "Error when deleting grave with id " + id, ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

        @Override
        public Room findRoom
        (int id
        
            
            
            ) {
        PreparedStatement st = null;
            try {
                Connection conn = DriverManager.getConnection(url, "root", "dzames");
                st = conn.prepareStatement(
                        "SELECT id,floor,numberOfBeds FROM Rooms WHERE id = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {
                    Room room = resultSetToRoom(rs);

                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                + "(source id: " + id + ", found " + room + " and " + resultSetToRoom(rs));
                    }

                    return room;
                } else {
                    return null;
                }

            } catch (SQLException ex) {
                throw new ServiceFailureException(
                        "Error when retrieving grave with id " + id, ex);
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    private Room resultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setFloor(rs.getInt("floor"));
        room.setNumberOfBeds(rs.getInt("numberOfBeds"));
        return room;
    }

}
