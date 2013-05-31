package gov.usgs.earthquake.event;

import java.net.URL;
import java.util.List;

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
		List<JSONEvent> events = service.getEvents(null);
		System.err.println(events.size());
	}

}
