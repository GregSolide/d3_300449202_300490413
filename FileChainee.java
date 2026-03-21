/**
 * @auteur Marcel Turcotte, Guy-Vincent Jourdan et Mehrdad Sabetzadeh
 *         (Université d'Ottawa)
 * 
 * 
 */

// TODO :
// 1) conserver la taille et implémenter une méthode taille()
// 2) écrire une méthode consulter()

public class FileChainee<D> implements File<D> {

	private static class Element<T> {
		private T valeur;
		private Element<T> suivant;

		private Element(T valeur, Element<T> suivant) {
			this.valeur = valeur;
			this.suivant = suivant;
		}
	}

	private Element<D> tete;
	private Element<D> queue;
	private int taille;

	public FileChainee() {
		tete = queue = null;
		taille = 0;
	}

	public boolean estVide() {
		return tete == null;
	}

	public void enfiler(D nouvelElement) {

		if (nouvelElement == null) {
			throw new NullPointerException("aucun objet nul dans ma file !");
			// aurait pu être IllegalArgumentException mais NullPointerException
			// semble être la norme
		}

		Element<D> nouvelElementFile;
		nouvelElementFile = new Element<D>(nouvelElement, null);
		if (estVide()) {
			tete = nouvelElementFile;
			queue = nouvelElementFile;
		} else {
			queue.suivant = nouvelElementFile;
			queue = nouvelElementFile;
		}
		taille++;
	}

	public D defiler() {

		if (estVide()) {
			throw new IllegalStateException("Méthode defiler appelée sur une file vide");
		}

		D valeurRetournee;
		valeurRetournee = tete.valeur;

		if (tete.suivant == null) {
			tete = queue = null;
		} else {
			tete = tete.suivant;
		}

		taille--;
		return valeurRetournee;
	}

	public D consulter() {

-
		if (estVide()) {
			throw new IllegalStateException("Méthode consulter appelée sur une file vide");
		}
		return tete.valeur;
	}

	public int taille() {
		return taille;
	}

	public String toString() {

		StringBuffer valeurRetournee = new StringBuffer("[");

		if (!estVide()) {
			Element<D> curseur = tete;
			valeurRetournee.append(curseur.valeur);
			while (curseur.suivant != null) {
				curseur = curseur.suivant;
				valeurRetournee.append(", " + curseur.valeur);
			}
		}

		valeurRetournee.append("]");
		return valeurRetournee.toString();

	}
}
