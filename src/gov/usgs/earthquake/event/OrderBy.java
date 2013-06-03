package gov.usgs.earthquake.event;

public enum OrderBy {

	TIME("time"),
	TIME_ASC("time-asc"),
	MAGNITUDE("magnitude"),
	MAGNITUDE_ASC("magnitude-asc");

	private String _name;

	OrderBy(String name) {
		_name = name;
	}

	public String toString() {
		return _name;
	}

}
