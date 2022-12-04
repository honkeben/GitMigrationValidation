package ssc.swarc.helper.vcs.migration.valdiator.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import ssc.swarc.helper.vcs.migration.valdiator.model.Credentials;
import ssc.swarc.helper.vcs.migration.valdiator.model.VCSTreeModel;
import ssc.swarc.helper.vcs.migration.valdiator.ui.util.LocationInputVerifier;

public class InputDialog {

	private JFrame frmInpuDialog;
	private JTextField textFieldTrunkPath;
	private JTextField textFieldBranchPath;
	private JTextField textFieldTagPath;
	private JTextField textFieldSvnUser;
	private JPasswordField passwordFieldSvnAccess;
	private JTextField textFieldGitProjectName;
	private JTextField textFieldGitUser;
	private JPasswordField passwordFieldGitAccess;
	private JTree treeGitlab;
	private JTree treeSvn;
	private JTextArea textAreaMessage;
	private JButton btnCompareButton;
	private JLabel lblAnalysisResultLabel;
 	private JTextArea textAreaAuthor;
 	
	private String relativeTrunkPathExample = "e.g. /MyProject/trunk";
	private String relativeBranchPathExample = "e.g. /MyProject/branches";
 	private String relativeTagPathExample = "e.g. /MyProject/tags";
 	private String fontName = "Tahoma";

	/**
	 * Create the application.
	 */
	public InputDialog() {
		initialize();
		frmInpuDialog.setVisible(true);
		frmInpuDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInpuDialog = new JFrame();
		frmInpuDialog.setTitle("Migration Valdiation Dialog");
		frmInpuDialog.setBounds(100, 100, 1146, 510);
		frmInpuDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelControl = new JPanel();
		panelControl.setLayout(new GridLayout(1, 1, 0, 0));
		frmInpuDialog.getContentPane().setLayout(new GridLayout(1, 1, 0, 0));
		frmInpuDialog.getContentPane().add(panelControl);
		
		JPanel panelCred = new JPanel();
		panelControl.add(panelCred);
		panelCred.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelSvnCred = new JPanel();
		panelSvnCred.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCred.add(panelSvnCred);
		panelSvnCred.setLayout(new GridLayout(8, 2, 0, 0));
		
		JLabel lblHeaderSvn = new JLabel("Subversion Data");
		lblHeaderSvn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeaderSvn.setFont(new Font(fontName, Font.BOLD, 16));
		panelSvnCred.add(lblHeaderSvn);
		
		JLabel lblPlaceholderSvn = new JLabel("");
		panelSvnCred.add(lblPlaceholderSvn);
		
		JLabel lblTrunkPath = new JLabel("trunk path");
		panelSvnCred.add(lblTrunkPath);
		
		textFieldTrunkPath = new JTextField(relativeTrunkPathExample);
		textFieldTrunkPath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textFieldTrunkPath.getText().isEmpty()) {
					textFieldTrunkPath.setText(relativeTrunkPathExample);
				}
				if(textFieldTrunkPath.getText().equals(relativeTrunkPathExample)) {
					textFieldTrunkPath.selectAll();
				}
			}
		});
		textFieldTrunkPath.setColumns(10);
		textFieldTrunkPath.setInputVerifier(new LocationInputVerifier());
		panelSvnCred.add(textFieldTrunkPath);
		
		JLabel lblBranchPath = new JLabel("branches path");
		panelSvnCred.add(lblBranchPath);
		

		textFieldBranchPath = new JTextField(relativeBranchPathExample);
		textFieldBranchPath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textFieldBranchPath.getText().isEmpty()) {
					textFieldBranchPath.setText(relativeBranchPathExample);
				}
				if(textFieldBranchPath.getText().equals(relativeBranchPathExample)) {
					textFieldBranchPath.selectAll();
				}
			}
		});
		textFieldBranchPath.setColumns(10);
		textFieldBranchPath.setInputVerifier(new LocationInputVerifier());
		panelSvnCred.add(textFieldBranchPath);
		
		JLabel lblTagPath = new JLabel("tags path");
		panelSvnCred.add(lblTagPath);
		
		textFieldTagPath = new JTextField(relativeTagPathExample);
		textFieldTagPath.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textFieldTagPath.getText().isEmpty()) {
					textFieldTagPath.setText(relativeTagPathExample);
				}
				if(textFieldTagPath.getText().equals(relativeTagPathExample)) {
					textFieldTagPath.selectAll();
				}
				
			}
		});
		textFieldTagPath.setColumns(10);
		textFieldTagPath.setInputVerifier(new LocationInputVerifier());
		panelSvnCred.add(textFieldTagPath);
		
		JLabel lblCredentialsSVN = new JLabel("Credentials");
		lblCredentialsSVN.setFont(new Font(fontName, Font.BOLD, 16));
		panelSvnCred.add(lblCredentialsSVN);
		
		JLabel lblPlaceholderCredSvn = new JLabel("");
		panelSvnCred.add(lblPlaceholderCredSvn);
		
		JLabel lblSvnUser = new JLabel("user name");
		panelSvnCred.add(lblSvnUser);
		
		textFieldSvnUser = new JTextField();
		textFieldSvnUser.setColumns(10);
		panelSvnCred.add(textFieldSvnUser);
		
		JLabel lblSvnAccess = new JLabel("password");
		panelSvnCred.add(lblSvnAccess);
		
		passwordFieldSvnAccess = new JPasswordField();
		panelSvnCred.add(passwordFieldSvnAccess);
		
		JPanel panelGitCred = new JPanel();
		panelGitCred.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCred.add(panelGitCred);
		panelGitCred.setLayout(new GridLayout(8, 2, 0, 0));
		
		JLabel lblHeaderGitlab = new JLabel("GitLab Data");
		lblHeaderGitlab.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeaderGitlab.setFont(new Font(fontName, Font.BOLD, 16));
		panelGitCred.add(lblHeaderGitlab);
		
		JLabel lblPlaceholderGit = new JLabel("");
		panelGitCred.add(lblPlaceholderGit);
		
		JLabel lblGitlabProject = new JLabel("Project Name");
		panelGitCred.add(lblGitlabProject);
		
		textFieldGitProjectName = new JTextField();
		textFieldGitProjectName.setColumns(10);
		panelGitCred.add(textFieldGitProjectName);
		
		JLabel lblPlaceholderGita = new JLabel("");
		panelGitCred.add(lblPlaceholderGita);
		
		JLabel lblPlaceholderGitb = new JLabel("");
		panelGitCred.add(lblPlaceholderGitb);
		
		JLabel lblPlaceholderGitc = new JLabel("");
		panelGitCred.add(lblPlaceholderGitc);
		
		JLabel lblPlaceholderGitd = new JLabel("");
		panelGitCred.add(lblPlaceholderGitd);
		
		JLabel lblCredentialsGit = new JLabel("Credentials");
		lblCredentialsGit.setFont(new Font(fontName, Font.BOLD, 16));
		panelGitCred.add(lblCredentialsGit);
		
		JLabel lblPlaceholderCredGit = new JLabel("");
		panelGitCred.add(lblPlaceholderCredGit);
		
		JLabel lblGitlabUser = new JLabel("user name");
		panelGitCred.add(lblGitlabUser);
		
		textFieldGitUser = new JTextField();
		textFieldGitUser.setColumns(10);
		panelGitCred.add(textFieldGitUser);
		
		JLabel lblGitlabAccess = new JLabel("password");
		panelGitCred.add(lblGitlabAccess);
		
		passwordFieldGitAccess = new JPasswordField();
		panelGitCred.add(passwordFieldGitAccess);
		
		JPanel panelBbutton = new JPanel();
		panelCred.add(panelBbutton);
		panelBbutton.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panelBbutton.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		 btnCompareButton = new JButton("Compare");
		panel.add(btnCompareButton);
		
		lblAnalysisResultLabel = new JLabel("OK/NOK");
		lblAnalysisResultLabel.setOpaque(true);
		panel.add(lblAnalysisResultLabel);
		
		textAreaAuthor = new JTextArea();
		textAreaAuthor.setFont(new Font(fontName, Font.PLAIN, 15));
		panelBbutton.add(textAreaAuthor);
		
		textAreaMessage = new JTextArea();
		textAreaMessage.setFont(new Font(fontName, Font.PLAIN, 15));
		panelCred.add(textAreaMessage);
		
		JPanel panelResGitlab = new JPanel();
		frmInpuDialog.getContentPane().add(panelResGitlab);
		panelResGitlab.setLayout(new GridLayout(0, 1, 0, 0));
		


		 DefaultMutableTreeNode topGitStatNode =
			        new DefaultMutableTreeNode("GitLab Statistics");
		treeGitlab = new JTree(topGitStatNode);
		
		JScrollPane scrollPaneGitlab = new JScrollPane(treeGitlab);
		panelResGitlab.add(scrollPaneGitlab);
		
		JPanel panelResSvn = new JPanel();
		frmInpuDialog.getContentPane().add(panelResSvn);
		panelResSvn.setLayout(new GridLayout(0, 1, 0, 0));
		
		 DefaultMutableTreeNode topSVNStatNode =
			        new DefaultMutableTreeNode("SVN Statistics");
		treeSvn = new JTree(topSVNStatNode);
		JScrollPane scrollPaneSvn = new JScrollPane(treeSvn);
		panelResSvn.add(scrollPaneSvn);
		
	}
	
	public JButton getCompareButton() {
		return btnCompareButton;
	}
	
	public JTree getSVNTree() {
		
		return treeSvn;
	}

	public JTree getGitLabTree() {
		return treeGitlab;
	}

	public Credentials getSVNCredentials() {
		return new Credentials(textFieldSvnUser.getText(),passwordFieldSvnAccess.getPassword(),textFieldTrunkPath.getText(),textFieldBranchPath.getText(),textFieldTagPath.getText());			
	}
	
	public Credentials getGitCredentials() {
		return new Credentials(textFieldGitUser.getText(),passwordFieldGitAccess.getPassword(),textFieldGitProjectName.getText());			
	}

	public void updateGitTree(VCSTreeModel model) { 
			    treeGitlab.setModel(model);	
	}
	
	public void updateSVNTree(VCSTreeModel model) {
			    treeSvn.setModel(model);		
	}
	
	public void showMessage(String message, String author) {
		textAreaMessage.setText(message);
		textAreaAuthor.setText(author);
	}

	public void setAnalysisResult(boolean equals) {
		if(equals) {
			lblAnalysisResultLabel.setText("OK");
			lblAnalysisResultLabel.setBackground(Color.GREEN);
		}
		else {
			lblAnalysisResultLabel.setText("NOT OK");
			lblAnalysisResultLabel.setBackground(Color.RED);
		}
		
		
	}
}
