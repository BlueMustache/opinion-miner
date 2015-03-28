package view;

import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.json.simple.JSONObject;

import model.Subject;
import model.TwitterDataSubject;
import controller.Controller.CommandListner;

public class ChartView extends JScrollPane implements Observer {

	private Subject subjectRef;
	private String observerRef;
	private JPanel mainPanel;

	public ChartView(Subject subjectReference, String observserRef) {
		// TODO Auto-generated constructor stub
		this.subjectRef = subjectReference;
		this.observerRef = observserRef;
		subjectReference.registerObserver(this, observserRef);
		// JFreeChart chart = createChart(dataset, chartTitle);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		this.mainPanel = new JPanel();
		PieDataset data = getData();
		JFreeChart chart = ChartFactory.createPieChart3D("Sentiment Results",data, true, true, true);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(270);
		plot.setForegroundAlpha(0.60f);
		plot.setInteriorGap(0.02);
		StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0}{2}");
		plot.setLabelGenerator(labelGenerator);		 
		plot.setLegendLabelGenerator(labelGenerator);
		ChartPanel chPanel = new ChartPanel(chart);
		chPanel.revalidate();
		chPanel.repaint();
		this.mainPanel.add(chPanel);
		this.setViewportView(mainPanel);
		this.revalidate();
		this.repaint();

	}

	public PieDataset getData() {
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef).getMongoDataStore();
		DefaultPieDataset sentimentResults = new DefaultPieDataset();
		int totaltweetCount = ((TwitterDataSubject) this.subjectRef).getMongoDataStore().size();

		int negCount = 0;
		int posCount = 0;
		for (JSONObject tweet : mongoDataStore) {
			// tweet.get("rapidMinerResults");
			if (tweet.get("RapidResult").toString()
					.equalsIgnoreCase("Negative")) {
				negCount++;
			} else {
				posCount++;
			}
		}
		double negPer = negCount/totaltweetCount;

		sentimentResults.setValue("Negative = ", negCount);
		sentimentResults.setValue("Positive = ", posCount);

		return sentimentResults;
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getObserverRef() {
		// TODO Auto-generated method stub
		return null;
	}

}
