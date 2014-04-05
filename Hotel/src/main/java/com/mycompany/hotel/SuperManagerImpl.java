package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Kubo
 */
public class SuperManagerImpl implements SuperManager{

    RoomManager roomManag;
    ReservationManager reservManag;

    public SuperManagerImpl() throws ClassNotFoundException, SQLException {
        this.roomManag = new RoomManagerImpl();
        this.reservManag = new ReservationManagerImpl();
    }

    @Override
    public Reservation chceckIn(String responsiblePerson, List<String> otherPersons, String account,
            Date DateOfCheckIn, int duration, int NOBed) throws SQLException {
           Reservation reservation = new Reservation(responsiblePerson, account, DateOfCheckIn, duration, NOBed);
           Room room = new Room(1, NOBed);
           
           room = roomManag.storeRoom(room);
           reservation.setIdRoom(room.getId());
           reservManag.storeReservation(reservation);
           return reservation;
    }

    @Override
    public void checkOut(Integer reservId) {
        Reservation reservation = reservManag.findReservation(reservId);
        int roomId = reservation.getIdRoom();
        reservManag.deleteReservation(reservation);
        roomManag.deleteRoom(roomManag.findRoom(roomId));
    }

    @Override
    public Reservation findReservation(String responsiblePerson) {
        Set<Reservation> all = new HashSet<>();
        int find = 1;
        while (reservManag.findReservation(find) != null) {
            if (reservManag.findReservation(find).getResponsiblePerson().equals(responsiblePerson)) {
                return reservManag.findReservation(find);
            }
            find++;
            
        }
        return null;

    }

}
