package ssc.swarc.helper.vcs.migration.valdiator.model;

import java.util.HashSet;

public class BranchInfo extends AbstractRevisionInfo {

	/**
	 * Constructor
	 * @param branchName the name of the revision
	 * @param revision the first revision found for this branch
	 */
	public BranchInfo(String branchName, Revision revision) {
		name = branchName;
		revisions = new HashSet<>();
		revisions.add(revision);
	}


	@Override
	public String toString() {
		return name + " (" + revisions.size() + ")";
	}

}
