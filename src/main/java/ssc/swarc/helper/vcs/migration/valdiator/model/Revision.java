package ssc.swarc.helper.vcs.migration.valdiator.model;

public class Revision {

	
	protected String revisionNumber;
	protected String message;
	protected String author;

	
	public Revision(String revision, String message, String author) {
		this.revisionNumber = revision;
		this.message = message;
		this.author = author;
	}

	public String getMessage() {
		return this.message;
	}


	public String getRevision() {
		return this.revisionNumber;
	}
	
	public String getAuthor() {
		return this.author;
	}


	
	  @Override
	  public int hashCode() {
		  return message.hashCode() * revisionNumber.hashCode() * author.hashCode();
	  }
	  
	  @Override
	  public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			if(o instanceof Revision) {
				return  ((Revision)o).getMessage().equals(message) &&  ((Revision)o).getRevision().equals(revisionNumber)&&  ((Revision)o).getAuthor().equals(author);
			}
	   return  false;
	  }

	@Override
	public String toString() {
		return revisionNumber;
	}


}
