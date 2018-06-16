package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {

    Action action;

    public Keys() {
        action = new Action();
    }

    public Action action() {
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                action.thrust = 1;
                break;
            case KeyEvent.VK_A:
                action.turn = -1;
                break;
            case KeyEvent.VK_S:
                action.thrust = -1;
                break;
            case KeyEvent.VK_D:
                action.turn = +1;
                break;
            case KeyEvent.VK_Q:
                action.strafe = -1;
                break;
            case KeyEvent.VK_E:
                action.strafe = 1;
                break;
            case KeyEvent.VK_Z:
                action.stop = true;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                action.thrust = 0;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                action.turn = 0;
                break;
            case KeyEvent.VK_Q:
            case KeyEvent.VK_E:
                action.strafe = 0;
                break;
            case KeyEvent.VK_Z:
                action.stop = false;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
        }
    }
}
