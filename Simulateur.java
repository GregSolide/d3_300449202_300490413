/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 *
 */
public class Simulateur {

	/**
	 * Longueur des numéros de plaque
	 */
	public static final int LONGUEUR_NUMERO_PLAQUE = 3;

	/**
	 * Nombre de secondes dans une heure
	 */
	public static final int NOMBRE_SECONDES_DANS_1H = 3600;

	/**
	 * Durée maximale pendant laquelle une voiture peut être stationnée dans le stationnement
	 */
	public static final int DUREE_MAX_STATIONNEMENT = 8 * NOMBRE_SECONDES_DANS_1H;

	/**
	 * Durée totale de la simulation en secondes (simulées)
	 */
	public static final int DUREE_SIMULATION = 24 * NOMBRE_SECONDES_DANS_1H;

	/**
	 * La distribution de probabilité pour le départ d’une voiture du stationnement
	 * en fonction de la durée pendant laquelle la voiture a été stationnée
	 */
	public static final DistributionTriangulaire pdfDepart = new DistributionTriangulaire(
			0,
			DUREE_MAX_STATIONNEMENT / 2,
			DUREE_MAX_STATIONNEMENT
	);

	/**
	 * La probabilité qu’une voiture arrive à une seconde (simulée) donnée
	 */
	private Rationnel probabiliteArriveeParSeconde;

	/**
	 * L’horloge de simulation. Initialement, l’horloge doit être réglée à zéro;
	 * ensuite, elle doit être incrémentée d’une unité après chaque seconde (simulée)
	 */
	private int horloge;

	/**
	 * Nombre total d’étapes (secondes simulées) pendant lesquelles la simulation doit s’exécuter.
	 * Cette valeur est fixée au début de la simulation. La boucle de simulation doit être exécutée
	 * tant que horloge < etapes. Lorsque horloge == etapes, la simulation est terminée.
	 */
	private int etapes;

	/**
	 * Instance du stationnement simulé.
	 */
	private Stationnement stationnement;

	/**
	 * File pour les voitures voulant entrer dans le stationnement
	 */
	private File<Emplacement> fileEntrante;

	/**
	 * File pour les voitures voulant sortir du stationnement
	 */
	private File<Emplacement> fileSortante;

	/**
	 * @param stationnement      est le stationnement à simuler
	 * @param tauxArriveeParHeure est le taux horaire auquel les voitures se présentent devant le stationnement
	 * @param etapes             est le nombre total d’étapes de la simulation
	 */
	public Simulateur(Stationnement stationnement, int tauxArriveeParHeure, int etapes) {
		if (stationnement == null) {
			throw new NullPointerException("Stationnement non fourni");
		}
		if (tauxArriveeParHeure < 0) {
			throw new IllegalArgumentException("Le taux horaire d’arrivée ne peut être négatif");
		}
		if (etapes <= 0) {
			throw new IllegalArgumentException("Le nombre d’étapes doit être positif");
		}

		this.stationnement = stationnement;
		this.probabiliteArriveeParSeconde = new Rationnel(tauxArriveeParHeure, NOMBRE_SECONDES_DANS_1H);
		this.horloge = 0;
		this.etapes = etapes;
		this.fileEntrante = new FileChainee<Emplacement>();
		this.fileSortante = new FileChainee<Emplacement>();
	}


	/**
	 * Simule le stationnement pendant le nombre d’étapes spécifié par la variable d’instance etapes
	 * NOTE : Assurez-vous que votre implémentation de simuler() utilise consulter() de l’interface File.
	 */
	public void simuler() {
		while (horloge < etapes) {

			// Arrivée potentielle de voiture
			if (GenerateurAleatoire.evenementSurvenu(probabiliteArriveeParSeconde)) {
				String plaque = GenerateurAleatoire.genererChaineAleatoire(LONGUEUR_NUMERO_PLAQUE);
				Voiture voiture = new Voiture(plaque);
				Emplacement e = new Emplacement(voiture, horloge);
				fileEntrante.enfiler(e);
			}

			// Sorties du stationnement
			for (int i = stationnement.getOccupation() - 1; i >= 0; i--) {
				Emplacement e = stationnement.getEmplacementA(i);
				int tempsStationne = horloge - e.getInstant();
				if (tempsStationne > 0 && tempsStationne <= DUREE_MAX_STATIONNEMENT) {
					Rationnel pDePart = pdfDepart.pdf(tempsStationne);
					if (GenerateurAleatoire.evenementSurvenu(pDePart)) {
						Emplacement depart = stationnement.retirer(i);
						fileSortante.enfiler(depart);
					}
				}
			}

			// Sortie des véhicules de la file de sortie (en l’état, on les jette)
			while (!fileSortante.estVide()) {
				fileSortante.defiler();
			}

			// Entrée des véhicules de la file entrante
			while (!fileEntrante.estVide() && stationnement.getOccupation() < stationnement.getCapacite()) {
				Emplacement candidat = fileEntrante.consulter();
				if (stationnement.tenterStationnement(candidat.getVoiture(), horloge)) {
					fileEntrante.defiler();
				} else {
					break;
				}
			}

			horloge++;
		}
	}

	public int getTailleFileEntrante() {
		return fileEntrante.taille();
	}
}
