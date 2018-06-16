package game;

import utilities.Vector2D;
import java.awt.Dimension;

public class Constants {
	public static final int FRAME_WIDTH = (int)(1920);
	public static final int FRAME_HEIGHT = (int)(1080);
	public static final Dimension FRAME_SIZE = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

	public static final int DELAY = 16; 
	public static final double DT = DELAY / 1000.0; 
}
