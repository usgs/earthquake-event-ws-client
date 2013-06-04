package gov.usgs.earthquake.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventIdTest {

	private EventId id = null;
	private static String NETWORK = "network";
	private static String CODE = "code";

	@Before
	public void setup() {
		id = new EventId("network", "code");
	}

	@Test
	public void testNetwork() {
		Assert.assertEquals(NETWORK, id.getNetwork());
	}

	@Test
	public void testCode() {
		Assert.assertEquals(CODE, id.getCode());
	}

	@Test
	public void testString() {
		Assert.assertEquals(NETWORK + CODE, id.toString());
	}

	@Test
	public void testIllegalArguments() {
		try {
			new EventId(null, "something");
			Assert.fail("null network should throw exception");
		} catch (IllegalArgumentException iae) {
			// expected
		}
		try {
			new EventId("something", null);
			Assert.fail("null code should throw exception");
		} catch (IllegalArgumentException iae) {
			// expected
		}
		try {
			new EventId(null, null);
			Assert.fail("null network and code should throw exception");
		} catch (IllegalArgumentException iae) {
			// expected
		}
	}

	@Test
	public void testEquals () {
		String net = "foo";
		String code = "bar";
		EventId eventId1 = new EventId(net, code);
		Object eventId1_1 = new EventId(net, code);
		Object eventId2 = new EventId("something", "different");
		Object eventId2_1 = new EventId(net, "different");
		Object eventId2_2 = new EventId("something", code);

		// Test non-equality
		Assert.assertFalse("EventId not equal to regular Object.",
				eventId1.equals(new Object()));
		Assert.assertFalse("EventId not equal when network differs.",
				eventId1.equals(eventId2_1));
		Assert.assertFalse("EventId not equal when code differs.",
				eventId1.equals(eventId2_2));
		Assert.assertFalse("EventId not equal when net and code differs.",
				eventId1.equals(eventId2));

		// Test equality
		Assert.assertTrue("EventId equal to itself.", eventId1.equals(eventId1));
		Assert.assertTrue("EventId equal to simlar.", eventId1.equals(eventId1_1));
	}

}
