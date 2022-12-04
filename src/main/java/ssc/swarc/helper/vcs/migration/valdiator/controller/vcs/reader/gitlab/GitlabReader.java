package ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader.gitlab;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Tag;
import org.gitlab4j.api.utils.ISO8601;

import ssc.swarc.helper.vcs.migration.valdiator.controller.util.VCSType;
import ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader.AbstractVCSReader;
import ssc.swarc.helper.vcs.migration.valdiator.model.BranchInfo;
import ssc.swarc.helper.vcs.migration.valdiator.model.Credentials;
import ssc.swarc.helper.vcs.migration.valdiator.model.Revision;
import ssc.swarc.helper.vcs.migration.valdiator.model.VCSTreeModel;

public class GitlabReader extends AbstractVCSReader {
	Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/*
	 * Default values:
	 */
	// Attributes requested from the properties file for gitlab credentials
	private static String gitLabUrlProp = "gitlab_url";
	private String projectName = "";
	private Date since = null;
	private Date until = null;
	
	private String msgType = "Error";
	
	GitLabApi gitLabApi = null;

	GitlabReader() {
		super();
	}

	public GitlabReader(Credentials credits) {
		super();
		user = credits.getUser();
		pwd = credits.getPW();
		projectName = credits.getProjectName();
		loadProperties();
		initReader();
		
	}

	@Override
	protected void loadProperties() {
		if (properties != null) {
			url = properties.getProperty(gitLabUrlProp);
		}
		try {
			since = ISO8601.toDate("2001-01-01T00:00:00Z");
		} catch (ParseException e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	protected void initReader() {
		// Log in to the GitLab server
		try {
			gitLabApi = GitLabApi.oauth2Login(url, user, pwd);
			since = ISO8601.toDate("2001-01-01T00:00:00Z");
			until = new Date();
		} catch (GitLabApiException | ParseException | IllegalArgumentException e) {
			logger.severe(e.getMessage());
			JOptionPane.showMessageDialog(null, "Gitlab Credentials are incorrect", msgType, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void startReading() {
		List<Project> projects = null;
		try {
			projects = gitLabApi.getProjectApi().getProjects();
			if (projects != null) {
				// Iterate through the projects and collect information
				for (Project project : projects) {
					if (projectName.equals(project.getName())) {
						VCSTreeModel info = new VCSTreeModel(VCSType.GITLAB);
						info.setProjectName(project.getName());
						collectBranchData(project, info);
						collectTagData(project, info);
						removeCommonRevisions(info);
						removeTagBranchInfos(info);
						readedInfoTreeModel= info;
					}
				}
			}
		} catch (GitLabApiException | NullPointerException e) {
			JOptionPane.showMessageDialog(null, "No connection with GitLab", msgType, JOptionPane.ERROR_MESSAGE);
		}
		if(readedInfoTreeModel==null) {
			JOptionPane.showMessageDialog(null, "No project "+projectName+" found in GitLab", msgType, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void removeTagBranchInfos(VCSTreeModel info) {
		Set<String> tags = info.getTagInfo().getTags();
		info.getBranchSet().removebranchesByName(tags);
	}

	@Override
	protected void shutdownReader() {
		gitLabApi.close();
	}

	/**
	 * A method to retrieve project-specific information about any contained
	 * branches
	 * 
	 * @param project The project instance to be inspected
	 * @throws GitLabApiException
	 */
	private void collectBranchData(Project project, VCSTreeModel info) throws GitLabApiException {
		List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project);
		for (Branch branch : branches) {
			List<Commit> commits = gitLabApi.getCommitsApi().getCommits(project, branch.getName(), since, until, "",
					false, true, true);

			for (Commit commit : commits) {
				String msg = commit.getMessage().substring(0, commit.getMessage().indexOf("git-svn-id:"));
				if (branch.getName().equals("master")) {
					
					info.getTrunkInfo().addRevision(new Revision(commit.getShortId(),msg, commit.getAuthorName()));
				} else {
					info.getBranchSet().putBranchCommit(commit.getShortId(), branch.getName(), msg, commit.getAuthorName());
				}
			}
		}
	}

	private void removeCommonRevisions(VCSTreeModel info) {
		List<Revision> revisionToRemove = new ArrayList<>();
		for (BranchInfo branch : info.getBranchSet().getBranches()) {
			for (Revision revision : branch.getRevisions()) {
				for(Revision rev : info.getTrunkInfo().getRevisions()) {
					if (rev.getRevision().equals(revision.getRevision())) {
						revisionToRemove.add(revision);
					}
				}
				
			}
		}
		for (BranchInfo branch : info.getBranchSet().getBranches()) {
			branch.getRevisions().removeAll(revisionToRemove);
		}
	}

	/**
	 * A method to retrieve project-specific information about any contained tags
	 * 
	 * @param project The project instance to be inspected
	 * @throws GitLabApiException
	 */
	private void collectTagData(Project project, VCSTreeModel info) throws GitLabApiException {
		List<Tag> tags = gitLabApi.getTagsApi().getTags(project);
		for (Tag tag : tags) {
			info.getTagInfo().addTag(tag.getName());
		}
	}

}
