// cette implémentation utilise une variable d’instance taille
public class ListeChaineeSimple<E> implements Liste<E> {

    private static class Noeud<T> {
        private T valeur;
        private Noeud<T> suivant;
        private Noeud(T valeur, Noeud<T> suivant) {
            this.valeur = valeur;
            this.suivant = suivant;
        }
    }

    private Noeud<E> tete;
    private int taille;

    public ListeChaineeSimple() {
        tete = null;
        taille = 0;
    }

    public int taille() {
        return taille;
    }

    public boolean estVide() {
        return taille == 0;
    }

    public void ajouterDebut(E o) {

        if (o == null) {
            throw new NullPointerException("Impossible d’ajouter une référence nulle à la liste");
        }
        Noeud<E> nouveauNoeud = new Noeud<E>(o, null);
        if (tete == null) {
            tete = nouveauNoeud;
        } else {
            nouveauNoeud.suivant = tete;
            tete = nouveauNoeud;
        }
        taille++;
    }

    public void ajouter(E o) {

        if (o == null) {
            throw new NullPointerException("Impossible d’ajouter une référence nulle à la liste");
        }
        Noeud<E> nouveauNoeud = new Noeud<E>(o, null);
        if (tete == null) {
            tete = nouveauNoeud;
        } else {
            Noeud<E> p = tete;
            while (p.suivant != null)
                p = p.suivant;
            p.suivant = nouveauNoeud;
        }
        taille++;
    }

    public boolean retirer(E o) {

        if (tete == null)
            return false;

        if (tete.valeur.equals(o)) {
            tete = tete.suivant;
            taille--;
            return true;
        } else {
            Noeud<E> p = tete;
            while (p.suivant != null && !p.suivant.valeur.equals(o)) {
                p = p.suivant;
            }
            if (p.suivant == null) {
                return false;
            } else {
                p.suivant = p.suivant.suivant;
                taille--;
                return true;
            }
        }
    }

    public E obtenir(int position) {

        if (estVide())
            throw new IllegalStateException("Liste vide !");

        if (position < 0 || position >= taille) {
            throw new IndexOutOfBoundsException(Integer.toString(position));
        }

        Noeud<E> p = tete;

        for (int i = 0; i < position; i++) {
            p = p.suivant;
        }

        return p.valeur;
    }

    public E retirer(int position) {

        if (estVide())
            throw new IllegalStateException("Liste vide !");

        if (position < 0 || position >= taille) {
            throw new IndexOutOfBoundsException(Integer.toString(position));
        }

        Noeud<E> aRetirer;

        if (position == 0) {
            aRetirer = tete;
            tete = tete.suivant;
        } else {
            Noeud<E> p = tete;

            for (int i = 0; i < (position - 1); i++) {
                p = p.suivant;
            }
            aRetirer = p.suivant;
            p.suivant = p.suivant.suivant;
        }
        taille--;
        return aRetirer.valeur;
    }

    public boolean equals(ListeChaineeSimple<E> autreListe) {

        if ((autreListe == null) || (taille != autreListe.taille()))
            return false;

        Noeud<E> curseurThis = tete;
        Noeud<E> curseurAutre = autreListe.tete;

        for (int i = 0; i < taille; i++) {
            if (!curseurThis.valeur.equals(curseurAutre.valeur))
                return false;
            curseurThis = curseurThis.suivant;
            curseurAutre = curseurAutre.suivant;
        }

        return true;
    }

    public String toString() {
        StringBuffer res = new StringBuffer();
        res.append("[");
        if (!estVide()) {
            Noeud<E> curseur = tete;
            res.append(curseur.valeur);
            while (curseur.suivant != null) {
                curseur = curseur.suivant;
                res.append(" " + curseur.valeur);
            }
        }
        res.append("]");
        return res.toString();
    }
}
