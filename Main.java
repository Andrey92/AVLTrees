import java.awt.*;
import gui.GUI;

public class Main {
	public static void main(String[]args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI g = new GUI();
				g.setVisible(true);
			}
		});
	}
}
