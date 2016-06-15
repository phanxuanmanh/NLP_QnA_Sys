package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import model.Encrypt;
import model.SendFile;

public class SendPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton sign, encrypt, sendCert, choseFile, send;
	JTextField fileView, tAddress;
	JTextArea tOutPut;
	JLabel lMessage, lFile, lAddress;
	JPanel filePanel, funtionPanel, notePanel;
	JFileChooser fileChose;
	String fileName;
	Box buttonBox;
	String certFile, priKeyFile;
	SendFile sendFile;
	String password;

	public SendPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Send Cert"));
		filePanel = new JPanel();
		funtionPanel = new JPanel();
		notePanel = new JPanel(new BorderLayout());
		notePanel.setBorder(BorderFactory.createTitledBorder("Notify"));
		Dimension dim = notePanel.getPreferredSize();
		dim.height = 200;
		notePanel.setPreferredSize(dim);
		tOutPut = new JTextArea();
		tOutPut.setEditable(false);
		JScrollPane sc = new JScrollPane(tOutPut);
		notePanel.add(sc, BorderLayout.CENTER);
		funtionPanel.setBorder(BorderFactory.createEtchedBorder());
		funtionPanel.setLayout(new BorderLayout());
		lAddress = new JLabel("IP address");
		tAddress = new JTextField(15);
		Box adBox = Box.createHorizontalBox();
		adBox.add(Box.createHorizontalStrut(10));
		adBox.add(Box.createVerticalStrut(10));
		adBox.add(lAddress);
		adBox.add(Box.createHorizontalStrut(10));
		adBox.add(tAddress);
		adBox.add(Box.createHorizontalStrut(70));
		funtionPanel.add(adBox, BorderLayout.NORTH);

		// button
		buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(Box.createVerticalStrut(10));
		encrypt = new JButton("Encrypt file");
		encrypt.setActionCommand("encrypt");
		encrypt.setIcon(new ImageIcon("image/encrypt.png"));
		buttonBox.add(encrypt);
		buttonBox.add(Box.createHorizontalStrut(10));
		sendCert = new JButton("Send file");
		sendCert.setActionCommand("send");
		sendCert.setIcon(new ImageIcon("image/send.png"));
		buttonBox.add(sendCert);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(Box.createHorizontalStrut(50));
		funtionPanel.add(buttonBox, BorderLayout.SOUTH);

		filePanel.setBorder(BorderFactory.createEtchedBorder());
		lFile = new JLabel("Select File");
		fileView = new JTextField(20);
		choseFile = new JButton("Browse");
		fileChose = new JFileChooser();
		fileChose.setCurrentDirectory(new java.io.File("inbox"));
		fileChose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChose.setAcceptAllFileFilterUsed(false);
		fileChose.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return "Open file";
			}

			public boolean accept(File file) {
				return true;
			}
		});
		Box IOBox = Box.createHorizontalBox();
		IOBox.add(Box.createHorizontalStrut(10));
		IOBox.add(Box.createVerticalStrut(10));
		IOBox.add(lFile);
		IOBox.add(Box.createHorizontalStrut(10));
		IOBox.add(fileView);
		IOBox.add(Box.createHorizontalStrut(10));
		IOBox.add(choseFile);
		IOBox.add(Box.createHorizontalStrut(10));

		choseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileView.setText("");
				if (fileChose.showOpenDialog(SendPanel.this) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getSelectedFile() : "
							+ fileChose.getSelectedFile());
					fileView.setText(fileChose.getSelectedFile() + "");
					fileName = fileChose.getSelectedFile() + "";
				} else {
					System.out.println("No Selection ");
				}

			}
		});

		filePanel.add(IOBox);
		add(filePanel, BorderLayout.NORTH);
		add(funtionPanel, BorderLayout.CENTER);
		add(notePanel, BorderLayout.SOUTH);
		encrypt.addActionListener(this);
		sendCert.addActionListener(this);

	}

	public void initSendFile(){
		sendFile = new SendFile(fileName, tAddress.getText(), certFile,
				priKeyFile);
	}
	public String getFileName() {
		return fileView.getText();
	}

	public void setCertFile(String fileName) {
		certFile = fileName;
	}

	public void setPrivateKeyFile(String filePath) {
		this.priKeyFile = filePath;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton b = (JButton) e.getSource();
		if (b.getActionCommand().equals("encrypt")) {
			initSendFile();
			sendFile.encrypt();
		} else if (b.getActionCommand().equals("send")) {
			Thread t = new Thread(sendFile);
			t.start();
		}
	}

}
