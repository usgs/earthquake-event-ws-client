package gov.usgs.earthquake.event;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventQueryTest {

	private EventQuery query = null;

	@Before
	public void setup() {
		query = new EventQuery();
	}

	@Test
	public void testStartTime() {
		long startTime = 0x12345L;
		query.setStartTime(startTime);
		Assert.assertEquals(startTime, query.getStartTime());
	}

	@Test
	public void testEndTime() {
		long endTime = 0x23456L;
		query.setEndTime(endTime);
		Assert.assertEquals(endTime, query.getEndTime());
	}

	@Test
	public void testUpdatedAfter() {
		long updatedAfter = 0x34567L;
		query.setUpdatedAfter(updatedAfter);
		Assert.assertEquals(updatedAfter, query.getUpdatedAfter());
	}

	@Test
	public void testLatitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setLatitude(test);
		Assert.assertEquals(test, query.getLatitude());
	}

	@Test
	public void testLongitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setLongitude(test);
		Assert.assertEquals(test, query.getLongitude());
	}
/*
	@Test
	public void test() {
		BigDecimal test = new BigDecimal(.1);
		query.set(test);
		Assert.assertEquals(test, query.get());
	}
*/
	@Test
	public void testOrderBy() {
		query.setOrderBy(OrderBy.TIME_ASC);
		Assert.assertEquals(OrderBy.TIME_ASC, query.getOrderBy());
		Assert.assertEquals("time-asc", OrderBy.TIME_ASC.toString());
	}

	@Test
	public void testFormat() {
		query.setFormat(Format.KML);
		Assert.assertEquals(Format.KML, query.getFormat());
		Assert.assertEquals("kml", Format.KML.toString());
	}

	@Test
	public void testKmlColorBy() {
		query.setKmlColorBy(KmlColorBy.DEPTH);
		Assert.assertEquals(KmlColorBy.DEPTH, query.getKmlColorBy());
		Assert.assertEquals("depth", KmlColorBy.DEPTH.toString());
	}

	@Test
	public void testReviewStatus() {
		query.setReviewStatus(ReviewStatus.ALL);
		Assert.assertEquals(ReviewStatus.ALL, query.getReviewStatus());
		Assert.assertEquals("all", ReviewStatus.ALL.toString());
	}

	@Test
	public void testAlertLevel() {
		query.setAlertLevel(AlertLevel.RED);
		Assert.assertEquals(AlertLevel.RED, query.getAlertLevel());
		Assert.assertEquals("red", AlertLevel.RED.toString());
	}
}
