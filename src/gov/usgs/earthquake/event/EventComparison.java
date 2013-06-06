package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Comparison of 2 events.
 */
public class EventComparison {

	/** location difference in kilometers. */
	private final BigDecimal locationDifference;
	/** magnitude difference. */
	private final BigDecimal magnitudeDifference;
	/** depth difference in kilometers. */
	private final BigDecimal depthDifference;
	/** time difference in seconds. */
	private final BigDecimal timeDifference;

	public EventComparison(EventInfo a, EventInfo b) {
		MathContext roundContext = new MathContext(5);

		BigDecimal distance = getDistance(a, b);
		if (distance == null) {
			locationDifference = null;
		} else {
			locationDifference = distance.round(roundContext);
		}
		if (a.getMagnitude() == null || b.getMagnitude() == null) {
			magnitudeDifference = null;
		} else {
			magnitudeDifference = a.getMagnitude().subtract(b.getMagnitude()).abs()
					.round(roundContext);
		}
		if (a.getDepth() == null || b.getDepth() == null) {
			depthDifference = null;
		} else {
			depthDifference = a.getDepth().subtract(b.getDepth()).abs()
					.round(roundContext);
		}
		if (a.getTime() == null || b.getTime() == null) {
			timeDifference = null;
		} else {
			timeDifference = new BigDecimal(a.getTime().getTime()
					- b.getTime().getTime()).abs().divide(new BigDecimal("1000"))
					.round(roundContext);
		}
	}

	public EventComparison(final BigDecimal timeDifference,
			final BigDecimal locationDifference,
			final BigDecimal depthDifference,
			final BigDecimal magnitudeDifference) {
		this.timeDifference = timeDifference;
		this.locationDifference = locationDifference;
		this.depthDifference = depthDifference;
		this.magnitudeDifference = magnitudeDifference;
	}

	public BigDecimal getLocationDifference() {
		return locationDifference;
	}

	public BigDecimal getMagnitudeDifference() {
		return magnitudeDifference;
	}

	public BigDecimal getDepthDifference() {
		return depthDifference;
	}

	public BigDecimal getTimeDifference() {
		return timeDifference;
	}

	/**
	 * Compares two events and returns their distance in kilometers.
	 * 
	 * @param a
	 *          first event
	 * @param b
	 *          second event
	 * @return distance in kilometers, or null if one event doesn't have location.
	 */
	public static BigDecimal getDistance(final EventInfo a, final EventInfo b) {
		try {
			return new BigDecimal(getDistance(
					a.getLongitude().doubleValue(), b.getLongitude().doubleValue(),
					a.getLatitude().doubleValue(), b.getLatitude().doubleValue()));
		} catch (NullPointerException npe) {
			return null;
		}
	}

	/**
	 * Utilizes the haversine formula to calculate great circle distance.
	 * http://www.movable-type.co.uk/scripts/gis-faq-5.1.html
	 * 
	 * @param lona
	 *          First longitude in degrees
	 * @param lonb
	 *          Second Longitude in degrees
	 * @param lata
	 *          First latitude in degrees
	 * @param latb
	 *          Second latitude in degrees
	 * @return distance in kilometers
	 */
	public static double getDistance(double lona, double lonb, double lata,
			double latb) {

		// Radius of the earth in kilometers
		double R = 6367;

		lona = Math.toRadians(lona);
		lonb = Math.toRadians(lonb);
		lata = Math.toRadians(lata);
		latb = Math.toRadians(latb);

		// latitude and longitude deltas
		double dlon = lonb - lona;
		double dlat = latb - lata;

		// Haversine Formula (from R.W. Sinnott, "Virtues of the Haversine", Sky
		// and Telescope, vol. 68, no. 2, 1984, p. 159):
		double A = Math.pow(Math.sin(dlat / 2), 2);
		A += Math.cos(lata) * Math.cos(latb) * Math.pow(Math.sin(dlon / 2), 2);
		return R * 2 * Math.asin(Math.min(1, Math.sqrt(A)));
	}

}
