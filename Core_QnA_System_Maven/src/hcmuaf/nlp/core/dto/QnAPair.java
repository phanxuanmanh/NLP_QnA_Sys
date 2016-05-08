package hcmuaf.nlp.core.dto;
public class QnAPair {
	private String question;
	private String answer;
	private int typeID;
	public QnAPair() {
	}

	public QnAPair(String question, String answer, int typeID) {
		super();
		this.question = question;
		this.answer = answer;
		this.typeID = typeID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	@Override
	public String toString() {
		return "Question: " + question + "-- Answer: " + answer
				+ " -- Type Id: " + typeID;
	}

}
