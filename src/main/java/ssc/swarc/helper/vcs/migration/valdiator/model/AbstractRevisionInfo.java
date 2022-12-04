package ssc.swarc.helper.vcs.migration.valdiator.model;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstractRevisionInfo {
	
	/**
	 * The  name
	 */
	protected String name = "";
	protected Set<Revision> revisions;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Set<Revision> getRevisions() {
		return revisions;
	}
	public void addRevision(Revision revision) {
		if (revisions == null) {
			revisions = new HashSet<>();
		}
		if (isNew(revisions, revision)) {
			this.revisions.add(revision);
		}
	}
	@Override
	public int hashCode() {
		return name.hashCode() * revisions.hashCode();
	}
	
	/**
	 * This method checks whether or not a revision was already identified.
	 * Currently required due to a bug with an override of the hashCode method of Revision
	 * @param revNew
	 * @return
	 */
	public static  boolean isNew(Set<Revision> revisions,Revision revNew) {
		for(Revision rev : revisions) {
			if(rev.getMessage().equals(revNew.getMessage())&& rev.getRevision().equals(revNew.getRevision())) {
				return false;
			}
		}
		return true;
	}
	
	  
	  @Override
	  public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if(o instanceof AbstractRevisionInfo) {
				return ((AbstractRevisionInfo)o).getName().equals(name) && ((AbstractRevisionInfo)o).getRevisions().containsAll(revisions);
			}
	   return  false;
	  }

}
