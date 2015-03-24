package command;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

import model.Subject;
import model.TwitterDataSubject;

public class BtnEvaluateMongoResults extends JButton implements Command {
	
	private Subject subjectRef;
	
	public BtnEvaluateMongoResults(String caption, Subject subject) {
		// TODO Auto-generated constructor stub
		super(caption);
		this.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
		this.subjectRef = subject;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//((TwitterDataSubject)this.subjectRef).setMongoDataStore();
			if (!(((TwitterDataSubject) this.subjectRef).getRapidResultsJSON().isEmpty())/*&&!(((TwitterDataSubject) this.subjectRef).getDatumResultsJSON().isEmpty())*/){
		System.out.println("Update button pressed");
		System.out.println("Rapidminer results size ="+((TwitterDataSubject)this.subjectRef).getDatumResultsJSON().size());
		System.out.println("Mongo data store size ="+((TwitterDataSubject)this.subjectRef).getMongoDataStore().size());
		System.out.println("Datumresults size ="+((TwitterDataSubject)this.subjectRef).getDatumResultsJSON().size());
		ArrayList<JSONObject> mongoResults = ((TwitterDataSubject)this.subjectRef).getMongoDataStore();
		ArrayList<JSONObject> rapidResults = ((TwitterDataSubject)this.subjectRef).getRapidResultsJSON();
		ArrayList<JSONObject> datumResults = ((TwitterDataSubject)this.subjectRef).getDatumResultsJSON();
		//JSONObject mongoData = new JSONObject();
		int mongoSize=((TwitterDataSubject)this.subjectRef).getMongoDataStore().size();

//		for(int i = 0; i<mongoSize; i++){	
		int i=0;
		for(JSONObject obj : mongoResults ){
//			for(JSONObject rapidObj : rapidResults ){
//				for(JSONObject datumObj : datumResults ){
//			JSONObject mongoData = new JSONObject();
			obj.put("rapidMinerResults",rapidResults.get(i).get("RapidResult"));
			obj.put("datumResults",datumResults.get(i).get("result"));
			//mongoResults.get(i).put("tweet",((TwitterDataSubject)this.subjectRef).getDatumResultsJSON().get(i).get("tweet"));
			//mongoResults.add(mongoData);		
//				}
//			}
			i++;
		}
		//((TwitterDataSubject)this.subjectRef).setMongoDataStore(mongoResults);
		((TwitterDataSubject)this.subjectRef).notifyEvaluation();
		for(JSONObject obj :mongoResults ){
			System.out.println(obj.toString());
		}
			}else{
			
			JOptionPane.showMessageDialog(null,"Error", "Evaluation.",JOptionPane.ERROR_MESSAGE);
		}
	}

}
