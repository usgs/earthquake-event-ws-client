package gov.usgs.earthquake.event;

import java.math.BigDecimal;

/**
 * Compare 2 versions of the same event to see if it has changed significantly
 *  and requires human inspection.
 */
public class EventSanityCheck {

	// Distance difference threshold in kilometers
	public static final BigDecimal DISTANCE_THRESHOLD = new BigDecimal("30");
	private final BigDecimal distanceThreshold;

	// Magnitude difference threshold
	public static final BigDecimal MAGNITUDE_THRESHOLD = new BigDecimal(".4");
	private final BigDecimal magnitudeThreshold;

	// Depth difference threshold in kilometers
	public static final BigDecimal DEPTH_THRESHOLD = new BigDecimal("30");
	private final BigDecimal depthThreshold;

	// Time difference threshold in seconds
	public static final BigDecimal TIME_THRESHOLD = new BigDecimal("5");
	private final BigDecimal timeThreshold;

	/**
	 * Default constructor with values as follows:
	 *
	 * <pre>
	 * TIME_THRESHOLD = 5s
	 * DISTANCE_THRESHOLD = 30km
	 * DEPTH_THRESHOLD = 30km
	 * MAGNITUDE_THRESHOLD = .4
	 * </pre>
	 */
	public EventSanityCheck() {
		this(TIME_THRESHOLD, DISTANCE_THRESHOLD, DEPTH_THRESHOLD,
				MAGNITUDE_THRESHOLD);
	}

	/**
	 * Constructor with configurable thresholds.
	 *
	 * @param timeThreshold
	 * @param distanceThreshold
	 * @param depthThreshold
	 * @param magnitudeThreshold
	 */
	public EventSanityCheck(BigDecimal timeThreshold,
			BigDecimal distanceThreshold, BigDecimal depthThreshold,
			BigDecimal magnitudeThreshold) {
		this.timeThreshold = timeThreshold;
		this.distanceThreshold = distanceThreshold;
		this.depthThreshold = depthThreshold;
		this.magnitudeThreshold = magnitudeThreshold;
	}

	/**
	 * Compares two events and returns a string of errors if they are not
	 * similar enough or null otherwise.
	 *
	 * @param c comparison to test.
	 * @return error message as string if out of range, null otherwise.
	 */
	public String isMatch(EventComparison c) {

		StringBuffer errors = new StringBuffer();

		// Check distance
		if ((distanceThreshold != null && c.getLocationDifference() != null)
				&& (c.getLocationDifference().compareTo(distanceThreshold) == 1)) {
			errors.append("Distance of " + c.getLocationDifference()
					+ " kilometers exceeds threshold of " + distanceThreshold
					+ ".\n");
		}

		// Check magnitude
		if ((magnitudeThreshold != null && c.getMagnitudeDifference() != null)
				&& (c.getMagnitudeDifference().compareTo(magnitudeThreshold) == 1)) {
			errors.append("Magnitude difference of "
					+ c.getMagnitudeDifference() + " exceeded threshold of "
					+ magnitudeThreshold + ".\n");
		}

		// Check depth
		if ((depthThreshold != null && c.getDepthDifference() != null)
				&& (c.getDepthDifference().compareTo(depthThreshold) == 1)) {
			errors.append("Depth difference of " + c.getDepthDifference()
					+ " kilometers exceeds threshold of " + depthThreshold
					+ ".\n");
		}

		// Check time
		if ((timeThreshold != null && c.getTimeDifference() != null)
				&& (c.getTimeDifference().compareTo(timeThreshold) == 1)) {
			errors.append("Time difference of " + c.getTimeDifference()
					+ " seconds exceeds threshold of " + timeThreshold + ".\n");
		}

		if (errors.length() == 0) {
			return null;
		} else {
			return errors.toString().trim();
		}
	}

	/**
	 * Calculate the "euclidean" distance between 2 events.
	 *
	 * <pre>
	 * sqrt((distance / distanceThreshold) &circ; 2
	 * 		+ (magnitudeDifference / magnitudeThreshold) &circ; 2
	 * 		+ (depthDifference / depthThreshold) &circ; 2
	 * 		+ (timeDifference / timeThreshold) &circ; 2)
	 * </pre>
	 *
	 * @param c
	 * @return
	 */
	public double getDistance(EventComparison c) {
		double diff = 0;

		if (c.getLocationDifference() != null && distanceThreshold != null) {
			double distance = c.getLocationDifference().doubleValue()
					/ distanceThreshold.doubleValue();
			diff += distance * distance;
		}

		if (c.getTimeDifference() != null && timeThreshold != null) {
			double distance = c.getTimeDifference().doubleValue()
					/ timeThreshold.doubleValue();
			diff += distance * distance;
		}

		if (c.getMagnitudeDifference() != null && magnitudeThreshold != null) {
			double distance = c.getMagnitudeDifference().doubleValue()
					/ magnitudeThreshold.doubleValue();
			diff += distance * distance;
		}

		if (c.getDepthDifference() != null && depthThreshold != null) {
			double distance = c.getDepthDifference().doubleValue()
					/ depthThreshold.doubleValue();
			diff += distance * distance;
		}

		return Math.sqrt(diff);
	}

}