public class Emplacement {
	private Voiture voiture;
	private int instant;

	public Voiture getVoiture() {
		return voiture;
	}

	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
	}

	public int getInstant() {
		return instant;
	}

	public void setInstant(int instant) {
		this.instant = instant;
	}

	public Emplacement(Voiture voiture, int instant) {
		this.voiture = voiture;
		this.instant = instant;
	}

	/**
	 * Retourne une représentation sous forme de chaîne de caractères de l’emplacement
	 */
	public String toString() {
		return voiture + ", instant : " + instant;
	}
}
