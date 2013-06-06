package gov.usgs.earthquake.event;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for DefaultEventInfo.
 */
public class DefaultEventInfoTest {

	/**
	 * Test the getter and setter for eventid.
	 */
	@Test
	public void testEventId() {
		DefaultEventInfo event = new DefaultEventInfo();
		event.setEventId(new EventId("network", "code"));
		Assert.assertEquals("test event id",
				"networkcode", event.getEventId().toString());
	}

}