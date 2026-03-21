/**
 * Cette classe fournit une implémentation d’une distribution de probabilité
 * triangulaire. Une explication mathématique simple de cette distribution
 * de probabilité est disponible sur Wikipédia à l’adresse :
 * https://en.wikipedia.org/wiki/Triangular_distribution
 * 
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 *
 */
public class DistributionTriangulaire {

	/**
	 * a, c, b sont les trois paramètres sur l’axe des x de
	 * https://en.wikipedia.org/wiki/File:Triangular_distribution_PMF.png
	 */
	int a, c, b;

	/**
	 * Constructeur de DistributionTriangulaire. Vous devez vérifier que la condition
	 * suivante est respectée : a >= 0 ET a < c ET c < b
	 * 
	 * @param a est la limite inférieure de la distribution
	 * @param c est le mode
	 * @param b est la limite supérieure de la distribution
	 */
	public DistributionTriangulaire(int a, int c, int b) {
		if (a < c && c < b) {
			this.a = a;
			this.c = c;
			this.b = b;
		} else {

			// Indice : lancer ici une exception appropriée !
	
		}
	}

	/**
	 * @param x est un point sur l’axe des x
	 * @return la densité de probabilité au point x
	 */
	public Rationnel pdf(int x) {

		if (x < a)
			return Rationnel.zero;

		if (x >= a && x <= c)
			return new Rationnel(2 * (x - a), (b - a) * (c - a));

		if (x > c && x <= b)
			return new Rationnel(2 * (b - x), (b - a) * (b - c));

		return Rationnel.zero;
	}
}
