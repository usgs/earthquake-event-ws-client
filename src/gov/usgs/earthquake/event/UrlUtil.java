package gov.usgs.earthquake.event;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/**
 * Utility methods for building and opening URLs.
 */
public class UrlUtil {

	/**
	 * Open a URL attempting to use gzip compression.
	 *
	 * @param url
	 *          url to open
	 * @return opened InputStream, ready to be read.
	 * @throws IOException
	 */
	public static InputStream getInputStream(final URL url) throws IOException {
		// request gzip
		URLConnection conn = url.openConnection();
		conn.addRequestProperty("Accept-encoding", "gzip");
		InputStream in = conn.getInputStream();

		// ungzip response
		if (conn.getContentEncoding().equalsIgnoreCase("gzip")) {
			in = new GZIPInputStream(in);
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

		Iterator<String> iter = params.keySet().iterator();
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
