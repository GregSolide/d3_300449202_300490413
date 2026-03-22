/**
 * @auteur Mehrdad Sabetzadeh, Université d'Ottawa
 */
public class Simulateur {

	public static final int LONGUEUR_NUMERO_PLAQUE = 3;
	public static final int NOMBRE_SECONDES_DANS_1H = 3600;
	public static final int DUREE_MAX_STATIONNEMENT = 8 * NOMBRE_SECONDES_DANS_1H;
	public static final int DUREE_SIMULATION = 24 * NOMBRE_SECONDES_DANS_1H;

	public static final DistributionTriangulaire pdfDepart = new DistributionTriangulaire(
			0,
			DUREE_MAX_STATIONNEMENT / 2,
			DUREE_MAX_STATIONNEMENT
	);

	private Rationnel probabiliteArriveeParSeconde;
	private int horloge;
	private int etapes;
	private Stationnement stationnement;
	private File<Emplacement> fileEntrante;
	private File<Emplacement> fileSortante;

	/**
	 * @param stationnement       est le stationnement à simuler
	 * @param tauxArriveeParHeure est le taux horaire auquel les voitures arrivent
	 * @param etapes              est le nombre total d'étapes de la simulation
	 */
	public Simulateur(Stationnement stationnement, int tauxArriveeParHeure, int etapes) {

		if (stationnement == null) {
			throw new NullPointerException("Stationnement non fourni");
		}
		if (tauxArriveeParHeure < 0) {
			throw new IllegalArgumentException("Le taux horaire d'arrivée ne peut être négatif");
		}
		if (etapes <= 0) {
			throw new IllegalArgumentException("Le nombre d'étapes doit être positif");
		}

		this.stationnement = stationnement;
		this.probabiliteArriveeParSeconde = new Rationnel(tauxArriveeParHeure, NOMBRE_SECONDES_DANS_1H);
		this.horloge = 0;
		this.etapes = etapes;
		this.fileEntrante = new FileChainee<Emplacement>();
		this.fileSortante = new FileChainee<Emplacement>();
	}

	/**
	 * Simule le stationnement pendant le nombre d'étapes spécifié.
	 * Utilise consulter() avant defiler() pour respecter la sémantique de la file.
	 */
	public void simuler() {

		horloge = 0;

		while (horloge < etapes) {

			// --- 1. Arrivée potentielle d'une nouvelle voiture ---
			if (GenerateurAleatoire.evenementSurvenu(probabiliteArriveeParSeconde)) {
				String plaque = GenerateurAleatoire.genererChaineAleatoire(LONGUEUR_NUMERO_PLAQUE);
				Voiture voiture = new Voiture(plaque);
				// On enregistre l'heure d'arrivée réelle dans l'emplacement
				Emplacement arrivee = new Emplacement(voiture, horloge);
				fileEntrante.enfiler(arrivee);
			}

			// --- 2. Départs : vérifier quelles voitures stationnées partent ---
			// Parcours en sens inverse pour éviter les décalages d'indices après retrait
			for (int i = stationnement.getOccupation() - 1; i >= 0; i--) {
				Emplacement e = stationnement.getEmplacementA(i);
				int tempsStationne = horloge - e.getInstant();

				if (tempsStationne > 0 && tempsStationne <= DUREE_MAX_STATIONNEMENT) {
					Rationnel probDepart = pdfDepart.pdf(tempsStationne);
					if (GenerateurAleatoire.evenementSurvenu(probDepart)) {
						fileSortante.enfiler(stationnement.retirer(i));
					}
				}
			}

			// Vider la file sortante (les voitures partent définitivement)
			while (!fileSortante.estVide()) {
				fileSortante.defiler();
			}

			// --- 3. Entrées : tenter de faire entrer les voitures en attente ---
			while (!fileEntrante.estVide()) {
				// consulter() d'abord sans retirer — comme demandé par l'énoncé
				Emplacement candidat = fileEntrante.consulter();

				// ✅ On conserve l'heure d'arrivée originale (candidat.getInstant())
				//    et NON pas horloge
				boolean entre = stationnement.tenterStationnement(
						candidat.getVoiture(), candidat.getInstant());

				if (entre) {
					fileEntrante.defiler(); // retirer seulement si le stationnement a réussi
				} else {
					break; // stationnement plein, on arrête — les autres restent en file
				}
			}

			horloge++;
		}
	}

	/**
	 * @return la taille de la file entrante à la fin de la simulation
	 */
	public int getTailleFileEntrante() {
		return fileEntrante.taille();
	}
}