package com.mycompany.hotel;

import ch.qos.logback.classic.pattern.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author Kubo
 */
public class ReservationManagerImpl implements ReservationManager {

    public static final Logger logger = LoggerFactory.getLogger(ReservationManagerImpl.class);

    private final String url = "jdbc:mysql://localhost:3306/pv168";
    private final String driver = "com.mysql.jdbc.Driver";
    private DataSource ds = null;

    public ReservationManagerImpl() throws ClassNotFoundException, SQLException {
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
    public Reservation storeReservation(Reservation reservation) throws SQLException {

        if (reservation == null) {
            throw new IllegalArgumentException("reservation is null");
        }
        if (reservation.getId() != null) {
            throw new IllegalArgumentException("reservation id is already set");
        }
        if (reservation.getResponsiblePerson() == null) {
            throw new IllegalArgumentException("responsible person is null");
        }
        if (reservation.getNOBed() < 0) {
            throw new IllegalArgumentException("negative no. of beds");
        }

        if (reservation.getIdRoom() < 0) {
            throw new IllegalArgumentException("negative ID of room");
        }

        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO Reservations (responsiblePerson, account, dateOfCheckIn, duration, nOBed, idRoom) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);) {
                st.setBytes(1, reservation.getResponsiblePerson().getBytes());
                st.setString(2, reservation.getAccount());
                st.setDate(3, reservation.getDateOfCheckIn());
                st.setInt(4, reservation.getDuration());
                st.setInt(5, reservation.getNOBed());
                st.setInt(6, reservation.getIdRoom());
                int addedRows = st.executeUpdate();
                if (addedRows != 1) {
                    throw new ServiceFailureException("Internal Error: More rows inserted when trying to insert reservation" + reservation);
                }

                try (ResultSet keyRS = st.getGeneratedKeys()) {
                    reservation.setId(getKey(keyRS, reservation));
                }
                conn.commit();
            }
            return reservation;
        } catch (SQLException ex) {
            logger.warn("store reservation sql ex", ex);
            throw new ServiceFailureException("Error when inserting reservation " + reservation + ex);
        }
    }

    private int getKey(ResultSet keyRS, Reservation reservation) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert reservation " + reservation
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            int result = keyRS.getInt(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert reservation " + reservation
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert reservation " + reservation
                    + " - no key found");
        }
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("reservation might not have been initilized");
        }
        if (reservation.getId() == null) {
            throw new IllegalArgumentException("reservation is not stored");
        }

        int id = reservation.getId();
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement("DELETE FROM Reservations WHERE id = ?")) {
                st.setInt(1, id);

                int executeUpdate = st.executeUpdate();

                if (executeUpdate == 0) {
                    throw new ServiceFailureException("reservation to delet is not in database");
                }
                if (executeUpdate != 1) {
                    throw new ServiceFailureException("something is wrong in database, multiple rows with same id");
                }
                conn.commit();
            }
        } catch (SQLException ex) {
            logger.warn("delete reservation sql ex", ex);
            throw new ServiceFailureException(
                    "Error when deleting reservation with id " + id, ex);
        }
    }

    @Override
    public void editReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("reservation is null");
        }
        if (reservation.getId() == null) {
            throw new IllegalArgumentException("reservation is not stored");
        }
        

        int id = reservation.getId();

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE Reservations SET  responsiblePerson = ?,"
                    + "account = ?, dateOfCheckIn = ?, duration = ?, nOBed = ?, idRoom = ?" + " WHERE id = ?")) {
                conn.setAutoCommit(false);
                st.setBytes(1, reservation.getResponsiblePerson().getBytes());
                st.setString(2, reservation.getAccount());
                st.setDate(3, reservation.getDateOfCheckIn());
                st.setInt(4, reservation.getDuration());
                st.setInt(5, reservation.getNOBed());
                st.setInt(6, reservation.getIdRoom());
                st.setInt(7, reservation.getId());

                int executeUpdate = st.executeUpdate();

                if (executeUpdate == 0) {
                    throw new ServiceFailureException("no room to edit");
                }
                if (executeUpdate != 1) {
                    throw new ServiceFailureException("something is wrong in database, multiple rows with same id");
                }

                conn.commit();
            }
        } catch (SQLException ex) {
            logger.warn("edit room sql ex", ex);
            throw new ServiceFailureException("Error when deleting room with id " + id, ex);
        }

    }

    @Override
    public Reservation findReservation(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("wrong id");
        }

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,responsiblePerson,account,dateOfCheckIn,duration,nOBed,idRoom FROM Reservations WHERE id = ?")) {
                st.setInt(1, id);
                try (ResultSet rs = st.executeQuery();) {
                    if (rs.next()) {
                        Reservation reservation = resultSetToReservation(rs);
                        if (rs.next()) {
                            throw new ServiceFailureException(
                                    "Internal error: More entities with the same id found "
                                    + "(source id: " + id + ", found " + reservation + " and " + resultSetToReservation(rs));
                        }
                        return reservation;
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException ex) {
            logger.warn("find reservation sql ex", ex);
            throw new ServiceFailureException(
                    "Error when retrieving reservation with id " + id, ex);
        }
    }

    private Reservation resultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setResponsiblePerson(rs.getString("responsiblePerson"));
        reservation.setAccount(rs.getString("account"));
        reservation.setDuration(rs.getInt("duration"));
        reservation.setIdRoom(rs.getInt("idRoom"));
        reservation.setNOBed(rs.getInt("nOBed"));
        reservation.setDateOfCheckIn(rs.getDate("dateOfCheckIn"));

        return reservation;
    }
}
