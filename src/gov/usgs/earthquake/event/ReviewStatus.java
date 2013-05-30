package gov.usgs.earthquake.event;

public enum ReviewStatus {

	ALL			("all"),
	AUTOMATIC	("automatic"),
	REVIEWED	("reviewed");

	private String _name;

	ReviewStatus(String name) {
		_name = name;
	}

	public String toString() {
		return _name;
	}
}