package gov.usgs.earthquake.event;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
	public void testGetInputStream_gzip () throws Exception {
		final int port = 9999;
		startGzipServer(port);

		URL url = new URL("http", "localhost", port, "/");
		InputStream in = UrlUtil.getInputStream(url);

		Assert.assertTrue("Got a gzip input stream.",
				(in instanceof GZIPInputStream));
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


	/**
	 * Starts a simple server. Used to test GzipEncoding locally.
	 */
	private static void startGzipServer (final int port) throws Exception {
		new Thread() {
			public void run () {
				ServerSocket server = null;
				try {
					server = new ServerSocket(port);
					Socket request = server.accept();
					PrintStream out = new PrintStream(request.getOutputStream());
					out.println("HTTP/1.1 200 OK");
					out.println("Content-Encoding: gzip");
					out.println();
					out.flush();
					new GZIPOutputStream(request.getOutputStream());
				} catch (Exception ex) {
					Assert.fail(ex.getMessage());
				}
			}
		}.start();
	}
}
