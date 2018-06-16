package game;

public class Action {
	public int thrust; // -1 = decelerate, 0 = off, 1 = accelerate
	public int turn; // -1 = left turn, 0 = no turn, 1 = right turn
	public int strafe; // -1 = left, 0 = off, 1 = right
	public boolean shoot;
	public boolean stop;

	public Action(){}

	@Override
	public String toString() {
		return "Action(" + thrust + "," + turn + "," + strafe + "," + shoot + ")";
	}

}