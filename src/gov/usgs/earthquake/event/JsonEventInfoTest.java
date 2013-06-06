package gov.usgs.earthquake.event;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for JsonEventInfo.
 */
public class JsonEventInfoTest {

	/**
	 * Test that getters access expected information from a JsonEvent.
	 */
	@Test
	public void testGetters() {
		JsonEventTest eventTest = new JsonEventTest();
		eventTest.setup();
		JsonEvent event = eventTest.event;

		JsonEventInfo eventInfo = new JsonEventInfo(event);
		Assert.assertEquals("id equals",
				event.getEventId(), eventInfo.getEventId());
		Assert.assertEquals("time equals",
				event.getTime(), eventInfo.getTime());
		Assert.assertEquals("latitude equals",
				event.getLatitude(), eventInfo.getLatitude());
		Assert.assertEquals("longitude equals",
				event.getLongitude(), eventInfo.getLongitude());
		Assert.assertEquals("depth equals",
				event.getDepth(), eventInfo.getDepth());
		Assert.assertEquals("magnitude equals",
				event.getMag(), eventInfo.getMagnitude());
	}

}