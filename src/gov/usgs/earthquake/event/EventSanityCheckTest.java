package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventSanityCheckTest {

	private JsonEventInfo a;
	private JsonEventInfo b;
	private JsonEventInfo c;
	private EventSanityCheck check;

	@Before
	public void setup() {
		check = new EventSanityCheck();

		String feature = "{\"type\":\"Feature\",\"properties\":{\"" +
				"mag\":6.4,\"" +
				"place\":\"140km WNW of Neiafu, Tonga\",\"" +
				"time\":1368305217500,\"" +
				"updated\":1369489291000,\"" +
				"tz\":-720,\"" +
				"url\":\"\",\"" +
				"detail\":\"\",\"" +
				"felt\":2,\"" +
				"cdi\":4.1,\"" +
				"mmi\":4.09,\"" +
				"alert\":\"green\",\"" +
				"status\":\"REVIEWED\",\"" +
				"tsunami\":null,\"" +
				"sig\":631,\"" +
				"net\":\"us\",\"" +
				"code\":\"c000gudx\",\"" +
				"ids\":\",pt13131001,at00mmnj2c,usc000gudx,\",\"" +
				"sources\":\",pt,at,us,\",\"" +
				"types\":\"\",\"" +
				"nst\":520,\"" +
				"dmin\":5.02427738,\"" +
				"rms\":0.54,\"" +
				"gap\":32.4,\"" +
				"magType\":\"Mw\",\"" +
				"type\":\"earthquake\"},\"" +
				"geometry\":{\"type\":\"Point\"," +
				"\"coordinates\":[-175.099,-17.954,212.2]},\"" +
				"id\":\"usc000gudx\"}";
		Object obj = JSONValue.parse(feature);
		JSONObject json = (JSONObject) obj;
		a = new JsonEventInfo(new JsonEvent(json));

		feature = "{\"type\":\"Feature\",\"properties\":{\"" +
				"mag\":6.0,\"" +
				"place\":\"140km WNW of Neiafu, Tonga\",\"" +
				"time\":1368305216500,\"" +
				"updated\":1369489291000,\"" +
				"tz\":-720,\"" +
				"url\":\"\",\"" +
				"detail\":\"\",\"" +
				"felt\":2,\"" +
				"cdi\":4.1,\"" +
				"mmi\":4.09,\"" +
				"alert\":\"green\",\"" +
				"status\":\"REVIEWED\",\"" +
				"tsunami\":null,\"" +
				"sig\":631,\"" +
				"net\":\"us\",\"" +
				"code\":\"c000gudx\",\"" +
				"ids\":\",pt13131001,at00mmnj2c,usc000gudx,\",\"" +
				"sources\":\",pt,at,us,\",\"" +
				"types\":\"\",\"" +
				"nst\":520,\"" +
				"dmin\":5.02427738,\"" +
				"rms\":0.54,\"" +
				"gap\":32.4,\"" +
				"magType\":\"Mw\",\"" +
				"type\":\"earthquake\"},\"" +
				"geometry\":{\"type\":\"Point\"," +
				"\"coordinates\":[-175.199,-17.854,202.2]},\"" +
				"id\":\"usc000gudx\"}";
		obj = JSONValue.parse(feature);
		json = (JSONObject) obj;
		b = new JsonEventInfo(new JsonEvent(json));
		
		feature = "{\"type\":\"Feature\",\"properties\":{\"" +
				"mag\":5.4,\"" +
				"place\":\"140km WNW of Neiafu, Tonga\",\"" +
				"time\":1368305211500,\"" +
				"updated\":1369489291000,\"" +
				"tz\":-720,\"" +
				"url\":\"\",\"" +
				"detail\":\"\",\"" +
				"felt\":2,\"" +
				"cdi\":4.1,\"" +
				"mmi\":4.09,\"" +
				"alert\":\"green\",\"" +
				"status\":\"REVIEWED\",\"" +
				"tsunami\":null,\"" +
				"sig\":631,\"" +
				"net\":\"us\",\"" +
				"code\":\"c000gudx\",\"" +
				"ids\":\",pt13131001,at00mmnj2c,usc000gudx,\",\"" +
				"sources\":\",pt,at,us,\",\"" +
				"types\":\"\",\"" +
				"nst\":520,\"" +
				"dmin\":5.02427738,\"" +
				"rms\":0.54,\"" +
				"gap\":32.4,\"" +
				"magType\":\"Mw\",\"" +
				"type\":\"earthquake\"},\"" +
				"geometry\":{\"type\":\"Point\"," +
				"\"coordinates\":[-176.199,-18.854,252.2]},\"" +
				"id\":\"usc000gudx\"}";
		obj = JSONValue.parse(feature);
		json = (JSONObject) obj;
		c = new JsonEventInfo(new JsonEvent(json));
	}

	@Test
	public void testIdenticalEvents() {
		EventComparison comp = new EventComparison(a, a);
		String errors = check.isMatch(comp);
		Assert.assertNull(errors);
	}

	@Test
	public void testCloseEvents() {
		EventComparison comp = new EventComparison(a, b);
		String errors = check.isMatch(comp);
		Assert.assertNull(errors);
	}

	@Test
	public void testDifferentEvents() {
		EventComparison comp = new EventComparison(a, c);
		String errors = check.isMatch(comp);
		System.out.println(errors);
		Assert.assertNotNull(errors);
	}

	@Test
	public void testDifferences() {
		EventSanityCheck timeCheck = new EventSanityCheck(BigDecimal.ONE, null, null, null);
		EventSanityCheck locationCheck = new EventSanityCheck(null, BigDecimal.ONE, null, null);
		EventSanityCheck magnitudeCheck = new EventSanityCheck(null, null, BigDecimal.ONE, null);
		EventSanityCheck depthCheck = new EventSanityCheck(null, null, null, BigDecimal.ONE);

		// comparison
		EventComparison timeComparison = new EventComparison(BigDecimal.TEN, null, null, null);
		EventComparison locationComparison = new EventComparison(null, BigDecimal.TEN, null, null);
		EventComparison magnitudeComparison = new EventComparison(null, null, BigDecimal.TEN, null);
		EventComparison depthComparison = new EventComparison(null, null, null, BigDecimal.TEN);

		Assert.assertNotNull("time checks time",
				timeCheck.isMatch(timeComparison));
		Assert.assertNull("time doesn't check location",
				timeCheck.isMatch(locationComparison));
		Assert.assertNull("time doesn't check magnitude",
				timeCheck.isMatch(magnitudeComparison));
		Assert.assertNull("time doesn't check depth",
				timeCheck.isMatch(depthComparison));

		Assert.assertNull("location doesn't check time",
				locationCheck.isMatch(timeComparison));
		Assert.assertNotNull("location checks location",
				locationCheck.isMatch(locationComparison));
		Assert.assertNull("location doesn't check magnitude",
				locationCheck.isMatch(magnitudeComparison));
		Assert.assertNull("location doesn't check depth",
				locationCheck.isMatch(depthComparison));

		Assert.assertNull("magnitude doesn't check time",
				magnitudeCheck.isMatch(timeComparison));
		Assert.assertNull("magnitude doesn't check location",
				magnitudeCheck.isMatch(locationComparison));
		Assert.assertNotNull("magnitude checks magnitude",
				magnitudeCheck.isMatch(magnitudeComparison));
		Assert.assertNull("magnitude doesn't check depth",
				magnitudeCheck.isMatch(depthComparison));

		Assert.assertNull("depth doesn't check time",
				depthCheck.isMatch(timeComparison));
		Assert.assertNull("depth doesn't check location",
				depthCheck.isMatch(locationComparison));
		Assert.assertNull("depth doesn't check magnitude",
				depthCheck.isMatch(magnitudeComparison));
		Assert.assertNotNull("depth checks depth",
				depthCheck.isMatch(depthComparison));

		Assert.assertEquals("time distance", 10.0,
				timeCheck.getDistance(timeComparison), 1e-6);
		Assert.assertEquals("time nodistance", 0.0,
				timeCheck.getDistance(locationComparison), 1e-6);
		Assert.assertEquals("location distance", 10.0,
				locationCheck.getDistance(locationComparison), 1e-6);
		Assert.assertEquals("location nodistance", 0.0,
				locationCheck.getDistance(magnitudeComparison), 1e-6);
		Assert.assertEquals("magnitude distance", 10.0,
				magnitudeCheck.getDistance(magnitudeComparison), 1e-6);
		Assert.assertEquals("magnitude nodistance", 0.0,
				magnitudeCheck.getDistance(depthComparison), 1e-6);
		Assert.assertEquals("depth distance", 10.0,
				depthCheck.getDistance(depthComparison), 1e-6);
		Assert.assertEquals("depth nodistance", 0.0,
				depthCheck.getDistance(timeComparison), 1e-6);
	}

}