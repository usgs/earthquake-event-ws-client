package gov.usgs.earthquake.event;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.zip.GZIPInputStream;

/**
 * Utility methods for building and opening URLs.
 */
public class UrlUtil {

	public static final String GZIP_ENCODING = "gzip";
	public static final int DEFAULT_GET_INPUT_STREAM_TRIES = 3;


	/**
	 * Open a URL attempting to use gzip compression. Will try
	 * DEFAULT_GET_INPUT_STREAM_TRIES times.
	 *
	 * @param url
	 *      url to open
	 * @return opened InputStream, ready to be read.
	 * @throws IOException
	 */
	public static InputStream getInputStream (final URL url) throws Exception {
		return getInputStream(url, DEFAULT_GET_INPUT_STREAM_TRIES);
	}

	/**
	 * Open a URL attempting to use gzip compression.
	 *
	 * @param url
	 *      url to open
	 * @param tries
	 *      number of times to try
	 * @return opened InputStream, ready to be read.
	 * @throws IOException
	 */
	public static InputStream getInputStream (final URL url, int tries)
			throws Exception {
		Exception exception = null;
		URLConnection conn = null;
		InputStream in = null;
		boolean success = false;

		while (!success && tries-- > 0) {
			try {
				// request gzip
				conn = url.openConnection();
				conn.addRequestProperty("Accept-encoding", GZIP_ENCODING);
				conn.connect();

				in = conn.getInputStream();

				// ungzip response
				if (GZIP_ENCODING.equals(conn.getContentEncoding())) {
					in = new GZIPInputStream(in);
				}

				success = true;
			} catch (Exception ex) {
				exception = ex;
				// Just ignore for now...
				// TODO :: Maybe log ?
			}
		}

		if (!success) {
			if (exception == null) {
				exception = new Exception("An unknown error occurred.");
				exception.fillInStackTrace();
			}
			throw exception;
		}

		return in;
	}

	/**
	 * Utility method to build a query string from a map of parameters.
	 *
	 * @param params
	 *          the params, and keys with null values are omitted.
	 * @return query string containing params.
	 */
	public static String getQueryString(final HashMap<String, Object> params) {
		StringBuffer buf = new StringBuffer();
		boolean first = true;

		Iterator<String> iter = new TreeSet<String>(params.keySet()).iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object value = params.get(key);

			if (value != null) {
				if (first) {
					buf.append("?");
					first = false;
				} else {
					buf.append("&");
				}
				buf.append(key).append("=").append(value.toString());
			}
		}
		return buf.toString();
	}

}
