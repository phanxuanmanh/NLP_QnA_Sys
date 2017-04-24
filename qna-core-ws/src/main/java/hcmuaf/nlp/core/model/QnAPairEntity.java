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

/**
 * The Class QnAPairEntity.
 */
@Entity
@Table(name = "QNA_PAIR")
public class QnAPairEntity {
	
	/** The question. */
	@Id
	@Column(name = "q_id")
	private Long question;
	
	/** The answer. */
	@Column(name = "a_id")
	private Long answer;

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public Long getAnswer() {
		return answer;
	}

	public void setAnswer(Long answer) {
		this.answer = answer;
	}

	
	
}
