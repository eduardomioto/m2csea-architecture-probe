package br.com.mioto.cloud.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HttpCommons.
 */
public class HttpCommons {

	private static final Logger log = LoggerFactory.getLogger(HttpCommons.class);

	/**
	 * Execute HTTP Get Request.
	 *
	 * @param urlToRead the url to read
	 * @param httpMethod the http method
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String callHttp(String urlToRead, String httpMethod) throws IOException {
		final StringBuffer result = new StringBuffer();
		final URL url = new URL(urlToRead);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(httpMethod);
		final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		log.info("[HTTTP {}] Requesting : {}", httpMethod, urlToRead);
		rd.close();
		return result.toString();
	}
}
