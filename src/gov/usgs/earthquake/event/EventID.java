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
	
	public Boolean equals(EventID in) {
		return (
			in.getNetwork().equals(network) &&
			in.getCode().equals(code)
		);
	}

	// Getters
	public String getNetwork()	{return network;}
	public String getCode()		{return code;}
}
