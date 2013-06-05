package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

public class DefaultEventInfo implements EventInfo {

	private EventId eventId;
	private Date time;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal depth;
	private BigDecimal magnitude;

	public DefaultEventInfo() {
		this(null, null, null, null, null, null);
	}

	public DefaultEventInfo(final EventId eventId, final Date time,
			final BigDecimal latitude, final BigDecimal longitude,
			final BigDecimal depth, final BigDecimal magnitude) {
		this.eventId = eventId;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.magnitude = magnitude;
	}

	/**
	 * @return the eventId
	 */
	public EventId getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(EventId eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the latitude
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the depth
	 */
	public BigDecimal getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(BigDecimal depth) {
		this.depth = depth;
	}

	/**
	 * @return the magnitude
	 */
	public BigDecimal getMagnitude() {
		return magnitude;
	}

	/**
	 * @param magnitude
	 *            the magnitude to set
	 */
	public void setMagnitude(BigDecimal magnitude) {
		this.magnitude = magnitude;
	}

}
