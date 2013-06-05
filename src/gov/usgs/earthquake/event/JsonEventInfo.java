package gov.usgs.earthquake.event;

import gov.usgs.earthquake.event.JsonEvent;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Retrieves info from a JsonEvent
 */
public class JsonEventInfo implements EventInfo {

	private JsonEvent jsonEvent;

	// comparison and difference from reference event
	private EventComparison comparison;
	private Double distance;

	/*
	 * @param JSONEvent event
	 */
	public JsonEventInfo(JsonEvent event) {
		this.jsonEvent = event;
		this.comparison = null;
		this.distance = null;
	}

	@Override
	public BigDecimal getMagnitude() {
		return jsonEvent.getMag();
	}

	@Override
	public BigDecimal getDepth() {
		return jsonEvent.getDepth();
	}

	@Override
	public BigDecimal getLatitude() {
		return jsonEvent.getLatitude();
	}

	@Override
	public BigDecimal getLongitude() {
		return jsonEvent.getLongitude();
	}

	@Override
	public Date getTime() {
		return jsonEvent.getTime();
	}

	@Override
	public EventId getEventId() {
		return jsonEvent.getEventId();
	}

	public JsonEvent getEvent() {
		return jsonEvent;
	}

	public EventComparison getEventComparison() {
		return comparison;
	}

	public void setEventComparison(final EventComparison comparison) {
		this.comparison = comparison;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(final Double distance) {
		this.distance = distance;
	}

}