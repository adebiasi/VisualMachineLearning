package examples.travelingSalesperson;

public class PathRecordInfo {

	public Path bestEver, currentBest;
	public Integer recordGeneration;
	public Double recordDistance = Double.POSITIVE_INFINITY; 
	String title;
	
	public PathRecordInfo(String title) {
		super();
		this.title = title;
	}
	
}
