package util;

public final class Mat {

	private Mat() {}

	public static int pow(int base, int esp) {
		if (esp < 0) throw new IllegalArgumentException("Esponente negativo!");
		if (base == 0 && esp == 0) throw new IllegalArgumentException("Base ed esponente nulli!");
		int r = 1, p = base;
		while (esp > 0) {
			if ((esp & 0x00000001) == 1) r *= p;
			p *= p;
			esp = esp >> 1;
		}
		return r;
	}

}
