package view;

import controller.Controller.CommandListner;
import model.Subject;

public interface Observer {
	public	void update(Subject subject);

	public void addActionListener(CommandListner commandListner);
	public Observer getView();
	public void setVisibility(boolean bool);
	public String getViewRef();
}
