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

	public static final long DEFAULT_TIME_DIFFERENCE = 16000;
	public static final BigDecimal DEFAULT_LOCATION_DIFFERENCE = new BigDecimal(
			"100");

	/** event web service for remote lookup. */
	private EventWebService service;
	private long timeDifference;
	private BigDecimal locationDifference;

	public EventIDAssociator() throws MalformedURLException {
		this(new EventWebService(new URL(
				"http://comcat.cr.usgs.gov/fdsnws/event/1/")), DEFAULT_TIME_DIFFERENCE,
				DEFAULT_LOCATION_DIFFERENCE);
	}

	/**
	 * Create a new EventIDAssociator.
	 * 
	 * @param service
	 *          the event webservice to use.
	 */
	public EventIDAssociator(final EventWebService service,
			final long timeDifference, final BigDecimal locationDifference) {
		this.service = service;
		this.timeDifference = timeDifference;
		this.locationDifference = locationDifference;
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
	public List<JsonEvent> getNearbyEvents(final BigDecimal latitude,
			final BigDecimal longitude, final Date time, final String network)
			throws Exception {
		EventQuery query = new EventQuery();

		// from a specific source (when not null)
		query.setCatalog(network);
		// time window
		query.setStartTime(new Date(time.getTime() - timeDifference));
		query.setEndTime(new Date(time.getTime() + timeDifference));
		// location window
		query.setLatitude(latitude);
		query.setLongitude(longitude);
		// service takes radius in degrees
		query.setMaxRadius(locationDifference.divide(KILOMETERS_PER_DEGREE,
				MathContext.DECIMAL32));

		return service.getEvents(query);
	}

	/**
	 * Search for nearby events using an EventInfo object.
	 * 
	 * @param info
	 *          used for latitude, longitude, time.
	 * @param network
	 *          search for events from a specific network.
	 * @return
	 * @throws Exception
	 */
	public List<JsonEvent> getNearbyEvents(final EventInfo info,
			final String network) throws Exception {
		return getNearbyEvents(info.getLatitude(), info.getLongitude(),
				info.getTime(), network);
	}

	/**
	 * Sort events based on "distance" from reference event.
	 * 
	 * @param events
	 *          events to sort.
	 * @param referenceEvent
	 *          event used for comparison.
	 * @param sanityRules
	 *          rules used for "distance".
	 * @return sorted list of JsonEventInfo objects, with EventComparison and
	 *         Distance filled in.
	 */
	public List<JsonEventInfo> sortEvents(final List<JsonEvent> events,
			final EventInfo referenceEvent, EventSanityCheck sanityRules) {
		TreeSet<JsonEventInfo> sortedEvents = new TreeSet<JsonEventInfo>(
				new JsonEventInfoComparator(sanityRules, referenceEvent));

		Iterator<JsonEvent> iter = events.iterator();
		while (iter.hasNext()) {
			JsonEventInfo info = new JsonEventInfo(iter.next());
			info.setEventComparison(new EventComparison(referenceEvent, info));
			info.setDistance(sanityRules.getDistance(info.getEventComparison()));
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
		return sortEvents(getNearbyEvents(info, network), info,
				new EventSanityCheck());
	}

	public static final int EXIT_EVENT_NOT_FOUND = 1;
	public static final int EXIT_EVENT_NOT_SANE = 2;
	public static final int EXIT_MULTIPLE_EVENTS_FOUND = 4;
	public static final int EXIT_USAGE = 255;

	public static final String TIME_ARGUMENT = "--time=";
	public static final String LATITUDE_ARGUMENT = "--latitude=";
	public static final String LONGITUDE_ARGUMENT = "--longitude=";
	public static final String DEPTH_ARGUMENT = "--depth=";
	public static final String MAGNITUDE_ARGUMENT = "--magnitude=";
	public static final String NETWORK_ARGUMENT = "--network=";

	@SuppressWarnings("unchecked")
	public static void main(final String[] args) throws Exception {
		// parse search arguments
		Date time = null;
		BigDecimal latitude = null;
		BigDecimal longitude = null;
		BigDecimal depth = null;
		BigDecimal magnitude = null;
		String network = null;
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
			}
		}

		// display usage
		if (time == null || latitude == null || longitude == null) {
			System.err.println("Usage: java EventIDAssociator"
					+ " --time=ISO8601 --latitude=LAT --longitude=LON"
					+ " [--depth=DEPTH] [--magnitude=MAG] [--network=NET]");
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

		EventIDAssociator associator = new EventIDAssociator();
		EventSanityCheck sanityCheck = new EventSanityCheck();

		// search for nearby events
		EventInfo referenceEvent = new DefaultEventInfo(null, time, latitude,
				longitude, depth, magnitude);
		List<JsonEventInfo> events = associator.getSortedNearbyEvents(
				referenceEvent, network);

		// build json output
		JSONObject jsonMetadata = new JSONObject();
		jsonMetadata.put("count", events.size());
		jsonMetadata.put("latitude", latitude);
		jsonMetadata.put("longitude", longitude);
		jsonMetadata.put("network", network);
		jsonMetadata.put("magnitude", magnitude);
		jsonMetadata.put("depth", depth);
		jsonMetadata.put("time", ISO8601.format(time));
		JSONArray jsonEvents = new JSONArray();
		Iterator<JsonEventInfo> iter = events.iterator();
		while (iter.hasNext()) {
			JsonEventInfo next = iter.next();

			JSONObject jsonEvent = new JSONObject(next.getEvent());

			JSONObject diff = new JSONObject();
			EventComparison comparison = next.getEventComparison();
			diff.put("magnitude", comparison.getMagnitudeDifference());
			diff.put("location", comparison.getLocationDifference());
			diff.put("depth", comparison.getDepthDifference());
			diff.put("time", comparison.getTimeDifference());
			diff.put("errorMessage", sanityCheck.isMatch(comparison));
			diff.put("variance", next.getDistance());
			jsonEvent.put("difference", diff);

			jsonEvents.add(jsonEvent);
		}

		JSONObject output = new JSONObject();
		// like a geojson feature collection
		output.put("type", "FeatureCollection");
		output.put("metadata", jsonMetadata);
		output.put("features", jsonEvents);
		System.out.println(output.toJSONString().replace("\\/", "/"));

		// output exit code
		if (events.size() == 0) {
			System.exit(EXIT_EVENT_NOT_FOUND);
		} else if (events.size() > 1) {
			System.exit(EXIT_MULTIPLE_EVENTS_FOUND);
		} else {
			// one event, check if sane
			if (sanityCheck.isMatch(events.get(0).getEventComparison()) != null) {
				System.exit(EXIT_EVENT_NOT_SANE);
			}
		}
	}

}
