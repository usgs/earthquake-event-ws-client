package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.sql.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsonEventTest {

	JsonEvent event = null;

	@Before
	public void setup() {
		String feature = "{\"type\":\"Feature\",\"properties\":{\""
				+ "mag\":6.4,\""
				+ "place\":\"140km WNW of Neiafu, Tonga\",\""
				+ "time\":1368305217500,\""
				+ "updated\":1369489291000,\""
				+ "tz\":-720,\""
				+ "url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/usc000gudx\",\""
				+ "detail\":\"http://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/usc000gudx.geojson\",\""
				+ "felt\":2,\""
				+ "cdi\":4.1,\""
				+ "mmi\":4.09,\""
				+ "alert\":\"green\",\""
				+ "status\":\"REVIEWED\",\""
				+ "tsunami\":null,\""
				+ "sig\":631,\""
				+ "net\":\"us\",\""
				+ "code\":\"c000gudx\",\""
				+ "ids\":\",pt13131001,at00mmnj2c,usc000gudx,\",\""
				+ "sources\":\",pt,at,us,\",\""
				+ "types\":\",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,p-wave-travel-times,phase-data,scitech-link,shakemap,tectonic-summary,\",\""
				+ "nst\":520,\""
				+ "dmin\":5.02427738,\""
				+ "rms\":0.54,\""
				+ "gap\":32.4,\""
				+ "magType\":\"Mw\",\""
				+ "type\":\"earthquake\"},\""
				+ "geometry\":{\"type\":\"Point\",\"coordinates\":[-175.099,-17.954,212.2]},\""
				+ "id\":\"usc000gudx\"}";
		Object obj = JSONValue.parse(feature);
		JSONObject json = (JSONObject) obj;
		event = new JsonEvent(json);
	}

	@Test
	public void testEventId() {
		String network = "us";
		String code = "c000gudx";
		EventId id = new EventId(network, code);
		Assert.assertTrue(id.equals(event.getEventId()));
	}

	@Test
	public void testMag() {
		Assert.assertEquals(new BigDecimal("6.4"), event.getMag());
	}

	@Test
	public void testCdi() {
		Assert.assertEquals(new BigDecimal("4.1"), event.getCdi());
	}

	@Test
	public void testMmi() {
		Assert.assertEquals(new BigDecimal("4.09"), event.getMmi());
	}

	@Test
	public void testDmin() {
		Assert.assertEquals(new BigDecimal("5.02427738"), event.getDmin());
	}

	@Test
	public void testRms() {
		Assert.assertEquals(new BigDecimal("0.54"), event.getRms());
	}

	@Test
	public void testGap() {
		Assert.assertEquals(new BigDecimal("32.4"), event.getGap());
	}

	@Test
	public void testLongitude() {
		Assert.assertEquals(new BigDecimal("-175.099"), event.getLongitude());
	}

	@Test
	public void testLatitude() {
		Assert.assertEquals(new BigDecimal("-17.954"), event.getLatitude());
	}

	@Test
	public void testDepth() {
		Assert.assertEquals(new BigDecimal("212.2"), event.getDepth());
	}

	@Test
	public void testPlace() {
		Assert.assertEquals("140km WNW of Neiafu, Tonga", event.getPlace());
	}

	@Test
	public void testUrl() {
		Assert.assertEquals(
				"http://earthquake.usgs.gov/earthquakes/eventpage/usc000gudx",
				event.getUrl());
	}

	@Test
	public void testDetail() {
		Assert.assertEquals(
				"http://earthquake.usgs.gov/earthquakes/feed/v1.0/detail/usc000gudx.geojson",
				event.getDetail());
	}

	@Test
	public void testAlert() {
		Assert.assertEquals("green", event.getAlert());
	}

	@Test
	public void testStatus() {
		Assert.assertEquals("reviewed", event.getStatus().toLowerCase());
	}

	@Test
	public void testNet() {
		Assert.assertEquals("us", event.getNet());
	}

	@Test
	public void testCode() {
		Assert.assertEquals("c000gudx", event.getCode());
	}

	@Test
	public void testIds() {
		Assert.assertEquals(",pt13131001,at00mmnj2c,usc000gudx,", event.getIds());
	}

	@Test
	public void testSources() {
		Assert.assertEquals(",pt,at,us,", event.getSources());
	}

	@Test
	public void testTypes() {
		Assert.assertEquals(
				",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,p-wave-travel-times,phase-data,scitech-link,shakemap,tectonic-summary,",
				event.getTypes());
	}

	@Test
	public void testMagType() {
		Assert.assertEquals("Mw", event.getMagType());
	}

	@Test
	public void testTime() {
		Assert.assertEquals(new Date(1368305217500L), event.getTime());
	}

	@Test
	public void testUpdated() {
		Assert.assertEquals(new Date(1369489291000L), event.getUpdated());
	}

	@Test
	public void testTz() {
		Assert.assertEquals(new Integer("-720"), event.getTz());
	}

	@Test
	public void testFelt() {
		Assert.assertEquals(new Integer("2"), event.getFelt());
	}

	@Test
	public void testTsunami() {
		Assert.assertEquals(null, event.getTsunami());
	}

	@Test
	public void testSig() {
		Assert.assertEquals(new Integer("631"), event.getSig());
	}

	@Test
	public void testNst() {
		Assert.assertEquals(new Integer("520"), event.getNst());
	}

}
