public class Assignment {
	
	// ATTRIBUTES

	private String assignment_type;
	private double assignment_score;
	private double assignment_total;

	// METHODS

	public Assignment(String[] assignment_details) {
		this.assignment_type = assignment_details[0];
		this.assignment_score = Double.parseDouble(assignment_details[1]);
		this.assignment_total = Double.parseDouble(assignment_details[2]);
	}
	
	private String calcScore() {
		if ( this.assignment_total == 0 ) { // ACCOUNTS FOR EXTRA CREDIT ASSIGNMENTS.
			return "100%+ (EC)";
		} else {
			return Double.toString((100 * (this.assignment_score / this.assignment_total))) + "%";
		}
	}

	public double getScore() {
		return this.assignment_score;
	}

	public double getTotal() {
		return this.assignment_total;
	}
	
	public String getType() {
		return this.assignment_type;
	}

	public String toString() {
		if (assignment_type.length() < 1) {
			return this.assignment_type.toUpperCase() + "\t\t\t\t\t\t\t" + this.calcScore() + "%\n";
		} else if (assignment_type.length() < 3) {
			return this.assignment_type.toUpperCase() + "\t\t\t\t\t\t" + this.calcScore() + "\n";
		} else if (assignment_type.length() < 8) {
			return this.assignment_type.toUpperCase() + "\t\t\t\t\t" + this.calcScore() + "\n";
		} else if (assignment_type.length() < 13) {
			return this.assignment_type.toUpperCase() + "\t\t\t\t" + this.calcScore() + "\n";
		} else {
			return this.assignment_type.toUpperCase() + "\t\t\t" + this.calcScore() + "\n";
		}
	}
}