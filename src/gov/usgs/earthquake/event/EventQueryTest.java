package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

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
		query.setStartTime(new Date(startTime));
		Assert.assertEquals(new Date(startTime), query.getStartTime());
	}

	@Test
	public void testEndTime() {
		long endTime = 0x23456L;
		query.setEndTime(new Date(endTime));
		Assert.assertEquals(new Date(endTime), query.getEndTime());
	}

	@Test
	public void testUpdatedAfter() {
		long updatedAfter = 0x34567L;
		query.setUpdatedAfter(new Date(updatedAfter));
		Assert.assertEquals(new Date(updatedAfter), query.getUpdatedAfter());
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

	@Test
	public void testMinRadius() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinRadius(test);
		Assert.assertEquals(test, query.getMinRadius());
	}

	@Test
	public void testMaxRadius() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxRadius(test);
		Assert.assertEquals(test, query.getMaxRadius());
	}

	@Test
	public void testMinLatitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinLatitude(test);
		Assert.assertEquals(test, query.getMinLatitude());
	}

	@Test
	public void testMaxLatitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxLatitude(test);
		Assert.assertEquals(test, query.getMaxLatitude());
	}

	@Test
	public void testMinLongitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinLongitude(test);
		Assert.assertEquals(test, query.getMinLongitude());
	}

	@Test
	public void testMaxLongitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxLongitude(test);
		Assert.assertEquals(test, query.getMaxLongitude());
	}

	@Test
	public void testMinDepth() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinDepth(test);
		Assert.assertEquals(test, query.getMinDepth());
	}

	@Test
	public void testMaxDepth() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxDepth(test);
		Assert.assertEquals(test, query.getMaxDepth());
	}

	@Test
	public void testMinMagnitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinMagnitude(test);
		Assert.assertEquals(test, query.getMinMagnitude());
	}

	@Test
	public void testMaxMagnitude() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxMagnitude(test);
		Assert.assertEquals(test, query.getMaxMagnitude());
	}

	@Test
	public void testMinMmi() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinMmi(test);
		Assert.assertEquals(test, query.getMinMmi());
	}

	@Test
	public void testMaxMmi() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxMmi(test);
		Assert.assertEquals(test, query.getMaxMmi());
	}

	@Test
	public void testMinCdi() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinCdi(test);
		Assert.assertEquals(test, query.getMinCdi());
	}

	@Test
	public void testMaxCdi() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxCdi(test);
		Assert.assertEquals(test, query.getMaxCdi());
	}

	@Test
	public void testMinGap() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinGap(test);
		Assert.assertEquals(test, query.getMinGap());
	}

	@Test
	public void testMaxGap() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxGap(test);
		Assert.assertEquals(test, query.getMaxGap());
	}

	@Test
	public void testMinSig() {
		BigDecimal test = new BigDecimal(.1);
		query.setMinSig(test);
		Assert.assertEquals(test, query.getMinSig());
	}

	@Test
	public void testMaxSig() {
		BigDecimal test = new BigDecimal(.1);
		query.setMaxSig(test);
		Assert.assertEquals(test, query.getMaxSig());
	}

	@Test
	public void testLimit() {
		Integer test = 1;
		query.setLimit(test);
		Assert.assertEquals(test, query.getLimit());
	}

	@Test
	public void testOffset() {
		Integer test = 1;
		query.setOffset(test);
		Assert.assertEquals(test, query.getOffset());
	}

	@Test
	public void testMinFelt() {
		Integer test = 1;
		query.setMinFelt(test);
		Assert.assertEquals(test, query.getMinFelt());
	}

	@Test
	public void testMagnitudeType() {
		String test = "test";
		query.setMagnitudeType(test);
		Assert.assertEquals(test, query.getMagnitudeType());
	}

	@Test
	public void testEventId() {
		String test = "test";
		query.setEventId(test);
		Assert.assertEquals(test, query.getEventId());
	}

	@Test
	public void testEventType() {
		String test = "test";
		query.setEventType(test);
		Assert.assertEquals(test, query.getEventType());
	}

	@Test
	public void testCatalog() {
		String test = "test";
		query.setCatalog(test);
		Assert.assertEquals(test, query.getCatalog());
	}

	@Test
	public void testContributor() {
		String test = "test";
		query.setContributor(test);
		Assert.assertEquals(test, query.getContributor());
	}

	@Test
	public void testProductType() {
		String test = "test";
		query.setProductType(test);
		Assert.assertEquals(test, query.getProductType());
	}

	@Test
	public void testIncludeAllOrigins() {
		Boolean test = true;
		query.setIncludeAllOrigins(test);
		Assert.assertEquals(test, query.getIncludeAllOrigins());
	}

	@Test
	public void testIncludeAllMagnitudes() {
		Boolean test = true;
		query.setIncludeAllMagnitudes(test);
		Assert.assertEquals(test, query.getIncludeAllMagnitudes());
	}

	@Test
	public void testIncludeArrivals() {
		Boolean test = true;
		query.setIncludeArrivals(test);
		Assert.assertEquals(test, query.getIncludeArrivals());
	}

	@Test
	public void testKmlAnimated() {
		Boolean test = true;
		query.setKmlAnimated(test);
		Assert.assertEquals(test, query.getKmlAnimated());
	}

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
