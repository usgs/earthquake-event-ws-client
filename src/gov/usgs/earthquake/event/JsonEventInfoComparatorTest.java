package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeSet;

import junit.framework.Assert;

import org.json.simple.JSONValue;
import org.junit.Before;
import org.junit.Test;

public class JsonEventInfoComparatorTest {

	private JsonEvent referenceEvent;
	private JsonEvent closeEvent;
	private JsonEvent farEvent;
	private JsonEvent fartherEvent;

	@Before
	public void setup() {
		Date time = new Date();
		BigDecimal latitude = new BigDecimal("34");
		BigDecimal longitude = new BigDecimal("-118");
		BigDecimal depth = new BigDecimal("43");
		BigDecimal magnitude = new BigDecimal("3.1");

		referenceEvent = getJsonEvent(time, latitude, longitude, depth, magnitude);
		closeEvent = getJsonEvent(new Date(time.getTime() + 1000L),
				latitude.add(new BigDecimal("0.1")),
				longitude.add(new BigDecimal("0.1")), depth.add(new BigDecimal("0.1")),
				magnitude.add(new BigDecimal("0.1")));
		farEvent = getJsonEvent(new Date(time.getTime() - 2000L),
				latitude.subtract(new BigDecimal("0.2")),
				longitude.subtract(new BigDecimal("0.2")),
				depth.subtract(new BigDecimal("0.2")),
				magnitude.subtract(new BigDecimal("0.2")));
		fartherEvent = getJsonEvent(new Date(time.getTime() - 5000L),
				latitude.subtract(new BigDecimal("0.5")),
				longitude.subtract(new BigDecimal("0.5")),
				depth.subtract(new BigDecimal("0.5")),
				magnitude.subtract(new BigDecimal("0.5")));
	}

	protected JsonEvent getJsonEvent(final Date time, final BigDecimal latitude,
			final BigDecimal longitude, final BigDecimal depth, final BigDecimal mag) {
		return new JsonEvent(JsonUtil.getJsonObject(JSONValue
				.parse("{\"type\":\"Feature\", \"properties\":{\"time\":"
						+ time.getTime() + ", \"mag\":" + mag.toString()
						+ "}, \"geometry\": {\"coordinates\":[" + longitude.toString()
						+ "," + latitude.toString() + "," + depth.toString() + "]}}")));
	}

	@Test
	public void testEventSorting() {

		TreeSet<JsonEventInfo> set = new TreeSet<JsonEventInfo>(
				new JsonEventInfoComparator(new EventSanityCheck(), new JsonEventInfo(
						referenceEvent)));
		set.add(new JsonEventInfo(fartherEvent));

		// force recomputation for "event2" branch.
		set.first().setDistance(null);
		set.first().setEventComparison(null);

		set.add(new JsonEventInfo(closeEvent));
		set.add(new JsonEventInfo(farEvent));

		JsonEventInfo first = set.first();
		Assert.assertSame("closest event is first", closeEvent, first.getEvent());
	}

}
