package ssc.swarc.helper.vcs.migration.valdiator.model;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ssc.swarc.helper.vcs.migration.valdiator.controller.util.VCSType;

public class VCSTreeModel implements TreeModel{
	
	protected String projectName;

	private TrunkInfo trunkInfo;
	private BranchSet branchSet;
	private TagInfo tagInfo;
	private VCSType vcs;
	
	public VCSTreeModel(VCSType type){
		vcs = type;
		initDefaults();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	private void initDefaults() {
		trunkInfo = new TrunkInfo();
		branchSet = new BranchSet();
		tagInfo = new TagInfo();

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		return numberAndNamesofBranchesAreEqual(o) && numberAndNamesofTagsAreEqual(o) && numberOfTrunkCommitsIsEqual(o);


	}

	private boolean numberOfTrunkCommitsIsEqual(Object o) {
		if(o instanceof VCSTreeModel) {
			VCSTreeModel com = ((VCSTreeModel)o);
	        return com.trunkInfo.revisions.size() == this.trunkInfo.getRevisions().size();
		}
		else {
			return false;
		}
	}

	private boolean numberAndNamesofTagsAreEqual(Object o) {
		if(o instanceof VCSTreeModel) {
			VCSTreeModel com = ((VCSTreeModel)o);
			Set<String> first = new HashSet<>();
			Set<String> second = new HashSet<>();
			for(String tag : com.getTagInfo().getTags()){
				first.add(tag);
			}
			for(String tag : this.getTagInfo().getTags()){
				second.add(tag);
			}
			if (first.size() != second.size())
	            return false;
	        return first.containsAll(second);
		}
		else {
			return false;
		}
	}

	private boolean numberAndNamesofBranchesAreEqual(Object o) {
		if(o instanceof VCSTreeModel) {
			VCSTreeModel com = ((VCSTreeModel)o);
			Set<String> first = new HashSet<>();
			Set<String> second = new HashSet<>();
			for(BranchInfo info : com.getBranchSet().getBranches()){
				first.add(info.getName());
			}
			for(BranchInfo info : this.getBranchSet().getBranches()){
				second.add(info.getName());
			}
			if (first.size() != second.size())
	            return false;
	        return first.containsAll(second);
		}
		else {
			return false;
		}
		
	}

	@Override
	public int hashCode() {
		return  trunkInfo.hashCode() * tagInfo.hashCode();
	}

	@Override
	public String toString() {
		if(vcs.equals(VCSType.GITLAB)) {
			return "GitLab Stats for: " + projectName;
		}
		else if(vcs.equals(VCSType.SVN)) {
			return "SVN Stats for: " + projectName;
		}
		else {
			return "Stats for: " + projectName;
		}
	}
	public TrunkInfo getTrunkInfo() {
		return trunkInfo;
	}

	public TagInfo getTagInfo() {
		return tagInfo;
	}

	public BranchSet getBranchSet() {
		return branchSet;
	}
	

	@Override
	public Object getRoot() {
		return this;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof VCSTreeModel && index == 0) {
			return ((VCSTreeModel)parent).getTrunkInfo() ;
		}
		else if (parent instanceof VCSTreeModel && index == 1) {
			return ((VCSTreeModel)parent).getBranchSet();
		}
		else if (parent instanceof VCSTreeModel && index == 2) {
			return ((VCSTreeModel)parent).getTagInfo();
		}
		else if (parent instanceof TrunkInfo ) {
			return ((TrunkInfo)parent).getRevisions().toArray()[index];
		}
		else if (parent instanceof BranchSet ) {
			return ((BranchSet)parent).getBranches().toArray()[index];
		}
		else if (parent instanceof BranchInfo ) {
			return ((BranchInfo)parent).getRevisions().toArray()[index];
		}
		else if (parent instanceof TagInfo ) {
			return ((TagInfo)parent).getTags().toArray()[index];
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof VCSTreeModel ) {
			return 3;
		}
		else if (parent instanceof TrunkInfo ) {
			return ((TrunkInfo)parent).getRevisions().size();
		}
		else if (parent instanceof TagInfo ) {
			return ((TagInfo)parent).getTags().size();
		}
		else if (parent instanceof BranchSet ) {
			return  ((BranchSet)parent).getBranches().size();
		}
		else if (parent instanceof BranchInfo ) {
			return  ((BranchInfo)parent).getRevisions().size();
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return (node instanceof Revision);
	}


	@Override
	public int getIndexOfChild(Object parent, Object child) {
		
		return 0;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		//  Auto-generated method stub
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		//  Auto-generated method stub
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		//  Auto-generated method stub
		
	}



}
