package ssc.swarc.helper.vcs.migration.valdiator.model;

import java.util.HashSet;
import java.util.Set;

public class BranchSet {
	
	private String name = "Branches";
	
	private Set<BranchInfo> branches = new HashSet<>();
	
	public Set<BranchInfo> getBranches() {
		return branches;
	}
	
	  @Override
	  public int hashCode() {
	   return  name.hashCode() * branches.hashCode();
	  }

	  @Override
	  public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if(o instanceof BranchSet) {
				return  ((BranchSet)o).getBranches().containsAll(branches);
			}
	   return  false;
	  }
	  
	@Override
	public String toString() {
		return name+ " (" + branches.size() + ")";
	}

	public void putBranchCommit(String shortId, String branchName, String message, String author) {
		for(BranchInfo info : branches) {
			if(info.getName().equals(branchName)) {
				info.addRevision(new Revision (shortId,message, author));
				return;
			}
		}
		branches.add(new BranchInfo(branchName, new Revision(shortId, message, author)));
		
	}

	public void removebranchesByName(Set<String> tags) {
		Set<BranchInfo> branchesToRemove = new HashSet<>();
		for(BranchInfo info : branches) {
			for(String tag : tags) {
				if((info.getName().equals(tag))){
					branchesToRemove.add(info);
				}
			}
			
		}
		branches.removeAll(branchesToRemove);
	}

}
