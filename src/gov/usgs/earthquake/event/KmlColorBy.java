package gov.usgs.earthquake.event;

public enum KmlColorBy {

	AGE		("age"),
	DEPTH	("depth");

	private String _name;

	KmlColorBy(String name) {
		_name = name;
	}

	public String toString() {
		return _name;
	}
}