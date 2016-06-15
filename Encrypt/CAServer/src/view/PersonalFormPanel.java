package view;

import java.awt.Component;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


import model.PersonalCertInfo;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class PersonalFormPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	JTextField tname, taddress, tcompany, tphone, temail, twebsite, tcountry,
			tZipcode;
	JLabel lname, lBirthday, laddress, lcompany, lphone, lemail, lwebsite,
			lcountry, lzipcode, lfromDay, lexpiryDay;
	JDatePickerImpl fromday, expiryDay,birthDay;
	JDatePanelImpl fromDayPanel, expiryDatePanel,birthDayPanel;
	
	public PersonalFormPanel() {
		
		mainPanel= new JPanel();
		mainPanel.setBorder(BorderFactory.createTitledBorder("Register Info"));
		mainPanel.setLayout(new SpringLayout());
		lname = new JLabel("Full name");
		lBirthday = new JLabel("Birth Day");
		laddress = new JLabel("Address");
		lcompany = new JLabel("Company");
		lphone = new JLabel("Phone number");
		lemail = new JLabel("Email");
		lwebsite = new JLabel("Website");
		lcountry = new JLabel("Country");
		lzipcode = new JLabel("ZipCode");
		lfromDay = new JLabel("Valid from");
		lexpiryDay = new JLabel("Valid to");
		tname = new JTextField(15);
		taddress = new JTextField(15);
		tcompany = new JTextField(15);
		tcountry = new JTextField(15);
		temail = new JTextField(15);
		tphone = new JTextField(15);
		twebsite = new JTextField(15);
		tZipcode = new JTextField(15);
		addLable(lname, tname);
		birthDayPanel = new JDatePanelImpl(new UtilDateModel());
		birthDay= new JDatePickerImpl(birthDayPanel);
		birthDay.setTextEditable(true);
		addLable(lBirthday, birthDay);
		addLable(lcountry, tcountry);
		addLable(lemail, temail);
		addLable(lwebsite, twebsite);
		addLable(laddress, taddress);
		addLable(lzipcode, tZipcode);
		addLable(lphone, tphone);
		addLable(lcompany, tcompany);

		fromDayPanel = new JDatePanelImpl(new UtilDateModel());
		fromday = new JDatePickerImpl(fromDayPanel);
		// Dimension dim = fromday.getPreferredSize();
		// dim.width=100;
		// dim.height=20;
		// fromday.setPreferredSize(dim);
		fromday.setTextEditable(true);
		expiryDatePanel = new JDatePanelImpl(new UtilDateModel());
		expiryDay= new JDatePickerImpl(expiryDatePanel);
		expiryDay.setTextEditable(true);
		
		addLable(lfromDay, fromday);
		addLable(lexpiryDay, expiryDay);
		SpringUtilities.makeCompactGrid(mainPanel, 11, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad
		add(mainPanel);
	}

	
	public void addLable(JLabel lable, Component com) {
		lable.setLabelFor(com);
		mainPanel.add(lable);
		mainPanel.add(com);
	}
	public PersonalCertInfo getFormInfo(){
		try {
			String name =tname.getText();
			String address = taddress.getText();
			int  birth= birthDay.getModel().getYear();
			String company = tcompany.getText();
			String email = temail.getText();
			String coutry = tcompany.getText();
			String phone =tphone.getText();
			String website = twebsite.getText();
			int zipcode = Integer.parseInt(tZipcode.getText());
			if(name.equals("")||address.equals("")||company.equals("")||email.equals("")||coutry.equals("")||phone.equals("")||website.equals("")){
				JOptionPane.showMessageDialog(null, "Missing value");
			}
			else
			return new PersonalCertInfo(name, birth, company, address, phone, email, website, coutry, zipcode);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Format false");
			return null;
		}
		return null;
		
	}
	public Date getFromDate(){
		Date from =(Date) fromday.getModel().getValue();
		return from;
	}
	public Date getExpiryDate(){
		Date to =(Date) expiryDay.getModel().getValue();
		return to;
	}
}
