package view;

import controller.Controller.CommandListner;

import model.Subject;

public interface Observer {
	public	void update(Subject subject);

	public void addActionListener(CommandListner commandListner);

//	public void addActionListener(SearchListner listner);

}
