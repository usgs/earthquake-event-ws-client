package gov.usgs.earthquake.event;

public enum Format {

	QUAKEML		("quakeml"),
	CSV			("csv"),
	GEOJSON		("geojson"),
	KML			("kml");

	private String _name;

	Format(String name) {
		_name = name;
	}

	public String toString() {
		return _name;
	}
}