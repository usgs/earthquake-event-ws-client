package gov.usgs.earthquake.event;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 * Test methods for the EventWebService.
 */
public class EventWebServiceTest {

	private EventWebService comcat = null;
	private EventWebService summary = null;
	private EventWebService detail = null;
	private EventQuery query = null;

	@Before
	public void setup () throws Exception {
		comcat = new EventWebService(
				new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/"));
		summary = new EventWebService(
				(new File("etc/geojson_testdata/summary/query")).toURI().toURL());
		detail = new EventWebService(
				(new File("etc/geojson_testdata/detail/query")).toURI().toURL());

		query = new EventQuery();
	}

	@Test
	public void testGetUrl() throws Exception {
		EventQuery query = new EventQuery();
		URL expected = null;

		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));

		expected = new URL(
				"http://comcat.cr.usgs.gov/fdsnws/event/1/query?" +
				"minmagnitude=2.5" +
				"&starttime=" + EventWebService.getIso8601Date(query.getStartTime()) +
				"&format=geojson");

		// Test with an explicit format
		Assert.assertEquals("query url matches (explicit format).",
				expected, comcat.getUrl(query, Format.GEOJSON));

		// Test with an implied format (from query)
		query.setFormat(Format.GEOJSON);
		Assert.assertEquals("query url matches (implied format).",
				expected, comcat.getUrl(query, null));
	}

	/**
	 * Make a request to the EventWebService.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseJsonEventCollection() throws Exception {
		// Summary branch
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new FileInputStream("etc/geojson_testdata/summary/query"));
		Assert.assertEquals("11 events in summary feed", 11, events.size());

		// Detail branch
		events = comcat.parseJsonEventCollection(
				new FileInputStream("etc/geojson_testdata/detail/query"));
		Assert.assertEquals("1 event in detail feed", 1, events.size());
		Assert.assertEquals("event id is 'usb000hc6w'", "usb000hc6w",
				events.get(0).getEventId().toString());
	}

	@Test(expected = Exception.class)
	public void testNullFeed_parseJsonEventCollection () throws Exception {
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new ByteArrayInputStream((new String("[]"))
						.getBytes()));
	}

	@Test(expected = Exception.class)
	public void testNullType_parseJsonEventCollection () throws Exception {
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new ByteArrayInputStream((new String("{\"foo\": \"bar\"}"))
						.getBytes()));
	}

	@Test
	public void testUnknownType_parseJsonEventCollection () throws Exception {
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new ByteArrayInputStream((new String("{" +
						"\"type\":\"SomethingElese\"" +
					"}")).getBytes()));

		Assert.assertEquals("No events parsed from unknown (but valid) format.",
				0, events.size());
	}

	@Test(expected = Exception.class)
	public void testNullFeatures_parseJsonEventCollection () throws Exception {
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new ByteArrayInputStream((new String("{" +
					"\"type\": \"FeatureCollection\"" + // Note: no "features" key
					"}")).getBytes()));
	}

	@Test(expected = Exception.class)
	public void testNullFeature_parseJsonEventCollection () throws Exception {
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new ByteArrayInputStream((new String("{" +
						"\"type\":\"FeatureCollection\"" +
						"\"features\":[\"foo\", \"bar\"]" + // Array element not JSONObject
					"}")).getBytes()));
	}

	@Test
	public void testGetEvents () throws Exception {
		List<JsonEvent> events = summary.getEvents(query);
		Assert.assertNotNull("Successfully got events.", events);

		// Note: Checking we got the _expected_ set of events is tested in the
		//       parsing section of the unit tests.
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
		// past day
		query.setStartTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		// M2.5+
		query.setMinMagnitude(new BigDecimal("2.5"));
		query.setFormat(Format.QUAKEML);

		InputStream quakeml = UrlUtil.getInputStream(comcat.getUrl(query, null));
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
