package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.lang.model.element.PackageElement;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import model.ReciveFile;
import model.SendFile;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JMenuBar menubar;
	SendPanel sendPanel;
	ReceivePanel receivePanel;
	JPanel contain;
	JMenu menuImPort, menuMode;
	JMenuItem importCert, importKey, receivMod, sendMode;
	String certFile, keyFile;
	JFileChooser fileChoser;

	public MainFrame() {
		menubar = new JMenuBar();
		menuImPort = new JMenu("Import");
		menuMode = new JMenu("Mode");
		setTitle("Authentiction File Transfer");
		importCert = new JMenuItem("Import Certificate File");
		importKey = new JMenuItem("Import Key File");
		importCert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChoser = new JFileChooser();
				fileChoser.setCurrentDirectory(new java.io.File("inbox"));
				fileChoser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChoser.setAcceptAllFileFilterUsed(false);
				fileChoser.addChoosableFileFilter(new CertFileFilter());
				if (fileChoser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("get Cert Selected File() : "
							+ fileChoser.getSelectedFile());
					certFile = fileChoser.getSelectedFile() + "";
					sendPanel.setCertFile(certFile);
				} else {
					System.out.println("No Selection ");
				}

			}
		});
		
		importKey.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChoser = new JFileChooser();
				fileChoser.setCurrentDirectory(new java.io.File("inbox"));
				fileChoser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChoser.setAcceptAllFileFilterUsed(false);
				fileChoser.addChoosableFileFilter(new KeyFileFilter());
				if (fileChoser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("get Selected Key File() : "
							+ fileChoser.getSelectedFile());
					keyFile = fileChoser.getSelectedFile() + "";
					sendPanel.setPrivateKeyFile(keyFile);
				} else {
					System.out.println("No Selection ");
				}

			}
		});
		receivMod = new JMenuItem("Go To Received Mod");
		sendMode = new JMenuItem("Go To Send Mode");
		menuImPort.add(importCert);
		menuImPort.add(importKey);
		menuMode.add(receivMod);
		menuMode.add(sendMode);
		menubar.add(menuMode);
		menubar.add(menuImPort);
		setJMenuBar(menubar);
		contain = new JPanel(new BorderLayout());
		contain.setBorder(BorderFactory.createEtchedBorder());
		sendPanel = new SendPanel();
		receivePanel = new ReceivePanel();
		contain.add(sendPanel, BorderLayout.WEST);
		contain.add(receivePanel, BorderLayout.EAST);
		add(contain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
  public void refresh(){
	  validate();;repaint();pack();
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
			MainFrame m = new MainFrame();
			m.receivePanel.setMessageListener(new ReciveFile());
			Thread t = new Thread();
			while (true) {
				t.sleep(1000);
				m.receivePanel.refresh();
				m.refresh();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
