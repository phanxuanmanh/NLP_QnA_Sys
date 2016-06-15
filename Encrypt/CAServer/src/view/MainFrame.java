package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import model.CertificateProvider;
import model.CheckInfo;
import model.IncorporationCertInfo;
import model.PersonalCertInfo;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel formPanel, infoPanel;
	JMenuBar menubar;
	JMenu option, help, about;
	JMenuItem incorpMenu, personMenu;
	CardLayout card;
	OutPutPanel outPutPanel;
	PersonalFormPanel personalTab;
	IncorporationFormPanel incorpTab;

	public MainFrame() {

		menubar = new JMenuBar();
		option = new JMenu("Option");
		incorpMenu = new JMenuItem("Incorporation certificate");
		personMenu = new JMenuItem("Personal certificate");
		menubar.add(option);
		option.add(incorpMenu);
		option.add(personMenu);
		menubar.add(option);

		setJMenuBar(menubar);
		setTitle("Certificate Register");
		setSize(1000, 1200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		formPanel = new JPanel(new CardLayout());
		personalTab = new PersonalFormPanel();
		incorpTab = new IncorporationFormPanel();
		formPanel.add(personalTab, "person");
		formPanel.add(incorpTab, "incorp");
		add(formPanel, BorderLayout.WEST);

		card = (CardLayout) formPanel.getLayout();
		incorpMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(formPanel, "incorp");
			}
		});
		personMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				card.show(formPanel, "person");
			}
		});

		outPutPanel = new OutPutPanel();

		outPutPanel.setCheckActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				boolean isExist = true;
				boolean available = true;
				if (personalTab.isShowing()) {
					PersonalCertInfo info = personalTab.getFormInfo();
					if (info == null) {
						available = false;
					} else {
						isExist = CheckInfo.check(info);
						available = true;
					}

				} else {
					IncorporationCertInfo info = incorpTab.getFormInfo();
					if (info == null) {
						available = false;
					} else {
						isExist = CheckInfo.check(info);
						available = true;
					}
				}
				if (!available)
					outPutPanel.addOutPutText("Infomation not available");
				else {
					if (isExist)
						outPutPanel
								.addOutPutText("Infomation has already exist");
					else
						outPutPanel.addOutPutText("Infomation is available");
				}
			}
		});
		outPutPanel.setRegistActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (personalTab.isShowing()) {
					if(checkPersonAvailable())
					try {
						CertificateProvider.createPersonalCert(
								personalTab.getFormInfo(),
								outPutPanel.getFoler(),
								personalTab.getFromDate(),
								personalTab.getExpiryDate());
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				} else {
					if(checkIncorpAvailable())
						try {
							CertificateProvider.createIncorpCert(
									incorpTab.getFormInfo(),
									outPutPanel.getFoler(),
									incorpTab.getFromDate(),
									incorpTab.getExpiryDate());
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
				}
			}
		});

		add(outPutPanel, BorderLayout.EAST);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		try {
			new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean checkPersonAvailable() {
			PersonalCertInfo info = personalTab.getFormInfo();
			if (info == null) {
				return false;
			} else {
				if(CheckInfo.check(info))
				return false;
				else return true;
			}
			
			
	}
	public boolean checkIncorpAvailable(){
		IncorporationCertInfo info = incorpTab.getFormInfo();
		if (info == null) {
			return false;
		} else {
			if(CheckInfo.check(info))
			return false;
			else return true;
		}
	}

}
