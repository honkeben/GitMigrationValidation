package ssc.swarc.helper.vcs.migration.valdiator.model;

public class TrunkInfo extends AbstractRevisionInfo {



	TrunkInfo(){
		name =  "Master";
	}

	@Override
	public String toString() {
		return name+ " (" + revisions.size() + ")";
	}

}
