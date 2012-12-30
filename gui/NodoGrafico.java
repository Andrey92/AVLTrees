package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.font.*;
import javax.swing.*;

import alberi.*;

public class NodoGrafico extends JComponent {
	public static final int HEIGHT = 50, WIDTH = 60;
	private MouseListener myListener;
	private PannelloGrafico panel;
	private boolean selected = false;

	// Albero di cui il nodo è radice
	private AlberoBin<Integer> a;
	// Altezza di a
	private int altezza;
	
	public NodoGrafico(AlberoBin<Integer> a, int x, int y, int altezza, PannelloGrafico panel) {
		this.a = a;
		setBounds(x, y, WIDTH, HEIGHT);
		this.altezza = altezza;
		this.panel = panel;
		myListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				NodoGrafico.this.panel.select(NodoGrafico.this);
			}
		};
		addMouseListener(myListener);
	}

	public int getVal() { return a.val(); }
	public boolean sinistro() { return a.sin() != null; }
	public boolean destro() { return a.des() != null; }
	public int getAltezza() { return altezza; }
	public void unselect() { selected = false; }
	public void select() { selected = true; }

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Ellipse2D el = new Ellipse2D.Double(0, 0, WIDTH - 1, HEIGHT - 1);
		g2.setPaint(Color.DARK_GRAY);
		g2.draw(el);
		if (selected) {
			g2.setPaint(new Color(240, 100, 80));
			g2.fill(el);
		}
		Font f = new Font("Monospaced", Font.PLAIN, 12);
		g2.setFont(f);
		String text = String.valueOf(a.val());
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = f.getStringBounds(text, context);
		g2.setPaint(Color.DARK_GRAY);
		g2.drawString(text, (int)((WIDTH - bounds.getWidth()) / 2), (int)((HEIGHT - bounds.getHeight()) / 2 - bounds.getY()));
	}
	public int hashCode() { return a.val(); }
}
