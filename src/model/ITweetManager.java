package model;

import java.util.ArrayList;

import twitter4j.Twitter;

public interface ITweetManager {
	public Twitter buildConfiguration();
	public void setTopic(String topic);
	public ArrayList<String> getTweets(String topic);

}
