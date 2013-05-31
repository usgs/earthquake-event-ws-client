package gov.usgs.earthquake.event;

import java.math.BigDecimal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Utility class for (safely) converting JSONSimple Objects to their types.
 */
public class JSONUtil {

	/**
	 * Convert Object to JSONObject.
	 * 
	 * @param o
	 *            object to convert.
	 * @return JSONObject, or null if not JSONObject.
	 */
	public static JSONObject getJSONObject(final Object o) {
		if (o instanceof JSONObject) {
			return (JSONObject) o;
		}
		return null;
	}

	/**
	 * Convert Object to JSONArray.
	 * 
	 * @param o
	 *            object to convert.
	 * @return JSONArray, or null if not JSONArray.
	 */
	public static JSONArray getJSONArray(final Object o) {
		if (o instanceof JSONArray) {
			return (JSONArray) o;
		}
		return null;
	}

	/**
	 * Convert Object to BigDecimal.
	 * 
	 * @param o
	 *            object to convert.
	 * @return BigDecimal, or null if not BigDecimal.
	 */
	public static BigDecimal getBigDecimal(final Object o) {
		if (o instanceof Number) {
			return new BigDecimal(((Number) o).toString());
		}
		return null;
	}

	/**
	 * Convert Object to String.
	 * 
	 * @param o
	 *            object to convert.
	 * @return String, or null if not String.
	 */
	public static String getString(final Object o) {
		if (o instanceof String) {
			return (String) o;
		}
		return null;
	}

	/**
	 * Convert Object to Long.
	 * 
	 * @param o
	 *            object to convert.
	 * @return Long, or null if not Long.
	 */
	public static Long getLong(final Object o) {
		if (o instanceof Long) {
			return (Long) o;
		}
		return null;
	}

}
