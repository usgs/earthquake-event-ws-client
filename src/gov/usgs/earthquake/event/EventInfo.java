package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

/*
 * Interface for retrieving event parameters.
 */
public interface EventInfo {

	/*
	 * Gives the event id.
	 * @return EventID
	 */
	public EventId getEventId();

	/*
	 * Gives the event's preferred magnitude.
	 * @return BigDecimal
	 */
	public BigDecimal getMagnitude();

	/*
	 * Gives the event depth in meters.
	 * @return BigDecimal
	 */
	public BigDecimal getDepth();

	/*
	 * Gives the event latitude.
	 * @return BigDecimal
	 */
	public BigDecimal getLatitude();

	/*
	 * Gives the event longitude.
	 * @return BigDecimal
	 */
	public BigDecimal getLongitude();

	/*
	 * Gives the event date.
	 * @return Date
	 */
	public Date getTime();

}