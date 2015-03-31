package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
//Might need to sight this code
public class DatumboxManager implements IDatumBoxManager {

	private final String api_key;

	public DatumboxManager(String apiKey) {
		// TODO Auto-generated constructor stub
		this.api_key = apiKey;
	}

	// Web Request Functions
	private void AddArgument(Map<String, String> arguments, String key,String value) {
		arguments.remove(key); // will return false if not found...
		arguments.put(key, value);
	}

	private String GetArguments(Map<String, String> arguments) {
		StringBuilder parameters = new StringBuilder();
		Set<Map.Entry<String, String>> entrySet = arguments.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			EncodeAndAddItem(parameters, entry.getKey(), entry.getValue());
		}

		return parameters.toString();
	}

	private void EncodeAndAddItem(StringBuilder baseRequest, String key,
			String dataItem) {
		if (baseRequest == null) {
			baseRequest = new StringBuilder();
		}
		if (baseRequest.length() != 0) {
			baseRequest.append("&");
		}
		baseRequest.append(key);
		baseRequest.append("=");
		baseRequest.append(java.net.URLEncoder.encode(dataItem));
	}

	private String SendPostRequest(String URL, Map<String, String> arguments) {
		URL uri;
		HttpURLConnection request = null;
		String postData = GetArguments(arguments);
		try {
			uri = new URL(URL);
			request = (HttpURLConnection) uri.openConnection();

			request.setRequestMethod("POST");

			request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			request.setUseCaches(false);
			request.setDoInput(true);
			request.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					request.getOutputStream());
			wr.writeBytes(postData);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = request.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\n');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (request != null) {
				request.disconnect();
			}
		}

	}

	public JSONObject TwitterSentimentAnalysis(String text) {
		Map<String, String> arguments = new HashMap<String, String>();

		AddArgument(arguments, "api_key", api_key);
		AddArgument(arguments, "text", text);
		String str = SendPostRequest("http://api.datumbox.com/1.0/TwitterSentimentAnalysis.json",arguments);

		return stringToJSONObject(str);
	}
	
	//Create a JSON Object from a JSON style string. Allows for easier processing in the future
	public JSONObject stringToJSONObject(String jString) {

		String outputKey = jString.substring(2, 8);
		String outputVal = jString.substring(10, 42);
		String statusKey = jString.substring(12, 18);
		String statusVal = jString.substring(20, 21);
		String resultKey = jString.substring(23, 29);

		String resultValue = jString.substring(32, 40);
		resultValue = resultValue.substring(0, 1).toUpperCase()+resultValue.substring(1);
		resultValue = resultValue.replaceAll("\"", "");
		// System.out.println(jString.substring(32, 40));

		JSONObject obj = new JSONObject();
		// System.out.println("JSON OBJ Created");

		obj.put(outputKey, outputVal);
		// System.out.println("out created2");
		obj.put(statusKey, statusVal);
		// System.out.println("status created");
		obj.put(resultKey, resultValue);
		// System.out.println("Results Created");

		return obj;

	}
}
