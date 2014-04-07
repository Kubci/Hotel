package com.mycompany.hotel;

import static com.mycompany.hotel.RoomManager.logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author Kubo
 */
public class RoomManagerImpl implements RoomManager{

    private final String url = "jdbc:mysql://localhost:3306/pv168";
    private final String driver = "com.mysql.jdbc.Driver";
    private DataSource ds = null;

    public RoomManagerImpl() throws ClassNotFoundException, SQLException {
        PoolProperties p = new PoolProperties();
        p.setUrl(url);
        p.setDriverClassName(driver);
        p.setUsername("root");
        p.setPassword("dzames");
        p.setLogAbandoned(true);
        ds = new DataSource();
        ds.setPoolProperties(p);

    }

    @Override
    public Room storeRoom(Room room) throws SQLException {

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

        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO Rooms (floor, numberOfBeds, idRes) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
                st.setInt(1, room.getFloor());
                st.setInt(2, room.getNumberOfBeds());
                st.setInt(3, 0);
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows inserted when trying to insert room" + room);
                }

                try (ResultSet keyRS = st.getGeneratedKeys()) {
                    room.setId(getKey(keyRS, room));
                }
                conn.commit();
            }
            return room;
        } catch (SQLException ex) {
            logger.warn("store room sql ex", ex);
            throw new ServiceFailureException("Error when inserting room " + room + ex);
        }
    }

    private int getKey(ResultSet keyRS, Room room) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert room " + room
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            int result = keyRS.getInt(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert room " + room
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert room " + room
                    + " - no key found");
        }
    }
    
    
    @Override
    public void generateRooms() throws SQLException {

        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int  n = rand.nextInt(5) + 1;
                storeRoom(new Room(i+1, n));
            }
        }
    }

    @Override
    public void deleteRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room might not have been initilized");
        }
        if (room.getId() == null) {
            throw new IllegalArgumentException("room is not stored");
        }

        int id = room.getId();
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement("DELETE FROM Rooms WHERE id = ?")) {
                st.setInt(1, id);

                int executeUpdate = st.executeUpdate();

                if (executeUpdate == 0) {
                    throw new ServiceFailureException("room to delet is not in database");
                }
                if (executeUpdate != 1) {
                    throw new ServiceFailureException("something is wrong in database, multiple rows with same id");
                }
                conn.commit();
            }
        } catch (SQLException ex) {
            logger.warn("delete room sql ex", ex);
            throw new ServiceFailureException(
                    "Error when deleting room with id " + id, ex);
        }
    }

    @Override
    public Room editRoom(Room room, Integer idRes) {
        if (room.getId() == null) {
            throw new IllegalArgumentException("room is not stored");
        }
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (idRes <= 0) {
            throw new IllegalArgumentException("wrong argument numberOfBeds");
        }
        int id = room.getId();

        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement("UPDATE Rooms SET idRes = ? " + " WHERE id = ?")) {
                
                st.setInt(1, idRes);
                st.setInt(2, room.getId());

                int executeUpdate = st.executeUpdate();
                conn.commit();
                if (executeUpdate == 0) {
                    throw new ServiceFailureException("no room to edit");
                }
                if (executeUpdate > 1) {
                    throw new ServiceFailureException("something is wrong in database, multiple rows with same id");
                }

                Room newRoom = new Room(room.getFloor(), idRes);
                newRoom.setId(id);
                newRoom.setIdRes(idRes);
               
                return newRoom;
                
            }
        } catch (SQLException ex) {
            logger.warn("edit room sql ex", ex);
            throw new ServiceFailureException("Error when deleting room with id " + id, ex);
        }

    }

    @Override
    public Room findRoom(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("wrong id");
        }

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,floor,numberOfBeds,idRes FROM Rooms WHERE id = ?")) {
                st.setInt(1, id);
                try (ResultSet rs = st.executeQuery();) {
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
                }
            }
        } catch (SQLException ex) {
            logger.warn("find room sql ex", ex);
            throw new ServiceFailureException(
                    "Error when retrieving room with id " + id, ex);
        }
    }
    
    @Override
    public Set<Room> findAllRooms() {
        Set<Room> allR = new HashSet<>();
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,floor,numberOfBeds,idRes FROM Rooms")) {
                try (ResultSet rs = st.executeQuery();) {
                    while (rs.next()) {
                         allR.add(resultSetToRoom(rs));
                    }
                }
            }
            return allR;
        } catch (SQLException ex) {
            logger.warn("find room sql ex", ex);
            throw new ServiceFailureException(
                    "Error when retrieving room with id " + allR, ex);
        }
    }

    private Room resultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setFloor(rs.getInt("floor"));
        room.setNumberOfBeds(rs.getInt("numberOfBeds"));
        room.setIdRes(rs.getInt("idRes"));
        return room;
    }
}
