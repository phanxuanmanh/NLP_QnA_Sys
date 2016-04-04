package hcmuaf.nlp.core.model;

public class Keyword {
	private String content;
	private int id;

	public Keyword(String content, int id) {
		super();
		this.content = content;
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
