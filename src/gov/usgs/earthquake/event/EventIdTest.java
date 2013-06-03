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

}
