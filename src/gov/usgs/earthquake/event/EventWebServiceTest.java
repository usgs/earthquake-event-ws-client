package gov.usgs.earthquake.event;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
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

	@Test
	public void testGetUrl() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);
		EventQuery query = new EventQuery();
		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));
		Assert.assertEquals("query url matches",
				service.getUrl(query, Format.GEOJSON),
				new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/query?"
						+ "minmagnitude=2.5"
						+ "&starttime=" + service.getIso8601Date(query.getStartTime())
						+ "&format=geojson"));
	}

	/**
	 * Make a request to the EventWebService.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseSummary() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);
		List<JsonEvent> events = service.parseJsonEventCollection(
				new FileInputStream(new File("etc/geojson_testdata/summary.geojson")));
		Assert.assertEquals("11 events in summary feed", 11, events.size());
	}

	/**
	 * Make a request for one event.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseDetail() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);
		List<JsonEvent> events = service.parseJsonEventCollection(
				new FileInputStream(new File("etc/geojson_testdata/detail.geojson")));
		Assert.assertEquals("1 event in detail feed", 1, events.size());
		Assert.assertEquals("event id is 'usb000hc6w'", "usb000hc6w",
				events.get(0).getEventId().toString());
	}

	/**
	 * Make a request to the EventWebService using a quakeml format.
	 *
	 * This method is no longer marked as a junit test because it requires an
	 * internet connection, however it is left as a potentially useful example of
	 * how to request another web-service format.
	 *
	 * @throws Exception
	 */
	public void testQuakeml() throws Exception {
		EventWebService service = new EventWebService(SERVICE_URL);

		EventQuery query = new EventQuery();
		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));
		query.setFormat(Format.QUAKEML);

		InputStream quakeml = UrlUtil.getInputStream(service.getUrl(query, null));
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
