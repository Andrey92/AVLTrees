package gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import alberi.*;
import util.*;

public class PannelloGrafico extends JPanel {

	private Set<NodoGrafico> nodiGrafici = new HashSet<NodoGrafico>();
	private NodoGrafico selected = null;
	private int altezzaAlbero;
	private MouseListener myListener;
	private JButton btnRemove;

	protected static final int SIN = 0;
	protected static final int DES = 1;
	protected static final int SU = 2;
	protected static final int STOP = 3;

	public PannelloGrafico(JButton b) {
		setLayout(null);
		btnRemove = b;
		select(null);
		myListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				select(null);
			}
		};
		addMouseListener(myListener);
	}

	public void disegnaAlbero(AlberoBin<Integer> a) {
		altezzaAlbero = altezza(a);
		setPreferredSize(new Dimension(NodoGrafico.WIDTH * Mat.pow(2, altezzaAlbero),
			(altezzaAlbero + 1) * 3 * NodoGrafico.HEIGHT / 2 + 20));
		assegnaNodiGrafici(a);
		repaint();
		select(null);
	}

	private <T> int altezza(AlberoBin<T> a) {
		int h1 = 0, h2 = 0;
		if (a.sin() != null) h1 = 1 + altezza(a.sin());
		if (a.des() != null) h2 = 1 + altezza(a.des());
		return h1 > h2 ? h1 : h2;
	}

	private void assegnaNodiGrafici(AlberoBin<Integer> a) {
		if (nodiGrafici.size() > 0) nodiGrafici.clear();

		int ac = altezzaAlbero;
		int currX = (getPreferredSize().width - NodoGrafico.WIDTH) / 2;
		int currY = NodoGrafico.HEIGHT / 2 + 10;
		int dir;

		AlberoBin<Integer> curr = a;
		while (curr != null) {
			nodiGrafici.add(new NodoGrafico(curr, currX, currY, ac, this));
			// Semantica della visita pre-order
			dir = SIN;
			while (dir != STOP) {
				switch (dir) {
				case DES:
					if (curr.des() == null) dir = SU;
					else {
						curr = curr.des();
						ac--;
						currX += Mat.pow(2, ac) * NodoGrafico.WIDTH / 2;
						currY += 3 * NodoGrafico.HEIGHT / 2;
						dir = STOP;
					}
					break;
				case SIN:
					if (curr.sin() == null) dir = DES;
					else {
						curr = curr.sin();
						ac--;
						currX -= Mat.pow(2, ac) * NodoGrafico.WIDTH / 2;
						currY += 3 * NodoGrafico.HEIGHT / 2;
						dir = STOP;
					}
					break;
				case SU:
					if (curr.padreBin() == null) {
						curr = curr.padreBin();
						dir = STOP;
					} else {
						if (curr.padreBin().sin() == curr) {
							curr = curr.padreBin();
							currX += Mat.pow(2, ac) * NodoGrafico.WIDTH / 2;
							currY -= 3 * NodoGrafico.HEIGHT / 2;
							dir = DES;
						} else {
							curr = curr.padreBin();
							currX -= Mat.pow(2, ac) * NodoGrafico.WIDTH / 2;
							currY -= 3 * NodoGrafico.HEIGHT / 2;
							dir = SU;
						}
						ac++;
					}
					break;
				}
			}
		}
	}

	public void select(NodoGrafico n) {
		if (selected != null) selected.unselect();
		selected = n;
		if (selected != null) {
			btnRemove.setEnabled(true);
			selected.select();
		}
		else btnRemove.setEnabled(false);
		repaint();
	}

	public int selected() { return selected.getVal(); }

	public void paintComponent(Graphics g) {
		removeAll();
		super.paintComponent(g);
		// Traccio gli archi
		Graphics2D g2 = (Graphics2D)g;
		int startX, startY, endX, endY;
		for (NodoGrafico ng: nodiGrafici) {
			add(ng);
			if (ng.destro() || ng.sinistro()) {
				startX = ng.getX() + NodoGrafico.WIDTH / 2;
				startY = ng.getY() + NodoGrafico.HEIGHT;
				endY = startY + 3 * NodoGrafico.HEIGHT / 2 - NodoGrafico.HEIGHT;
				g2.setPaint(Color.DARK_GRAY);
				if (ng.sinistro()) {
					endX = startX - Mat.pow(2, ng.getAltezza() - 1) * NodoGrafico.WIDTH / 2;			
					Line2D versoSinistra = new Line2D.Double(startX, startY, endX, endY);
					g2.draw(versoSinistra);
				}
				if (ng.destro()) {
					endX = startX + Mat.pow(2, ng.getAltezza() - 1) * NodoGrafico.WIDTH / 2;
					Line2D versoDestra = new Line2D.Double(startX, startY, endX, endY);
					g2.draw(versoDestra);
				}
			}
		}
	}
}
