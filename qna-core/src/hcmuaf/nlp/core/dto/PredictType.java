/**
 * @author Manh Phan
 *
 * Edited date Jun 25, 2016
 */
package hcmuaf.nlp.core.dto;

/**
 * The Class PredictType.
 */
public class PredictType {
	
	/** The type id. */
	private Integer typeId;
	
	/** The exact degree. */
	private double exactDegree;

	/**
	 * Instantiates a new predict type.
	 *
	 * @param typeId the type id
	 * @param exactDegree the exact degree
	 */
	public PredictType(Integer typeId, double exactDegree) {
		this.typeId = typeId;
		this.exactDegree = exactDegree;
	}

	/**
	 * Gets the type id.
	 *
	 * @return the type id
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * Sets the type id.
	 *
	 * @param typeId the new type id
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * Gets the exact degree.
	 *
	 * @return the exact degree
	 */
	public double getExactDegree() {
		return exactDegree;
	}

	/**
	 * Sets the exact degree.
	 *
	 * @param exactDegree the new exact degree
	 */
	public void setExactDegree(double exactDegree) {
		this.exactDegree = exactDegree;
	}

}
