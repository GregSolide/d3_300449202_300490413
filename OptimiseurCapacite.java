public class OptimiseurCapacite {
	private static final int NOMBRE_EXECUTIONS = 10;

	private static final double SEUIL = 5.0d;

	public static int getNombreOptimalDePlaces(int tauxHoraire) {
	
		throw new UnsupportedOperationException("Cette méthode n’a pas encore été implémentée !");
	
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
