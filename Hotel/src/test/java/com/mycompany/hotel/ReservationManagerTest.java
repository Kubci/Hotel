package com.mycompany.hotel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ReservationManagerTest {
	
	private ReservationManager manager;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		manager = new ReservationManagerImpl();
	}
	
	@Test
	public void CreateReservationTest() throws SQLException {
		
		Reservation reservation = simpleReservationBuilder();		
		reservation.setId(manager.storeReservation(reservation).getId());
		
		Reservation reservation2 = manager.findReservation(reservation.getId());
		
		assertEquals(reservation.getResponsiblePerson(), reservation2.getResponsiblePerson());
		//assertEquals(reservation.getOtherPersons(), reservation2.getOtherPersons());
		assertEquals(reservation.getAccount(), reservation2.getAccount());
		assertEquals(reservation.getId(), reservation2.getId());
		assertEquals(reservation.getDateOfCheckIn(), reservation2.getDateOfCheckIn());
		assertEquals(reservation.getDuration(), reservation2.getDuration());
		assertEquals(reservation.getNOBed(), reservation2.getNOBed());
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void deleteReservationWithWrongArgumentTest() {
		manager.deleteReservation(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteReservationTest() throws SQLException {
		manager.deleteReservation(null);
	}
	@Test(expected = IllegalArgumentException.class) 
	public void createReservationWithWrongArgumentTest() throws SQLException {
		manager.storeReservation(null);
	}
	@Test(expected = IllegalArgumentException.class) 
	public void updateReservatioinWithWrongArgumentTest() {
		manager.editReservation(null);
	}
	@Test(expected = IllegalArgumentException.class) 
	public void getReservationWithWrongArgumentTest() {
		manager.findReservation(-1);
	}
	
	private Reservation simpleReservationBuilder() {
		String acc = "123456789";
		Date checkIn = new Date(500000);
		String resPers = "ahoj";
		List<String> names = new ArrayList<>();
		names.add("lala");
		names.add("nazdar");
		names.add("cau");
		
		int duration = 10;
		int nBeds = 4;
		
                Reservation reserv = new Reservation(resPers, acc, checkIn, duration, nBeds);
                return reserv;
	}
}
