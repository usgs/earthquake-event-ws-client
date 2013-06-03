package gov.usgs.earthquake.event;

import java.math.BigDecimal;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JsonUtilTest {

	/**
	 * Test that this utility class is instantiable. The target class is entirely
	 * static, so no need to be instantiable, but test anyway.
	 */
	@Test
	public void testInstance () {
		JsonUtil util = new JsonUtil();
		Assert.assertNotNull("Can instantiate a JsonUtil instance.", util);
	}

	/**
	 * Test to ensure utility properly returns null when a non-JSONObject object
	 * is passed in. Conversely, it ought to properly return a true JSONObject
	 * when one is given.
	 */
	@Test
	public void testGetJsonObject () {
		JSONObject expected = new JSONObject();

		JSONObject actual = JsonUtil.getJsonObject(new Object());
		Assert.assertNull("Non JSONObject returns null.", actual);

		actual = JsonUtil.getJsonObject(expected);
		Assert.assertEquals("JSONObject properly returned.", expected, actual);
	}

	/**
	 * Test to ensure utility properly returns null when a non-JSONArray object
	 * is passed in. Conversely, it ought to properly return a true JSONArray
	 * when one is given.
	 */
	@Test
	public void testGetJsonArray () {
		JSONArray expected = new JSONArray();

		JSONArray actual = JsonUtil.getJsonArray(new Object());
		Assert.assertNull("Non JSONArray returns null.", actual);

		actual = JsonUtil.getJsonArray(expected);
		Assert.assertEquals("JSONArray properly returned.", expected, actual);
	}

		/**
	 * Test to ensure utility properly returns null when a non-BigDecimal object
	 * is passed in. Conversely, it ought to properly return a true BigDecimal
	 * when one is given.
	 */
	@Test
	public void testGetBigDecimal () {
		BigDecimal expected = new BigDecimal("0.0");

		BigDecimal actual = JsonUtil.getBigDecimal(new Object());
		Assert.assertNull("Non BigDecimal returns null.", actual);

		actual = JsonUtil.getBigDecimal(expected);
		Assert.assertEquals("BigDecimal properly returned.", expected, actual);
	}

	/**
	 * Test to ensure utility properly returns null when a non-String object
	 * is passed in. Conversely, it ought to properly return a true String
	 * when one is given.
	 */
	@Test
	public void testGetString () {
		String expected = new String("TEST");

		String actual = JsonUtil.getString(new Object());
		Assert.assertNull("Non String returns null.", actual);

		actual = JsonUtil.getString(expected);
		Assert.assertEquals("String properly returned.", expected, actual);
	}

	/**
	 * Test to ensure utility properly returns null when a non-Long object
	 * is passed in. Conversely, it ought to properly return a true Long
	 * when one is given.
	 */
	@Test
	public void testGetLong () {
		Long expected = new Long("12345");

		Long actual = JsonUtil.getLong(new Object());
		Assert.assertNull("Non Long returns null.", actual);

		actual = JsonUtil.getLong(expected);
		Assert.assertEquals("Long properly returned.", expected, actual);
	}

	/**
	 * Test to ensure utility properly returns null when a non-Integer object
	 * is passed in. Conversely, it ought to properly return a true Integer
	 * when one is given.
	 */
	@Test
	public void testGetInteger () {
		Integer expected = new Integer("123");

		Integer actual = JsonUtil.getInteger(new Object());
		Assert.assertNull("Non Integer returns null.", actual);

		actual = JsonUtil.getInteger(expected);
		Assert.assertEquals("Integer properly returned.", expected, actual);
	}

	/**
	 * Test to ensure utility properly returns null when a non-Date object
	 * is passed in. Conversely, it ought to properly return a true Date
	 * when one is given.
	 */
	@Test
	public void testGetDate () {
		Long expected_stamp = new Long("1370296964188");
		Date expected = new Date(expected_stamp);

		Date actual = JsonUtil.getDate(new Object());
		Assert.assertNull("Non Date returns null.", actual);

		actual = JsonUtil.getDate(expected);
		Assert.assertEquals("Date properly returned (date).", expected, actual);

		actual = JsonUtil.getDate(expected_stamp);
		Assert.assertEquals("Date properly returned (long).", expected, actual);
	}
}
