package gov.usgs.earthquake.event;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test methods for the EventWebService.
 */
public class EventWebServiceTest {

	private static final int LOCAL_PORT = 9999;
	private static final String SUMMARY_FILE = "etc/testdata/summary.geojson";
	private static final String DETAIL_FILE = "etc/testdata/detail.geojson";

	private EventWebService comcat = null;
	private EventWebService local = null;
	private EventQuery query = null;

	@Before
	public void setup () throws Exception {
		comcat = new EventWebService(
				new URL("http://comcat.cr.usgs.gov/fdsnws/event/1/"));

		local = new EventWebService(new URL("http://localhost:" +
				String.valueOf(LOCAL_PORT) + "/fdsnws/event/1/"));

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
		Assert.assertEquals("Query url matches (explicit format).",
				expected, comcat.getUrl(query, Format.GEOJSON));

		// Test with an implied format (from query)
		query.setFormat(Format.GEOJSON);
		Assert.assertEquals("Query url matches (implied format).",
				expected, comcat.getUrl(query, null));
	}

	/**
	 * Make a request to the EventWebService.
	 *
	 * @throws Exception
	 */
	@Test
	public void testParseJsonEventCollection_summary () throws Exception {
		// Summary branch
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new FileInputStream(SUMMARY_FILE));
		Assert.assertEquals("11 events in summary feed", 11, events.size());
	}

	@Test
	public void testParseJsonEventCollection_detail () throws Exception {
		// Detail branch
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new FileInputStream(DETAIL_FILE));
		Assert.assertEquals("1 event in detail feed", 1, events.size());
		Assert.assertEquals("event id is 'usb000hc6w'", "usb000hc6w",
				events.get(0).getEventId().toString());
	}

	@Test(expected = Exception.class)
	public void testParseJsonEventCollection_nullFeed () throws Exception {
		comcat.parseJsonEventCollection(new ByteArrayInputStream(
				(new String("[]")).getBytes()));
	}

	@Test(expected = Exception.class)
	public void testParseJsonEventCollection_nullType () throws Exception {
		comcat.parseJsonEventCollection(new ByteArrayInputStream(
				(new String("{\"foo\": \"bar\"}")).getBytes()));
	}

	@Test
	public void testParseJsonEventCollection_unknownType () throws Exception {
		List<JsonEvent> events = comcat.parseJsonEventCollection(
				new ByteArrayInputStream((new String("{" +
						"\"type\":\"SomethingElese\"" +
					"}")).getBytes()));

		Assert.assertEquals("No events parsed from unknown (but valid) format.",
				0, events.size());
	}

	@Test(expected = Exception.class)
	public void testParseJsonEventCollection_nullFeatures () throws Exception {
		comcat.parseJsonEventCollection(new ByteArrayInputStream((new String("{" +
				"\"type\": \"FeatureCollection\"" + // Note: no "features" key
				"}")).getBytes()));
	}

	@Test(expected = Exception.class)
	public void testParseJsonEventCollection_nullFeature () throws Exception {
		comcat.parseJsonEventCollection(new ByteArrayInputStream((new String("{" +
				"\"type\":\"FeatureCollection\"" +
				"\"features\":[\"foo\", \"bar\"]" + // Array element not JSONObject
				"}")).getBytes()));
	}

	@Test
	public void testGetEvents () throws Exception {
		TestingWebServer server = null;
		List<JsonEvent> events = null;

		// No gzip
		server = new TestingWebServer(LOCAL_PORT, SUMMARY_FILE);
		server.start();
		events = local.getEvents(query);
		Assert.assertNotNull("Events fetched without compression.", events);
		server.stop();

		// With gzip
		server = new TestingWebServer(LOCAL_PORT, SUMMARY_FILE, true);
		server.start();
		events = null;
		events = local.getEvents(query);
		Assert.assertNotNull("Events fetched with compression.", events);
		server.stop();

		// Note: Checking we got the _expected_ set of events is tested in the
		//       parsing section of the unit tests.
	}

	@Test(expected = Exception.class)
	public void testGetEvents_Null () throws Exception {
		(new ExceptionEventWebService(
				new URL("http://localhost/"))).getEvents(new EventQuery());
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


	private class ExceptionEventWebService extends EventWebService {
		public ExceptionEventWebService (final URL url) {
			super(url);
		}

		public URL getUrl (EventQuery query, Format format)
				throws MalformedURLException {
			return null;
		}
	}

	private class TestingWebServer extends ServerSocket implements Runnable {
		private byte [] response = null;
		private boolean useGzip = false;
		private boolean running = false;

		public TestingWebServer (final int port, String responseFile)
				throws Exception {
			this(port, responseFile, false);
		}

		public TestingWebServer (final int port, final String responseFile,
				boolean useGzip) throws Exception {
			super(port);
			this.response = getBytes(responseFile);
			this.useGzip = useGzip;
		}

		public void run () {
			while (stillRunning()) {
				try {
					Socket request = accept();
					request.shutdownInput();

					OutputStream out = request.getOutputStream();
					sendResponse(out);

					out.flush();
					request.shutdownOutput();
				} catch (SocketException sx) {
					// Ignore
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
			}
		}

		public void start () throws Exception {
			running = true;
			(new Thread(this)).start();
		}

		public void stop () throws Exception {
			running = false;
			Thread.sleep(250); // Give server time to stop cleanly
			close();
		}

		public boolean stillRunning () {
			return running;
		}

		private void sendResponse (final OutputStream out) throws Exception {
			OutputStream output = out;

			output.write("HTTP/1.1 200 OK\r\n".getBytes());
			output.write((new String("Content-Type: application/json\r\n")
					.getBytes()));

			if (useGzip) {
				output.write((new String("Content-Encoding: gzip\r\n\r\n").getBytes()));
				output.flush();

				output = new GZIPOutputStream(out); // <-- will send gzip headers
			} else {
				output.write((new String("Content-Encoding: identity\n\n").getBytes()));
				output.flush();
			}

			output.write(response);

			if (useGzip) {
				((GZIPOutputStream)output).finish();
			}

			output.flush();
		}

		private byte [] getBytes (final String fileName) throws Exception {
			StringBuffer buf = new StringBuffer();
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			while ((line = reader.readLine()) != null) {
				buf.append(line);
			}

			return buf.toString().getBytes();
		}

	}
}
