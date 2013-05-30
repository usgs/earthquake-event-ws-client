package gov.usgs.earthquake.event;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONEventTest {

	private JSONObject json = null;

	@Before
	public void setup() {
		json = new JSONObject();
	}

	@Test
	public void testEventId() {
		String network = "network";
		String code = "code";
		EventID id = new EventID(network, code);

		json.put("net", network);
		json.put("code", code);
		JSONEvent event = new JSONEvent(json);

		Assert.assertTrue(id.equals(event.getEventID()));
	}

	@Test
	public void testMag() {
		String mag = "4.5";

		json.put("mag", mag);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(mag), event.getMag());
	}

	@Test
	public void testCdi() {
		String cdi = "4.25";

		json.put("cdi", cdi);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(cdi), event.getCdi());
	}

	@Test
	public void testMmi() {
		String mmi = "5.5";

		json.put("mmi", mmi);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(mmi), event.getMmi());
	}

	@Test
	public void testDmin() {
		String test = "5.5";

		json.put("dmin", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(test), event.getDmin());
	}

	@Test
	public void testRms() {
		String test = "5.5";

		json.put("rms", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(test), event.getRms());
	}

	@Test
	public void testGap() {
		String test = "5.5";

		json.put("gap", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(test), event.getGap());
	}

	@Test
	public void testLongitude() {
		String test = "5.5";

		json.put("longitude", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(test), event.getLongitude());
	}

	@Test
	public void testLatitude() {
		String test = "5.5";

		json.put("latitude", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(test), event.getLatitude());
	}

	@Test
	public void testDepth() {
		String test = "5.5";

		json.put("depth", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(new BigDecimal(test), event.getDepth());
	}

	@Test
	public void testPlace() {
		String test = "5.5";

		json.put("place", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getPlace());
	}

	@Test
	public void testUrl() {
		String test = "5.5";

		json.put("url", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getUrl());
	}

	@Test
	public void testDetail() {
		String test = "5.5";

		json.put("detail", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getDetail());
	}

	@Test
	public void testAlert() {
		String test = "5.5";

		json.put("alert", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getAlert());
	}

	@Test
	public void testStatus() {
		String test = "5.5";

		json.put("status", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getStatus());
	}

	@Test
	public void testNet() {
		String test = "5.5";

		json.put("net", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getNet());
	}

	@Test
	public void testCode() {
		String test = "5.5";

		json.put("code", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getCode());
	}

	@Test
	public void testIds() {
		String test = "5.5";

		json.put("ids", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getIds());
	}

	@Test
	public void testSources() {
		String test = "5.5";

		json.put("sources", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getSources());
	}

	@Test
	public void testTypes() {
		String test = "5.5";

		json.put("types", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getTypes());
	}

	@Test
	public void testMagType() {
		String test = "5.5";

		json.put("magType", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(test, event.getMagType());
	}

	@Test
	public void testTime() {
		String test = "5";

		json.put("time", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getTime());
	}

	@Test
	public void testUpdated() {
		String test = "5";

		json.put("updated", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getUpdated());
	}

	@Test
	public void testTz() {
		String test = "5";

		json.put("tz", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getTz());
	}

	@Test
	public void testFelt() {
		String test = "5";

		json.put("felt", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getFelt());
	}

	@Test
	public void testTsunami() {
		String test = "5";

		json.put("tsunami", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getTsunami());
	}

	@Test
	public void testSig() {
		String test = "5";

		json.put("sig", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getSig());
	}

	@Test
	public void testNst() {
		String test = "5";

		json.put("nst", test);
		JSONEvent event = new JSONEvent(json);

		Assert.assertEquals(Integer.parseInt(test), event.getNst());
	}
}
