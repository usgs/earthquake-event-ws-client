package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test methods for the EventWebService.
 */
public class EventWebServiceTest {

	/** Service URL for testing. */
	static final URL SERVICE_URL;
	static {
		URL url = null;
		try {
			url = new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/");
		} catch (Exception e) {
			e.printStackTrace();
		}
		SERVICE_URL = url;
	}

	/**
	 * Make a request to the EventWebService using an empty request object.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequest() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);

		EventQuery query = new EventQuery();
		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));

		List<JSONEvent> events = service.getEvents(query);
		System.err.println(events.size() + " M2.5+ events, past day");
		Assert.assertTrue("events in past day", events.size() > 0);
	}

}
