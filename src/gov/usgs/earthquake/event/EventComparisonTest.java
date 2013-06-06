package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.Assert;

/**
 * Tests for EventComparison.
 */
public class EventComparisonTest {

	/**
	 * Test that null eventinfo parameters returns null comparison differences.
	 */
	@Test
	public void testEventWithoutLocation() {
		EventInfo withLocation = new DefaultEventInfo(null, new Date(),
				new BigDecimal("34"), new BigDecimal("118"), new BigDecimal("111.0"),
				new BigDecimal("9.0"));
		EventInfo withoutLocation = new DefaultEventInfo(null, null, null,
				new BigDecimal("118"), null, null);

		EventComparison comparison;

		// second parameter has nulls
		comparison = new EventComparison(withLocation, withoutLocation);
		Assert.assertNull("null time returns null difference",
				comparison.getTimeDifference());
		Assert.assertNull("null location returns null difference",
				comparison.getLocationDifference());
		Assert.assertNull("null depth returns null difference",
				comparison.getDepthDifference());
		Assert.assertNull("null magnitude returns null difference",
				comparison.getMagnitudeDifference());

		// first parameter has nulls
		comparison = new EventComparison(withoutLocation, withLocation);
		Assert.assertNull("null time returns null difference",
				comparison.getTimeDifference());
		Assert.assertNull("null location returns null difference",
				comparison.getLocationDifference());
		Assert.assertNull("null depth returns null difference",
				comparison.getDepthDifference());
		Assert.assertNull("null magnitude returns null difference",
				comparison.getMagnitudeDifference());
	}

}
