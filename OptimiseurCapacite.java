public class OptimiseurCapacite {
	private static final int NOMBRE_EXECUTIONS = 10;

	private static final double SEUIL = 5.0d;

	public static int getNombreOptimalDePlaces(int tauxHoraire) {
		if (tauxHoraire < 0) {
			throw new IllegalArgumentException("Le taux horaire doit être non négatif");
		}

		int capacite = 1;
		while (true) {
			System.out.println("==== Capacité du stationnement fixée à : " + capacite + " ====\n");

			double totalFile = 0.0;
			for (int i = 1; i <= NOMBRE_EXECUTIONS; i++) {
				Stationnement stationnement = new Stationnement(capacite);
				Simulateur simulateur = new Simulateur(stationnement, tauxHoraire, Simulateur.DUREE_SIMULATION);
	
				long debut = System.currentTimeMillis();
				simulateur.simuler();
				long duree = System.currentTimeMillis() - debut;
				int fileFinale = simulateur.getTailleFileEntrante();
				totalFile += fileFinale;
				System.out.println("Simulation " + i + " (" + duree + " ms) ; Longueur de la file d'attente à la fin de la simulation : " + fileFinale);
			}

			double moyenne = totalFile / NOMBRE_EXECUTIONS;
			if (moyenne <= SEUIL) {
				return capacite;
			}

			capacite++;
		}
	}

	public static void main(String args[]) {
	
		InfoEtudiant.afficher();

		long debutPrincipal = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Utilisation : java OptimiseurCapacite <taux_horaire_d_arrivee>");
			System.out.println("Exemple : java OptimiseurCapacite 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("Le taux horaire d’arrivée doit être un entier positif !");
			return;
		}

		int tauxHoraire = Integer.parseInt(args[0]);

		int tailleStationnement = getNombreOptimalDePlaces(tauxHoraire);

		System.out.println();
		System.out.println("LA SIMULATION EST TERMINÉE !");
		System.out.println("Le nombre minimal de places de stationnement requises : " + tailleStationnement);

		long finPrincipal = System.currentTimeMillis();

		System.out.println("Temps total d’exécution : " + ((finPrincipal - debutPrincipal) / 1000f) + " secondes");

	}
}
