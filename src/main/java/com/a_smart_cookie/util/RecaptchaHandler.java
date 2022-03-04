package com.a_smart_cookie.util;

import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public final class RecaptchaHandler {

	private static final Logger LOG = Logger.getLogger(RecaptchaHandler.class);

	private static final String SECRET_KEY = "6LfEvLQeAAAAAF50hL7OaCLyvWqvG5RTIbUxVO2j";
	private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";


	public static boolean verify(String gRecaptchaResponse) {
		if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
			return false;
		}

		try {
			URL verifyUrl = new URL(SITE_VERIFY_URL);

			// Open a Connection to URL above.
			HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

			// Add the Header information to the Request to prepare send to the server.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Data will be sent to the server.
			String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;

			// Send Request
			conn.setDoOutput(true);

			// Get the output stream of Connection.
			// Write data in this stream, which means to send data to Server.
			OutputStream outStream = conn.getOutputStream();
			outStream.write(postParams.getBytes());

			outStream.flush();
			outStream.close();

			// Response code return from Server.
			int responseCode = conn.getResponseCode();
			LOG.trace("responseCode=" + responseCode);

			// Get the Input Stream of Connection to read data sent from the Server.
			InputStream is = conn.getInputStream();

			JsonReader jsonReader = Json.createReader(is);
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			// ==> {"success": true}
			LOG.trace("Response: " + jsonObject);

			return jsonObject.getBoolean("success");
		} catch (Exception e) {
			LOG.debug("Verify result is false");
			return false;
		}
	}
}
