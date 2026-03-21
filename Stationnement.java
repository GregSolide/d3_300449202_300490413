/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 */
public class Stationnement {

	// IMPORTANT : Il est *déconseillé* de définir de nouvelles variables d’instance
	// dans Stationnement. Vous êtes censé fournir une implémentation de
	// Stationnement
	// basée sur une liste. Définir de nouvelles variables d’instance peut vous
	// éloigner de cet objectif d’implémentation et entraîner une perte de points.
	/**
	 * Liste servant à stocker les informations d’occupation du stationnement
	 */
	private Liste<Emplacement> occupation;

	/**
	 * Nombre maximal de voitures que le stationnement peut accueillir
	 */
	private int capacite;

	/**
	 * Construit un stationnement avec une capacité (maximale) donnée
	 * 
	 * @param capacite est la capacité (maximale) du stationnement
	 */
	public Stationnement(int capacite) {

		if (capacite < 0) {
			throw new IllegalArgumentException("La capacité doit être positive");
		}

		this.capacite = capacite;
		this.occupation = new ListeChaineeSimple<Emplacement>();
	}

	/**
	 * Stationne une voiture (c) dans le stationnement.
	 * 
	 * @param c         est la voiture à stationner
	 * @param timestamp est le temps (simulé) auquel la voiture est stationnée
	 */
	public void stationner(Voiture c, int timestamp) {

		if (c == null) {
			throw new NullPointerException("Voiture non fournie");
		}
		if (timestamp < 0) {
			throw new IllegalArgumentException("Horodatage invalide");
		}
		if (occupation.taille() >= capacite) {
			throw new IllegalStateException("Stationnement plein");
		}

		Emplacement e = new Emplacement(c, timestamp);
		occupation.ajouter(e);
	}

	/**
	 * Retire la voiture (emplacement) stationnée à l’indice i de la liste
	 * 
	 * @param i est l’indice de la voiture à retirer
	 * @return l’emplacement qui a été retiré
	 */
	public Emplacement retirer(int i) {

		if (i < 0 || i >= occupation.taille()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}

		return occupation.retirer(i);
	}

	public boolean tenterStationnement(Voiture c, int timestamp) {

		if (c == null) {
			throw new NullPointerException("Voiture non fournie");
		}
		if (timestamp < 0) {
			throw new IllegalArgumentException("Horodatage invalide");
		}

		if (occupation.taille() >= capacite) {
			return false;
		}

		stationner(c, timestamp);
		return true;

	}

	/**
	 * @return la capacité du stationnement
	 */
	public int getCapacite() {
		return capacite;
	}

	/**
	 * Retourne l’instance Emplacement à une position donnée (i)
	 * 
	 * @param i est l’indice de l’emplacement
	 * @return l’instance Emplacement à la position i
	 */
	public Emplacement getEmplacementA(int i) {
		if (i < 0 || i >= occupation.taille()) {
			throw new IndexOutOfBoundsException(Integer.toString(i));
		}
		return occupation.obtenir(i);
	}

	/**
	 * @return le nombre total de voitures stationnées dans le stationnement
	 */
	public int getOccupation() {
		return occupation.taille();
	}

	/**
	 * @return représentation sous forme de chaîne de caractères du stationnement
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Capacité totale : " + this.capacite + System.lineSeparator());
		buffer.append("Occupation totale : " + this.occupation.taille() + System.lineSeparator());
		buffer.append("Voitures stationnées dans le stationnement : " + this.occupation + System.lineSeparator());

		return buffer.toString();
	}
}
