package hcmuaf.nlp.core.model;

import java.util.ArrayList;

public class QuestionVectorCsv {
	private ArrayList<String> weightArr;
	private int classID;

	public QuestionVectorCsv(ArrayList<String> weightArr, int classID) {
		super();
		this.weightArr = weightArr;
		this.classID = classID;
	}

	public ArrayList<String> getWeightArr() {
		return weightArr;
	}

	public void setWeightArr(ArrayList<String> weightArr) {
		this.weightArr = weightArr;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

}
