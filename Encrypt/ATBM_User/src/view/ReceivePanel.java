package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.security.cert.X509Certificate;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import model.Encrypt;
import model.HashMD5;
import model.ReciveFile;
import model.VerifyCert;

public class ReceivePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton viewInfo, checkInfo, deny, accept, checkFile, choseFile;
	JTextField fileView;
	JLabel lMessage, lFile;
	JPanel filePanel, messagePanel, notePanel;
	JFileChooser fileChose;
	String outFolderName;
	JTextArea tOutPut;
	ReciveFile recive;
	X509Certificate cert;
	String md5,password,filePath;
	public ReceivePanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Inbox"));
		filePanel = new JPanel();
		messagePanel = new JPanel();
		notePanel = new JPanel(new BorderLayout());
		messagePanel.setBorder(BorderFactory.createTitledBorder("New request"));
		notePanel.setBorder(BorderFactory.createTitledBorder("Notify"));
		Dimension dim = notePanel.getPreferredSize();
		dim.height = 200;
		notePanel.setPreferredSize(dim);
		tOutPut= new JTextArea();
		tOutPut.setEditable(false);
		JScrollPane sc = new JScrollPane(tOutPut);
		notePanel.add(sc,BorderLayout.CENTER);
		
		filePanel.setBorder(BorderFactory.createEtchedBorder());
		
		
		lFile = new JLabel("Output Path");
		fileView = new JTextField(20);
		choseFile = new JButton("Browse");
		fileChose = new JFileChooser();
		fileChose.setCurrentDirectory(new java.io.File("inbox"));
		fileChose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChose.setAcceptAllFileFilterUsed(false);
		fileChose.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return "Open folder";
			}

			public boolean accept(File file) {
				if (file.isDirectory()) {
					return true;
				}
				return false;
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
				if (fileChose.showOpenDialog(ReceivePanel.this) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): "
							+ fileChose.getCurrentDirectory());
					System.out.println("getSelectedFile() : "
							+ fileChose.getSelectedFile());
					fileView.setText(fileChose.getSelectedFile() + "");
					outFolderName = fileChose.getSelectedFile() + "";
				} else {
					System.out.println("No Selection ");
				}

			}
		});

		filePanel.add(IOBox);
		add(filePanel, BorderLayout.NORTH);
		add(messagePanel, BorderLayout.CENTER);
		add(notePanel, BorderLayout.SOUTH);

		messagePanel.setLayout(new GridLayout(5, 1));
		
	
	}
	

	public void addMessage(String address) {
		final Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalStrut(10));
		box.add(Box.createVerticalStrut(10));
		box.add(lMessage = new JLabel("A request from " + address));
		box.add(Box.createHorizontalStrut(10));
		box.add(viewInfo = new JButton("View Info"));
		box.add(Box.createHorizontalStrut(10));
		box.add(checkInfo = new JButton("Verify Info"));
		box.add(Box.createHorizontalStrut(10));
		box.add(accept = new JButton("Accept & Decrypt "));
		box.add(Box.createHorizontalStrut(10));
		box.add(deny = new JButton("Deny"));
		box.add(Box.createHorizontalStrut(10));
		box.add(checkFile = new JButton("Check MD5"));
		box.add(Box.createHorizontalStrut(10));
		accept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				md5 =recive.getMd5();
				password=recive.getPassword();
				filePath=recive.getFile(fileView.getText());
				Encrypt.decryptFile(filePath, cert.getPublicKey(), password, outFolderName);
				File f = new File(filePath);
				f.delete();
			}
		});
		deny.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			recive.deny();

			}
		});
		checkInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean check=VerifyCert.verifyCer(cert);
				if(check==true){
					JOptionPane.showMessageDialog(null,"Infomation is true");
				}else{
					JOptionPane.showMessageDialog(null,"Infomation is fake");
				}
			}
		});
		viewInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tOutPut.setText(cert.toString());
			}
		});
		checkFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File f  = new File(filePath);
				boolean isEdit =HashMD5.isEdited(outFolderName+"/" + f.getName(), md5, cert.getPublicKey());
				if(isEdit){
					int i = JOptionPane.showConfirmDialog(null,"File is Edited. Do you want to delete it now","File is Edited",JOptionPane.YES_NO_OPTION);
					if(i== JOptionPane.YES_OPTION){
						File newFile = new File(outFolderName+"/" + f.getName());
						newFile.delete();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Your data is safe");
				}
				recive.deny();
				setMessageListener(recive);
			}
		});
		messagePanel.add(box);
	}
	public void setMessageListener(ReciveFile recive){
		this.recive=recive;
		recive.OpenServer();
		cert =recive.getCert();
		addMessage(recive.getSocketAddress());
	}
	public void refresh(){
		revalidate();
		repaint();
	}
	public void  setOutCert(){
		if(cert!=null)tOutPut.setText(cert.toString());
	}
	
}
