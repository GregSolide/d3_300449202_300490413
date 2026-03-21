/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 * 
 *         L’implémentation de cette classe est complète. Vous n’avez *pas* besoin
 *         de modifier cette classe dans ce devoir.
 * 
 */
public class Voiture {

	/**
	 * Variable d’instance pour stocker le numéro de plaque de la voiture
	 */
	private String numeroPlaque;

	/**
	 * @return le numéro de plaque
	 */
	public String getNumeroPlaque() {
		return numeroPlaque;
	}

	/**
	 * Définit le numéro de plaque de la voiture
	 * 
	 * @param numeroPlaque est le numéro de plaque de la voiture
	 */
	public void setNumeroPlaque(String numeroPlaque) {
		this.numeroPlaque = numeroPlaque;
	}

	/**
	 * Constructeur de la classe Voiture
	 * 
	 * @param numeroPlaque est le numéro de plaque de la voiture
	 */
	public Voiture(String numeroPlaque) {
		this.numeroPlaque = numeroPlaque;
	}

	/**
	 * Retourne une représentation sous forme de chaîne de caractères de la voiture
	 */
	public String toString() {
		return "Plaque :  " + numeroPlaque;
	}
}
