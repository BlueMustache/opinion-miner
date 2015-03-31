package model;

import java.util.Map;

import org.json.simple.JSONObject;

public interface IDatumBoxManager {
	
	public JSONObject TwitterSentimentAnalysis(String text);

	public JSONObject stringToJSONObject(String str);
}
