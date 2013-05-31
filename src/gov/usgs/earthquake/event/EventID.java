package gov.usgs.earthquake.event;

/**
 * Event ID class.
 */
public class EventID {

	private String network = null;
	private String code = null;

	/**
	 * Construct a new EventID from a network and code.
	 *
	 * @param [String] network
	 * @param [String] code
	 */
	public EventID(String network, String code) {
		this.network = network;
		this.code = code;
	}

	public String toString() {
		return this.network + this.code;
	}
	
	@Override
	public boolean equals(Object in) {
		if (in instanceof EventID) {
			return (
				((EventID) in).getNetwork().equalsIgnoreCase(network) &&
				((EventID) in).getCode().equalsIgnoreCase(code)
			);
		} else {
			return false;
		}
	}

	// Getters
	public String getNetwork()	{return network;}
	public String getCode()		{return code;}
}
