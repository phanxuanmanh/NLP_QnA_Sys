/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.model;

import java.util.ArrayList;

/**
 * The Class QuestionVectorCsv.
 */
public class QuestionVectorCsv {
	
	/** The weight arr. */
	private ArrayList<String> weightArr;
	
	/** The class id. */
	private Integer classID;

	/**
	 * Instantiates a new question vector csv.
	 *
	 * @param weightArr the weight arr
	 * @param classID the class id
	 */
	public QuestionVectorCsv(ArrayList<String> weightArr, int classID) {
		super();
		this.weightArr = weightArr;
		this.classID = classID;
	}

	/**
	 * Gets the weight arr.
	 *
	 * @return the weight arr
	 */
	public ArrayList<String> getWeightArr() {
		return weightArr;
	}

	/**
	 * Sets the weight arr.
	 *
	 * @param weightArr the new weight arr
	 */
	public void setWeightArr(ArrayList<String> weightArr) {
		this.weightArr = weightArr;
	}

	/**
	 * Gets the class id.
	 *
	 * @return the class id
	 */
	public Integer getClassID() {
		return classID;
	}

	/**
	 * Sets the class id.
	 *
	 * @param classID the new class id
	 */
	public void setClassID(Integer classID) {
		this.classID = classID;
	}

	

}
