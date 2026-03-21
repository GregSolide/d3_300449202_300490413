// beaucoup de méthodes manquantes et d’implémentations manquantes

public interface Liste<E> {

	abstract void ajouter(E elem);

	abstract E retirer(int index);

	abstract boolean retirer(E o);

	abstract E obtenir(int index);

	abstract int taille();

	abstract boolean estVide();
}
