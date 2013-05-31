package gov.usgs.earthquake.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * A wrapper around the Event Web Service.
 */
public class EventWebService {

	/** Base URL to the event web service. */
	private final URL serviceURL;

	/**
	 * Construct an EventWebService.
	 * 
	 * @param serviceURL
	 */
	public EventWebService(final URL serviceURL) {
		this.serviceURL = serviceURL;
	}

	/**
	 * Convert an EventQuery object into an EventWebService URL, using a
	 * specific return format.
	 * 
	 * @param query
	 *            the query.
	 * @param format
	 *            the format.
	 * @return a URL for query and format.
	 * @throws MalformedURLException
	 */
	public URL getURL(final EventQuery query, final Format format)
			throws MalformedURLException {
		return new URL(serviceURL, "query?starttime=-1%20days&format=geojson");
	}

	/**
	 * Request events from the event web service.
	 * 
	 * @param query
	 *            query describing events to return.
	 * @return list of events.
	 * @throws Exception
	 *             if any occur.
	 */
	public List<JSONEvent> getEvents(final EventQuery query) throws Exception {
		InputStream result = getInputStream(getURL(query, Format.GEOJSON));
		try {
			return parseJSONEventCollection(result);
		} finally {
			try {
				result.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}

	/**
	 * Parse the response from event web service into an array of JSONEvent
	 * objects.
	 * 
	 * @param input
	 *            input stream response from event web service.
	 * @return list of parsed events
	 * @throws Exception
	 *             if format is unexpected.
	 */
	public List<JSONEvent> parseJSONEventCollection(final InputStream input)
			throws Exception {
		JSONParser parser = new JSONParser();

		// parse feature collection into objects
		JSONObject collection = JSONUtil.getJSONObject(parser
				.parse(new InputStreamReader(input)));
		if (collection == null) {
			throw new Exception("Expected feature collection");
		}
		JSONArray features = JSONUtil.getJSONArray(collection.get("features"));
		if (features == null) {
			throw new Exception("Expected features");
		}

		// parse features into eventss
		ArrayList<JSONEvent> events = new ArrayList<JSONEvent>(features.size());
		Iterator<?> iter = features.iterator();
		while (iter.hasNext()) {
			JSONObject next = JSONUtil.getJSONObject(iter.next());
			if (next == null) {
				throw new Exception("Expected feature");
			}
			events.add(new JSONEvent(next));
		}

		return events;

	}

	/**
	 * Open an InputStream, attempting to use gzip compression.
	 * 
	 * @param url
	 *            url to open
	 * @return opened InputStream, ready to be read.
	 * @throws IOException
	 */
	public InputStream getInputStream(final URL url) throws IOException {
		// request gzip
		URLConnection conn = url.openConnection();
		conn.addRequestProperty("Accept-encoding", "gzip");
		InputStream in = conn.getInputStream();

		// ungzip response
		if (conn.getContentEncoding().equalsIgnoreCase("gzip")) {
			in = new GZIPInputStream(in);
		}

		return in;
	}

}
