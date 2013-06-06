package gov.usgs.earthquake.event;

import gov.usgs.earthquake.event.DefaultEventInfo;
import gov.usgs.earthquake.event.EventComparison;
import gov.usgs.earthquake.event.EventInfo;
import gov.usgs.earthquake.event.JsonEventInfoComparator;
import gov.usgs.earthquake.event.EventQuery;
import gov.usgs.earthquake.event.EventSanityCheck;
import gov.usgs.earthquake.event.EventWebService;
import gov.usgs.earthquake.event.JsonEvent;
import gov.usgs.earthquake.event.JsonEventInfo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Looks for nearby events in the default association window.
 * 
 * Will be used by ConvertEDR for determining event associations.
 */
public class EventIDAssociator {

	public static final BigDecimal KILOMETERS_PER_DEGREE = new BigDecimal("111.2");

	public static final BigDecimal DEFAULT_TIME_DIFFERENCE = new BigDecimal("16");
	public static final BigDecimal DEFAULT_LOCATION_DIFFERENCE = new BigDecimal(
			"100");
	public static final BigDecimal DEFAULT_MAGNITUDE_DIFFERENCE = null;
	public static final BigDecimal DEFAULT_DEPTH_DIFFERENCE = null;

	/** event web service for remote lookup. */
	private EventWebService service;
	private EventComparison nearbyCriteria;
	private EventSanityCheck sanityCheck;

	public EventIDAssociator() throws MalformedURLException {
		this(new EventWebService(new URL(
				"http://comcat.cr.usgs.gov/fdsnws/event/1/")), new EventComparison(
				DEFAULT_TIME_DIFFERENCE, DEFAULT_LOCATION_DIFFERENCE,
				DEFAULT_DEPTH_DIFFERENCE, DEFAULT_MAGNITUDE_DIFFERENCE),
				new EventSanityCheck());
	}

	/**
	 * Create a new EventIDAssociator.
	 * 
	 * @param service
	 *          the event webservice to use.
	 */
	public EventIDAssociator(final EventWebService service,
			final EventComparison nearbyCriteria, final EventSanityCheck sanityCheck) {
		this.service = service;
		this.nearbyCriteria = nearbyCriteria;
		this.sanityCheck = sanityCheck;
	}

	/**
	 * Search for nearby events using latitude, longitude, and time.
	 * 
	 * @param latitude
	 *          the latitude.
	 * @param longitude
	 *          the longitude.
	 * @param time
	 *          the time.
	 * @return a list of nearby events.
	 * @throws Exception
	 */
	public List<JsonEvent> getNearbyEvents(final Date time,
			final BigDecimal latitude, final BigDecimal longitude,
			final BigDecimal depth, final BigDecimal magnitude, final String network)
			throws Exception {
		return getNearbyEvents(new DefaultEventInfo(null, time, latitude,
				longitude, depth, magnitude), network);
	}

	/**
	 * Search for nearby events using an EventInfo object.
	 * 
	 * @param info
	 *          used for latitude, longitude, time.
	 * @param network
	 *          search for events from a specific network, null for all networks.
	 * @return
	 * @throws Exception
	 */
	public List<JsonEvent> getNearbyEvents(final EventInfo info,
			final String network) throws Exception {
		Date time = info.getTime();
		BigDecimal latitude = info.getLatitude();
		BigDecimal longitude = info.getLongitude();
		BigDecimal depth = info.getDepth();
		BigDecimal magnitude = info.getMagnitude();

		boolean anySet = false;
		EventQuery query = new EventQuery();

		// from a specific source (when not null)
		query.setCatalog(network);

		if (time != null && nearbyCriteria.getTimeDifference() != null) {
			long milliseconds = nearbyCriteria.getTimeDifference()
					.multiply(new BigDecimal("1000")).longValue();
			// time window
			query.setStartTime(new Date(time.getTime() - milliseconds));
			query.setEndTime(new Date(time.getTime() + milliseconds));
			anySet = true;
		}

		if (latitude != null && longitude != null
				&& nearbyCriteria.getLocationDifference() != null) {
			// location window
			query.setLatitude(latitude);
			query.setLongitude(longitude);
			// service takes radius in degrees
			query.setMaxRadius(nearbyCriteria.getLocationDifference()
					.divide(KILOMETERS_PER_DEGREE, MathContext.DECIMAL32));
			anySet = true;
		}

		if (depth != null && nearbyCriteria.getDepthDifference() != null) {
			query.setMinDepth(info.getDepth().subtract(
					nearbyCriteria.getDepthDifference()));
			query.setMaxDepth(info.getDepth().add(
					nearbyCriteria.getDepthDifference()));
			anySet = true;
		}

		if (magnitude != null && nearbyCriteria.getMagnitudeDifference() != null) {
			query.setMinMagnitude(info.getMagnitude().subtract(
					nearbyCriteria.getMagnitudeDifference()));
			query.setMaxMagnitude(info.getMagnitude().add(
					nearbyCriteria.getMagnitudeDifference()));
			anySet = true;
		}

		if (!anySet) {
			throw new IllegalArgumentException("no parameters set");
		}
		return service.getEvents(query);
	}

	/**
	 * Sort events based on "distance" from reference event.
	 * 
	 * @param events
	 *          events to sort.
	 * @param referenceEvent
	 *          event used for comparison.
	 * @return sorted list of JsonEventInfo objects, with EventComparison and
	 *         Distance filled in.
	 */
	public List<JsonEventInfo> sortEvents(final List<JsonEvent> events,
			final EventInfo referenceEvent) {
		TreeSet<JsonEventInfo> sortedEvents = new TreeSet<JsonEventInfo>(
				new JsonEventInfoComparator(sanityCheck, referenceEvent));

		Iterator<JsonEvent> iter = events.iterator();
		while (iter.hasNext()) {
			JsonEventInfo info = new JsonEventInfo(iter.next());
			sortedEvents.add(info);
		}

		return new ArrayList<JsonEventInfo>(sortedEvents);
	}

	/**
	 * Get nearby events, and sort them based on distance (closest first) from the
	 * reference event.
	 * 
	 * @param info
	 *          the reference event information.
	 * @param network
	 *          the network to search, or null for all.
	 * @return a sorted list of nearby events, closest first.
	 * @throws Exception
	 */
	public List<JsonEventInfo> getSortedNearbyEvents(final EventInfo info,
			final String network) throws Exception {
		return sortEvents(getNearbyEvents(info, network), info);
	}

	public static final int EXIT_EVENT_NOT_FOUND = 1;
	public static final int EXIT_EVENT_NOT_SANE = 2;
	public static final int EXIT_MULTIPLE_EVENTS_FOUND = 4;
	public static final int EXIT_USAGE = 255;

	public static final String SERVICE_URL_ARGUMENT = "--url=";
	public static final String DEFAULT_SERVICE_URL = "http://comcat.cr.usgs.gov/fdsnws/event/1/";

	public static final String TIME_ARGUMENT = "--time=";
	public static final String LATITUDE_ARGUMENT = "--latitude=";
	public static final String LONGITUDE_ARGUMENT = "--longitude=";
	public static final String DEPTH_ARGUMENT = "--depth=";
	public static final String MAGNITUDE_ARGUMENT = "--magnitude=";
	public static final String NETWORK_ARGUMENT = "--network=";

	public static void main(final String[] args) throws Exception {
		// parse search arguments
		Date time = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		BigDecimal depth = null;
		BigDecimal magnitude = null;
		String network = null;
		URL serviceUrl = new URL(DEFAULT_SERVICE_URL);

		BigDecimal timeDifference = DEFAULT_TIME_DIFFERENCE;
		BigDecimal locationDifference = DEFAULT_LOCATION_DIFFERENCE;
		BigDecimal depthDifference = DEFAULT_DEPTH_DIFFERENCE;
		BigDecimal magnitudeDifference = DEFAULT_MAGNITUDE_DIFFERENCE;

		for (String arg : args) {
			if (arg.startsWith(TIME_ARGUMENT)) {
				time = ISO8601.parse(arg.replace(TIME_ARGUMENT, ""));
			} else if (arg.startsWith(LATITUDE_ARGUMENT)) {
				latitude = new BigDecimal(arg.replace(LATITUDE_ARGUMENT, ""));
			} else if (arg.startsWith(LONGITUDE_ARGUMENT)) {
				longitude = new BigDecimal(arg.replace(LONGITUDE_ARGUMENT, ""));
			} else if (arg.startsWith(DEPTH_ARGUMENT)) {
				depth = new BigDecimal(arg.replace(DEPTH_ARGUMENT, ""));
			} else if (arg.startsWith(MAGNITUDE_ARGUMENT)) {
				magnitude = new BigDecimal(arg.replace(MAGNITUDE_ARGUMENT, ""));
			} else if (arg.startsWith(NETWORK_ARGUMENT)) {
				network = arg.replace(NETWORK_ARGUMENT, "");
			} else if (arg.startsWith(SERVICE_URL_ARGUMENT)) {
				serviceUrl = new URL(arg.replace(SERVICE_URL_ARGUMENT, ""));
			}
		}

		// display usage
		if (time == null || latitude == null || longitude == null) {
			System.err.println("Usage: java EventIDAssociator"
					+ " --time=ISO8601 --latitude=LAT --longitude=LON"
					+ " [--depth=DEPTH] [--magnitude=MAG] [--network=NET]"
					+ " [--url=SERVICE_URL]");
			System.err.println("Exit values:");
			System.err.println("\t" + 0 + " - one event found");
			System.err.println("\t" + EXIT_EVENT_NOT_FOUND + " - no events found");
			System.err.println("\t" + EXIT_EVENT_NOT_SANE
					+ " - event significantly different");
			System.err.println("\t" + EXIT_MULTIPLE_EVENTS_FOUND
					+ " - multiple events found");
			System.err.println("\t" + EXIT_USAGE + " - usage error");
			System.exit(EXIT_USAGE);
		}

		EventIDAssociator associator = new EventIDAssociator(new EventWebService(
				serviceUrl), new EventComparison(timeDifference, locationDifference,
				depthDifference, magnitudeDifference), new EventSanityCheck());

		// search for nearby events
		EventInfo referenceEvent = new DefaultEventInfo(null, time, latitude,
				longitude, depth, magnitude);
		List<JsonEventInfo> events = associator.getSortedNearbyEvents(
				referenceEvent, network);

		// output results
		System.out
				.println(associator.formatOutput(referenceEvent, network, events));

		// output exit code
		if (events.size() == 0) {
			System.exit(EXIT_EVENT_NOT_FOUND);
		} else if (events.size() > 1) {
			System.exit(EXIT_MULTIPLE_EVENTS_FOUND);
		} else {
			// one event, check if sane
			if (associator.getEventSanityCheck().isMatch(
					events.get(0).getEventComparison()) != null) {
				System.exit(EXIT_EVENT_NOT_SANE);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String formatOutput(final EventInfo referenceEvent,
			final String network, final List<JsonEventInfo> events) {
		// build json output
		JSONObject jsonMetadata = new JSONObject();
		jsonMetadata.put("count", events.size());
		jsonMetadata.put("time", ISO8601.format(referenceEvent.getTime()));
		jsonMetadata.put("latitude", referenceEvent.getLatitude());
		jsonMetadata.put("longitude", referenceEvent.getLongitude());
		jsonMetadata.put("depth", referenceEvent.getDepth());
		jsonMetadata.put("magnitude", referenceEvent.getMagnitude());
		jsonMetadata.put("network", network);

		JSONArray jsonEvents = new JSONArray();
		Iterator<JsonEventInfo> iter = events.iterator();
		while (iter.hasNext()) {
			JsonEventInfo next = iter.next();

			JSONObject diff = new JSONObject();
			EventComparison comparison = next.getEventComparison();
			diff.put("magnitude", comparison.getMagnitudeDifference());
			diff.put("location", comparison.getLocationDifference());
			diff.put("depth", comparison.getDepthDifference());
			diff.put("time", comparison.getTimeDifference());
			diff.put("errorMessage", sanityCheck.isMatch(comparison));
			diff.put("variance", next.getDistance());

			JSONObject jsonEvent = new JSONObject(next.getEvent());
			jsonEvent.put("difference", diff);

			jsonEvents.add(jsonEvent);
		}

		JSONObject output = new JSONObject();
		// like a geojson feature collection
		output.put("type", "FeatureCollection");
		output.put("metadata", jsonMetadata);
		output.put("features", jsonEvents);
		return output.toJSONString().replace("\\/", "/");
	}

	public EventWebService getEventWebService() {
		return service;
	}
	public void setEventWebService(final EventWebService service) {
		this.service = service;
	}

	public EventComparison getNearbyCriteria() {
		return nearbyCriteria;
	}
	public void setNearbyCriteria(final EventComparison nearbyCriteria) {
		this.nearbyCriteria = nearbyCriteria;
	}

	public EventSanityCheck getEventSanityCheck() {
		return sanityCheck;
	}
	public void setEventSanityCheck(final EventSanityCheck sanityCheck) {
		this.sanityCheck = sanityCheck;
	}

}
