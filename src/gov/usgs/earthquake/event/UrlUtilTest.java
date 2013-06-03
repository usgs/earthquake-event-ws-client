package gov.usgs.earthquake.event;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

public class UrlUtilTest {

	@Test
	public void testInstance () {
		UrlUtil instance = new UrlUtil();
		Assert.assertNotNull("Can instantiate a UrlUtil object.", instance);
	}

	@Test
	public void testGetInputStream () throws Exception {
		InputStream in = UrlUtil.getInputStream((new File(".")).toURI().toURL());
		Assert.assertNotNull("Can get an input stream.", in);
	}

	@Test
	public void testGetQueryString () {
		int qs_param_count = 0;
		HashMap<String, Object> params = new HashMap<String, Object>();

		// No parameters
		String queryString = UrlUtil.getQueryString(params);
		Assert.assertEquals("Proper query string with no params.",
				"", queryString);

		// Only null-valued params
		params.put("foo", null);
		params.put("bar", null);
		queryString = UrlUtil.getQueryString(params);
		Assert.assertEquals("Proper query string for null-valued params only.",
			"", queryString);

		// Null-valued + non-null-valued params
		params.put("a", "true");
		params.put("b", "false");
		queryString = UrlUtil.getQueryString(params);
		Assert.assertNotNull(queryString);
		Assert.assertTrue("Query string starts with a '?'.",
				queryString.startsWith("?"));
		Assert.assertTrue("Query string contains 'a' parameter.",
				queryString.contains("a=true"));
		Assert.assertTrue("Query string contains 'b' parameter.",
				queryString.contains("b=false"));

		// This is the tricky bit, for N parameters, there should exist N-1
		// ampersand characters.
		for (int i = 0; i < queryString.length(); i++) {
			if (queryString.charAt(i) == '&') {
				qs_param_count += 1;
			}
		}
		Assert.assertEquals("Query string contains proper number of parameters.", 1,
				qs_param_count);
	}
}
