package gov.usgs.earthquake.event;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	 * Convert an EventQuery object into an EventWebService URL, using a specific
	 * return format.
	 *
	 * @param query
	 *          the query.
	 * @param format
	 *          the format.
	 * @return a URL for query and format.
	 * @throws MalformedURLException
	 */
	public URL getUrl(final EventQuery query, final Format format)
			throws MalformedURLException {

		// fill hashmap with parameters
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("alertlevel", query.getAlertLevel());
		params.put("catalog", query.getCatalog());
		params.put("contributor", query.getContributor());
		params.put("endtime", ISO8601.format(query.getEndTime()));
		params.put("eventid", query.getEventId());
		params.put("eventtype", query.getEventType());
		params.put("format", format == null ? query.getFormat() : format);
		params.put("includeallmagnitudes", query.getIncludeAllMagnitudes());
		params.put("includeallorigins", query.getIncludeAllOrigins());
		params.put("includearrivals", query.getIncludeArrivals());
		params.put("kmlanimated", query.getKmlAnimated());
		params.put("kmlcolorby", query.getKmlColorBy());
		params.put("latitude", query.getLatitude());
		params.put("limit", query.getLimit());
		params.put("longitude", query.getLongitude());
		params.put("magnitudetype", query.getMagnitudeType());
		params.put("maxcdi", query.getMaxCdi());
		params.put("maxdepth", query.getMaxDepth());
		params.put("maxgap", query.getMaxGap());
		params.put("maxlatitude", query.getMaxLatitude());
		params.put("maxlongitude", query.getMaxLongitude());
		params.put("maxmagnitude", query.getMaxMagnitude());
		params.put("maxmmi", query.getMaxMmi());
		params.put("maxradius", query.getMaxRadius());
		params.put("maxsig", query.getMaxSig());
		params.put("mincdi", query.getMinCdi());
		params.put("mindepth", query.getMinDepth());
		params.put("minfelt", query.getMinFelt());
		params.put("mingap", query.getMinGap());
		params.put("minlatitude", query.getMinLatitude());
		params.put("minlongitude", query.getMinLongitude());
		params.put("minmagnitude", query.getMinMagnitude());
		params.put("minmmi", query.getMinMmi());
		params.put("minradius", query.getMinRadius());
		params.put("minsig", query.getMinSig());
		params.put("offset", query.getOffset());
		params.put("orderby", query.getOrderBy());
		params.put("producttype", query.getProductType());
		params.put("reviewstatus", query.getReviewStatus());
		params.put("starttime", ISO8601.format(query.getStartTime()));
		params.put("updatedafter", ISO8601.format(query.getUpdatedAfter()));

		String queryString = UrlUtil.getQueryString(params);
		return new URL(serviceURL, "query" + queryString);
	}

	/**
	 * Request events from the event web service.
	 *
	 * @param query
	 *          query describing events to return.
	 * @return list of events.
	 * @throws Exception
	 *           if any occur.
	 */
	public List<JsonEvent> getEvents(final EventQuery query) throws Exception {
		InputStream result = null;
		List<JsonEvent> events = null;

		try {
			result = UrlUtil.getInputStream(getUrl(query, Format.GEOJSON));
			events = parseJsonEventCollection(result);
		} finally {
			result.close();
		}

		return events;
	}

	/**
	 * Parse the response from event web service into an array of JSONEvent
	 * objects.
	 *
	 * @param input
	 *          input stream response from event web service.
	 * @return list of parsed events
	 * @throws Exception
	 *           if format is unexpected.
	 */
	protected List<JsonEvent> parseJsonEventCollection(final InputStream input)
			throws Exception {
		JSONParser parser = new JSONParser();

		// parse feature collection into objects
		JSONObject feed = JsonUtil.getJsonObject(parser
				.parse(new InputStreamReader(input)));
		if (feed == null) {
			throw new Exception("Expected feed object");
		}

		// check feed type
		String type = JsonUtil.getString(feed.get("type"));
		if (type == null) {
			throw new Exception("Expected geojson type");
		}

		ArrayList<JsonEvent> events = new ArrayList<JsonEvent>();

		if (type.equals("Feature")) {
			// detail feed with one event

			events.add(new JsonEvent(feed));
		} else if (type.equals("FeatureCollection")) {
			// summary feed with many events

			JSONArray features = JsonUtil.getJsonArray(feed.get("features"));
			if (features == null) {
				throw new Exception("Expected features");
			}

			// parse features into events
			Iterator<?> iter = features.iterator();
			while (iter.hasNext()) {
				JSONObject next = JsonUtil.getJsonObject(iter.next());
				if (next == null) {
					throw new Exception("Expected feature");
				}
				events.add(new JsonEvent(next));
			}
		}

		return events;
	}

}
