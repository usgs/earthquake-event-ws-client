package gov.usgs.earthquake.event;

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
}