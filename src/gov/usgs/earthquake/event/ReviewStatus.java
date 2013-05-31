package gov.usgs.earthquake.event;

public enum ReviewStatus {

	ALL			("all"),
	AUTOMATIC	("AUTOMATIC"),
	PUBLISHED	("PUBLISHED"),
	REVIEWED	("REVIEWED");

	private String _name;

	ReviewStatus(String name) {
		_name = name;
	}

	public String toString() {
		return _name;
	}
}