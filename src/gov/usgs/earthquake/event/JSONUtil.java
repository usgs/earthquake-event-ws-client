package gov.usgs.earthquake.event;

import java.math.BigDecimal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONUtil {

	public static JSONObject getJSONObject(final Object o) {
		if (o instanceof JSONObject) {
			return (JSONObject) o;
		}
		return null;
	}

	public static JSONArray getJSONArray(final Object o) {
		if (o instanceof JSONArray) {
			return (JSONArray) o;
		}
		return null;
	}

	public static BigDecimal getBigDecimal(final Object o) {
		if (o instanceof Number) {
			return new BigDecimal(((Number) o).toString());
		}
		return null;
	}

	public static String getString(final Object o) {
		if (o instanceof String) {
			return (String) o;
		}
		return null;
	}

	public static Long getLong(final Object o) {
		if (o instanceof Long) {
			return (Long) o;
		}
		return null;
	}

}
