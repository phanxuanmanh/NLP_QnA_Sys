package vn.hcmuaf.nlp.ui.home;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HomeScreen extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomeScreen() {
		
		addTextContent(" <fieldset>"
				+ "<legend>Gioi thieu:</legend>"
				+ "Trường Đại học Nông Lâm thành phố Hồ Chí Minh là một trường đại học công lập, đa ngành, trực thuộc Bộ Giáo dục và Đào tạo, tọa lạc trên khu đất rộng 118 ha thuộc phường Linh Trung, Quận Thủ Đức, Thành phố Hồ Chí Minh và một phần thuộc phường Đông Hoà, thị xã Dĩ An, tỉnh Bình Dương"
				+ "</fieldset>");
	}

	public void addTextContent(String content) {
		Label label = new Label(content, ContentMode.HTML);
		addComponent(label);
	}
}
