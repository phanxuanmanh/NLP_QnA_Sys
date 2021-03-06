/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class QuestionType.
 */
@Entity
@Table(name = "QUESTION_TYPES")
public class QuestionType {
	
	/** The type id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "type_id")
	private Integer typeID;
	
	/** The type name. */
	@Column(name = "type_name")
	private String typeName;
	
	
	/**
	 * Instantiates a new question type.
	 *
	 * @param typeID the type id
	 * @param typeName the type name
	 */
	public QuestionType(Integer typeID, String typeName) {
		super();
		this.typeID = typeID;
		this.typeName = typeName;
	}

	/**
	 * Instantiates a new question type.
	 */
	public QuestionType() {
		super();
	}
	
	public Integer getTypeID() {
		return typeID;
	}

	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
	}

	/**
	 * Gets the type name.
	 *
	 * @return the type name
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Sets the type name.
	 *
	 * @param typeName the new type name
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
