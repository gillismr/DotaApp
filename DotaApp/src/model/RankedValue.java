package model;

import java.util.ArrayList;
import java.util.List;

public class RankedValue {

	private String rawText;
	private List<Double> values;
	private int numValues;
	
	public RankedValue() {
		// TODO Auto-generated constructor stub
	}
	
	public RankedValue(String rawText) {
		this.rawText = rawText;
		this.values = new ArrayList<Double>();
		int i = 0;
		for(String value:rawText.split(" ")){
			values.add(Double.parseDouble(value));
			i++;
		}
		this.numValues = i;
		this.collapseRepeats();
	}
	
	private void collapseRepeats() {
		if(this.numValues > 1){
			boolean shouldCollapse = true;
			for(int i = 1; i<this.numValues; i++){
				//If any of the values is ever different, set to false and don't collapse
				shouldCollapse = shouldCollapse && this.get(i) == this.get(i-1);
			}
			if(shouldCollapse){
				this.setNumValues(1);
				List<Double> oneValue = new ArrayList<Double>();
				oneValue.add(this.get(1));
				this.setValues(oneValue);
			}
		}
		
	}

	public double get(int i){
		if(numValues == 1)
			return values.get(0);
		else
			return values.get(i);
	}

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public int length() {
		return numValues;
	}

	public void setNumValues(int numValues) {
		this.numValues = numValues;
	}

}
