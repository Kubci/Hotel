package com.mycompany.hotel;

import java.sql.SQLException;
import java.sql.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuperManagerImplTest {
	
	private static final String resPerson = "Hugo Kokoska";
	private static final String account = "123456789";
	private static final Date date = new Date(500_000_000);
	private static final int duration = 5;
	private static final int noBeds = 10;
	
	@Test
	public void checkInTest() throws ClassNotFoundException, SQLException {
		
		SuperManagerImpl manager = new SuperManagerImpl();
		Reservation asdf = manager.chceckIn(resPerson, account, date, duration, noBeds);
		
		assertEquals(resPerson, asdf.getResponsiblePerson());
		assertEquals(account, asdf.getAccount());
		assertEquals(date.getTime(), asdf.getDateOfCheckIn().getTime());
		assertEquals(duration, asdf.getDuration());
		assertEquals(noBeds, asdf.getNOBed());
	}
	
	@Test(expected = NullPointerException.class)
	public void checkInWrongParametersTest() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		manager.chceckIn(null, account, date, duration, noBeds);
	}
	
	@Test(expected = NullPointerException.class)
	public void checkInWrongParametersTest2() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		manager.chceckIn(resPerson, null, date, duration, noBeds);
	}
	
	@Test(expected = NullPointerException.class)
	public void checkInWrongParametersTest3() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		manager.chceckIn(resPerson, account, null, duration, noBeds);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkInWrongParametersTest4() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		manager.chceckIn(resPerson, account, date, -1, noBeds);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkInWrongParametersTest5() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		manager.chceckIn(resPerson, account, date, duration, -1);
	}
	
	@Test
	public void findReservationTest() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		Reservation insertedRes = manager.chceckIn(resPerson, account, date, duration, noBeds);
		manager.chceckIn("ahoj", "987654321", new Date(700000000), 1, 1);
		
		Reservation foundRes = manager.findReservation(resPerson);
		          System.out.println(foundRes == null);
		assertEquals(insertedRes.getAccount(), foundRes.getAccount());
		assertEquals(insertedRes.getDateOfCheckIn(), foundRes.getDateOfCheckIn());
		assertEquals(insertedRes.getDuration(), foundRes.getDuration());
		assertEquals(insertedRes.getId(), foundRes.getId());
		assertEquals(insertedRes.getIdRoom(), foundRes.getIdRoom());
		assertEquals(insertedRes.getNOBed(), foundRes.getNOBed());
		assertEquals(insertedRes.getResponsiblePerson(), foundRes.getResponsiblePerson());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void findReservationTestWrongArguments() throws SQLException, ClassNotFoundException {
		SuperManagerImpl manager = new SuperManagerImpl();
		Reservation insertedRes = manager.chceckIn(resPerson, account, date, duration, noBeds);
		manager.findReservation("");
	}

	@Test(expected = NullPointerException.class)
	public void findReservationTestWrongArguments2() throws ClassNotFoundException, SQLException {
		SuperManagerImpl manager = new SuperManagerImpl();
		Reservation insertedRes = manager.chceckIn(resPerson, account, date, duration, noBeds);
		manager.findReservation(null);
	}
	
	@Test
	public void checkOutTest() throws ClassNotFoundException, SQLException {
		SuperManagerImpl manager = new SuperManagerImpl();
		Reservation insertedRes = manager.chceckIn(resPerson, account, date, duration, noBeds);
		
		manager.checkOut(insertedRes.getId());
		
		assertEquals(null, manager.findReservation(insertedRes.getResponsiblePerson()));
	}
}
