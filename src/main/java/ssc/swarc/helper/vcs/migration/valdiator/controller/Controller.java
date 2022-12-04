package ssc.swarc.helper.vcs.migration.valdiator.controller;

import java.awt.EventQueue;
import java.util.logging.Logger;

import ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader.gitlab.GitlabReader;
import ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader.svn.SVNReader;
import ssc.swarc.helper.vcs.migration.valdiator.model.Revision;
import ssc.swarc.helper.vcs.migration.valdiator.ui.InputDialog;

public class Controller {
	Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private InputDialog view;

	/**
	 * Controller for organizing the UI and the logic
	 */
	public Controller() {

		Runnable runnable = () -> {
			try {
				view = new InputDialog();
				// Link the only button of the UI with control
				view.getCompareButton().addActionListener(e -> processComparison());
				// Listener to populate the message Area with information about SVN Revisions
				view.getSVNTree().addTreeSelectionListener(e -> valueChangedinSVNTree());
				// Listener to populate the message Area with information about Git Revisions
				view.getGitLabTree().addTreeSelectionListener(e -> valueChangedInGitTree());
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		};
		EventQueue.invokeLater(runnable);
	}

	/**
	 * Method to start control the comparison process Reading 1. Reading from VCS 2.
	 * Show Infos 3. Analyze Results 4. Show Results
	 */
	private void processComparison() {
		SVNReader svnReader = new SVNReader(view.getSVNCredentials());
		svnReader.startReading();
		view.updateSVNTree(svnReader.getModel());
		GitlabReader gitReader = new GitlabReader(view.getGitCredentials());
		gitReader.startReading();
		view.updateGitTree(gitReader.getModel());
		try {
			view.setAnalysisResult(gitReader.getModel().equals(svnReader.getModel()));
		} catch (Exception e) {
			logger.severe("Comparison not possible - check credentials");
		}
	}

	public void valueChangedinSVNTree() {
		Object node = view.getSVNTree().getLastSelectedPathComponent();

		if (node instanceof Revision) {
			view.showMessage((char)27 + "Message: " +((Revision) node).getMessage(), (char)27 + "Author: " + ((Revision) node).getAuthor());
		} else {
			view.showMessage("", "");
		}

	}

	public void valueChangedInGitTree() {
		Object node = view.getGitLabTree().getLastSelectedPathComponent();

		if (node instanceof Revision) {
			view.showMessage((char)27 + "Message: " +((Revision) node).getMessage(), (char)27 + "Author: " + ((Revision) node).getAuthor());
		} else {
			view.showMessage("", "");
		}

	}
}
