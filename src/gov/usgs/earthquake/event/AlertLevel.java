package gov.usgs.earthquake.event;

public enum AlertLevel {
	ALL		("all"),
	GREEN	("green"),
	YELLOW	("yellow"),
	ORANGE	("orange"),
	RED		("red");

	private String _name;

	AlertLevel(String name) {
		_name = name;
	}

	public String toString() {
		return _name;
	}
}