package gov.usgs.earthquake.event;

import java.util.Comparator;

/**
 * Provides an ordering for events, based on "euclidean" distance from a
 * reference event.
 */
public class JsonEventInfoComparator implements Comparator<JsonEventInfo> {

	private EventSanityCheck sanityRules;
	private EventInfo referenceEvent;

	/**
	 * Construct a new EventInfoComparator.
	 * 
	 * @param sanityRules
	 *            determines "euclidean" distance.
	 * @param mainEvent
	 *            main event other events are compared agains.
	 */
	public JsonEventInfoComparator(EventSanityCheck sanityRules,
			EventInfo referenceEvent) {
		this.sanityRules = sanityRules;
		this.referenceEvent = referenceEvent;
	}

	/**
	 * Compare two events against the reference event.
	 */
	@Override
	public int compare(JsonEventInfo event1, JsonEventInfo event2) {
		// compute these once
		if (event1.getEventComparison() == null) {
			event1.setEventComparison(new EventComparison(referenceEvent, event1));
			event1.setDistance(sanityRules.getDistance(event1.getEventComparison()));
		}
		if (event2.getEventComparison() == null) {
			event2.setEventComparison(new EventComparison(referenceEvent, event2));
			event2.setDistance(sanityRules.getDistance(event2.getEventComparison()));
		}

		Double event1Distance = event1.getDistance();
		Double event2Distance = event2.getDistance();
		if (event1Distance > event2Distance) {
			// event1 closer than event2
			return -1;
		} else if (event1Distance < event2Distance) {
			// event2 closer than event1
			return 1;
		} else {
			// equal
			return 0;
		}
	}

}
