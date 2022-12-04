package ssc.swarc.helper.vcs.migration.valdiator.model;

import java.util.HashSet;
import java.util.Set;

public class TagInfo {
	
	protected String name = "Tags";
	
	protected Set<String> tags;
	
	public Set<String> getTags() {
		return tags;
	}

	public void addTag(String tag) {
		if(tags == null) {
			tags = new HashSet<>();
		}
		this.tags.add(tag);
	}
	
	  @Override
	  public int hashCode() {
	   return  name.hashCode() * tags.hashCode();
	  }
	  
	  @Override
	  public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if(o instanceof TagInfo) {
				return  ((TagInfo)o).getTags().containsAll(tags);
			}
	   return  false;
	  }

	@Override
	public String toString() {
		return name;
	}

}
