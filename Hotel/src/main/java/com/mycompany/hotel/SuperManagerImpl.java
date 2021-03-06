package com.mycompany.hotel;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Set;

/**
 *
 * @author Kubo
 */
public class SuperManagerImpl implements SuperManager {

    RoomManager roomManag;
    ReservationManager reservManag;

    public SuperManagerImpl() throws ClassNotFoundException, SQLException {
        this.roomManag = new RoomManagerImpl();
        this.reservManag = new ReservationManagerImpl();
    }

    @Override
    public Reservation chceckIn(String responsiblePerson, String account,
            Date dateOfCheckIn, int duration, int numBeds) throws SQLException {

        if (responsiblePerson == null) {
            throw new NullPointerException("Responsible person is null");
        }

        if (account == null) {
            throw new NullPointerException("Account is null");
        }

        if (dateOfCheckIn == null) {
            throw new NullPointerException("Date is null");
        }

        if (duration <= 0) {
            throw new IllegalArgumentException("duration can't be negative or zero");
        }

        if (numBeds <= 0) {
            throw new IllegalArgumentException("Number of Beds can'ŧ be negative or zero");
        }
        Reservation reservation = new Reservation(responsiblePerson, account, dateOfCheckIn, duration, numBeds);

        Room room = null;
        Set<Room> rooms = roomManag.findAllFreeRooms();
        for (Room rm : rooms) {
            if (rm.getNumberOfBeds() == numBeds && rm.getIdRes() == 0) {
                room = rm;
                break;
            }
        }
        if (room == null) {
            return null;
        }
        reservation.setIdRoom(room.getId());
        reservation = reservManag.storeReservation(reservation);

        roomManag.editRoom(room, reservation.getId());
      
        return reservation;
    }

    @Override
    public void checkOut(Integer reservId) {

        if (reservId == null) {
            throw new NullPointerException("Id is null");
        }

        if (reservId < 0) {
            throw new IllegalArgumentException("ID can't be negative");
        }

        Reservation reservation = reservManag.findReservation(reservId);
        int roomId = reservation.getIdRoom();
        reservManag.deleteReservation(reservation);
        roomManag.editRoom(roomManag.findRoom(roomId), 0);
    }
   
    @Override
    public Reservation findReservation(Integer id){
        return reservManag.findReservation(id);
    }
}
