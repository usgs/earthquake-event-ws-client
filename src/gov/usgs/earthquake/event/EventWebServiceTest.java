package gov.usgs.earthquake.event;

import java.io.InputStream;
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
	 * Make a request to the EventWebService.
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

		List<JsonEvent> events = service.getEvents(query);
		Assert.assertTrue("events in past day", events.size() > 0);
	}

	/**
	 * Make a request for one event.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDetailRequest() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);

		EventQuery query = new EventQuery();
		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));
		// only need 1 event
		query.setLimit(1);

		List<JsonEvent> events = service.getEvents(query);
		Assert.assertTrue("most recent event in past day", events.size() > 0);

		EventQuery detailQuery = new EventQuery();
		detailQuery.setEventId(events.get(0).getEventID().toString());
		events = service.getEvents(detailQuery);
		Assert.assertTrue("one event in detail query", events.size() == 1);
	}

	/**
	 * Make a request to the EventWebService using a quakeml format.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQuakeml() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);

		EventQuery query = new EventQuery();
		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));
		query.setFormat(Format.QUAKEML);

		InputStream quakeml = service.getInputStream(service.getUrl(query, null));
		try {
			int read = 0;
			byte[] buf = new byte[1024];
			while ((read = quakeml.read(buf)) != -1) {
				System.err.write(buf, 0, read);
			}
		} finally {
			try {
				quakeml.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}

}
