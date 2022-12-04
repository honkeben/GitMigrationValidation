package ssc.swarc.helper.vcs.migration.valdiator.model;

public class Credentials {
	private String user;
	private char[] pw;
	
	private String pathTrunk;
	private String pathBranch;
	private String pathTag;
	
	private String projectName;
	
	public Credentials(String login, char[] access, String trunk, String branch, String tag) {
		user = login;
		pw = access;
		pathTrunk =	 trunk;
		pathBranch =branch;
		pathTag =tag;
				
	}
	
	public Credentials(String login, char[] access, String project) {
		user = login;
		pw = access;
		projectName =	 project;
			
	}
	
	public String getUser() {
		return user;
	}
	public char[] getPW() {
		return pw;
	}

	public String getPathTrunk() {
		return pathTrunk;
	}

	public String getPathBranch() {
		return pathBranch;
	}
	public String getPathTag() {
		return pathTag;
	}

	public String getProjectName() {
		return projectName;
	}


}
