package simulatepath;

public class Molecule {
	
	private double stepLengthX;
	private double stepLengthY;
	private double stepLengthZ;
	private Position position;
	private double velRail;
	private MicroTubule currentMicrotubule;
	/* This constructor is for active propagation */
	public Molecule(double stepLengthX, double stepLengthY, 
			double stepLengthZ, Position position, double velRail){
		this.setStepLengthX(stepLengthX);
		this.setStepLengthY(stepLengthY);
		this.setStepLengthZ(stepLengthZ);
		this.setPosition(position);
		this.setVelRail(velRail);
	}
	/* This constructor is for passive propagation */
	public Molecule(double stepLengthX, double stepLengthY, 
			double stepLengthZ, Position position){
		this.setStepLengthX(stepLengthX);
		this.setStepLengthY(stepLengthY);
		this.setStepLengthZ(stepLengthZ);
		this.setPosition(position);
	}
	
	public double getStepLengthX() {
		return stepLengthX;
	}
	public void setStepLengthX(double stepLengthX) {
		this.stepLengthX = stepLengthX;
	}
	public double getStepLengthY() {
		return stepLengthY;
	}
	public void setStepLengthY(double stepLengthY) {
		this.stepLengthY = stepLengthY;
	}
	public double getStepLengthZ() {
		return stepLengthZ;
	}
	public void setStepLengthZ(double stepLengthZ) {
		this.stepLengthZ = stepLengthZ;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public double getVelRail() {
		return velRail;
	}
	public void setVelRail(double velRail) {
		this.velRail = velRail;
	}
	public MicroTubule getCurrentMicrotubule() {
		return currentMicrotubule;
	}
	public void setCurrentMicrotubule(MicroTubule currentMicrotubule) {
		this.currentMicrotubule = currentMicrotubule;
	}
}
