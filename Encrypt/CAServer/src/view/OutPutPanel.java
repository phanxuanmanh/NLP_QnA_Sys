package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;


public class OutPutPanel  extends JPanel{
	JPanel chooseFilePanel,outputPanel,showPanel;
	JButton check,regist,browse;
	JLabel loutFile;
	JFileChooser outFile;
	String outFileName;
	JTextField tfileView;
	JTextArea outputInfo;
public OutPutPanel(){
	outFileName="";
	setLayout(new BorderLayout());
	chooseFilePanel = new JPanel(new SpringLayout());
	loutFile = new JLabel("Out put Path");
	browse = new JButton("Browse");
	outFile= new JFileChooser();
	outFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	outFile.setAcceptAllFileFilterUsed(false);
	outFile.addChoosableFileFilter(new FileFilter() {
		
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
	IOBox.add(loutFile);
	IOBox.add(Box.createHorizontalStrut(10));
	IOBox.add(tfileView= new JTextField(15));
	IOBox.add(Box.createHorizontalStrut(10));
	IOBox.add(browse);

	browse.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent arg0) {
		 tfileView.setText("");
		 if (outFile.showOpenDialog(OutPutPanel.this) == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): "
						+ outFile.getCurrentDirectory());
				System.out.println("getSelectedFile() : "
						+ outFile.getSelectedFile());
				tfileView.setText(outFile.getSelectedFile() + "");
				outFileName= outFile.getSelectedFile() + "";
			} else {
				System.out.println("No Selection ");
			}
			
		}
	});
	check= new JButton();
	check.setToolTipText("check for used");
	check.setIcon(new ImageIcon("image/check2.png"));
	
    regist=new JButton();
    regist.setIcon(new ImageIcon("image/add2.png"));
    regist.setToolTipText("Add new certificate");
  
    
	Box buttonBox = Box.createHorizontalBox();
	buttonBox.add(Box.createHorizontalStrut(10));
	buttonBox.add(Box.createVerticalStrut(200));
	buttonBox.add(check);
	buttonBox.add(Box.createHorizontalStrut(10));
	buttonBox.add(regist);
	buttonBox.add(Box.createHorizontalStrut(50));
	
	outputPanel = new JPanel(new BorderLayout());
	showPanel= new JPanel(new BorderLayout());
	showPanel.setBorder(BorderFactory.createTitledBorder("Process Infomation"));
	
	outputInfo = new JTextArea();
	outputInfo.setEditable(false);
	JScrollPane sc = new JScrollPane(outputInfo);
	showPanel.add(sc,BorderLayout.CENTER);
	outputPanel.add(IOBox,BorderLayout.NORTH);
	outputPanel.add(showPanel,BorderLayout.CENTER);
    add(buttonBox,BorderLayout.NORTH);
    add(outputPanel,BorderLayout.CENTER);
    
}

public void setCheckActionListener(ActionListener listen){
	check.addActionListener(listen);
}
public void setRegistActionListener(ActionListener listen){
	regist.addActionListener(listen);
}
public void addOutPutText(String text){
	String s =outputInfo.getText();
	outputInfo.setText(s+"\n"+text);
}
public String getFoler(){
	return tfileView.getText();
}
}
