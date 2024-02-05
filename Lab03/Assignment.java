public class Assignment {
	
	// ATTRIBUTES

	private String assignment_name;
	private String assignment_type;
	private double assignment_score;
	private double assignment_total;
	private double assignment_weight;

	// METHODS

	public Assignment(String name, String type, double score, double total, double weight) {
		this.assignment_name = name;
		this.assignment_type = type;
		this.assignment_score = score;
		this.assignment_total = total;
		this.assignment_weight = weight;
	}
	
	public double getWeightedScore() {
		return assignment_weight * (assignment_score / assignment_total);
	}
	
	public String getType() {
		return assignment_type;
	}

	public String toString() {
		return this.assignment_name + "\t" + "\t" + "\t" + (100 * (this.assignment_score / this.assignment_total)) + "%\n";
	}
}