package gov.usgs.earthquake.event;

/**
 * Event ID class.
 *
 * Represents a unique identifier assigned by a network.
 * An Event may have multiple IDs, usually no more than one per network.
 */
public class EventId {

	private String network = null;
	private String code = null;

	/**
	 * Construct a new EventID from a network and code.
	 *
	 * @param network
	 *          the event network.
	 * @param code
	 *          the code assigned by the event network.
	 * @throws IllegalArgumentException
	 *           if network or code are null.
	 */
	public EventId(String network, String code) throws IllegalArgumentException {
		if (network == null || code == null) {
			throw new IllegalArgumentException("EventId requires network and code");
		}
		this.network = network;
		this.code = code;
	}

	public String toString() {
		return this.network + this.code;
	}

	@Override
	public boolean equals(Object in) {
		if (in instanceof EventId) {
			EventId inId = (EventId) in;
			return (network.equalsIgnoreCase(inId.getNetwork()) 
					&& code.equalsIgnoreCase(inId.getCode()));
		}
		return false;
	}

	// Getters
	public String getNetwork() {
		return network;
	}

	public String getCode() {
		return code;
	}

}
