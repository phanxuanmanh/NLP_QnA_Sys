/**
 * @author Manh Phan
 *
 * Edited date Jun 21, 2016
 */
package hcmuaf.nlp.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Class QnAPairEntity.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "QNA_PAIR")
public class QnAPairEntity {
	
	/** The question. */
	@Id
	@Column(name = "q_id")
	private Integer question;
	
	/** The answer. */
	@Column(name = "a_id")
	private Integer answer;
	
	/**
	 * Gets the question.
	 *
	 * @return the question
	 */
	public Integer getQuestion() {
		return question;
	}
	
	/**
	 * Sets the question.
	 *
	 * @param question the new question
	 */
	public void setQuestion(Integer question) {
		this.question = question;
	}
	
	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public Integer getAnswer() {
		return answer;
	}
	
	/**
	 * Sets the answer.
	 *
	 * @param answer the new answer
	 */
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	
	
}
