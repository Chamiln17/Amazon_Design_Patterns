import java.util.ArrayList;
import java.util.List;

// Interface commune (Component)
interface ReviewComponent {
    void display();
}

class Avis implements ReviewComponent {
    private String text;
    private List<ReviewComponent> children;

    public Avis(String text) {
        this.text = text;
        this.children = new ArrayList<>();}

    public void add(ReviewComponent component) {
        children.add(component);}

    public void remove(ReviewComponent component) {
        children.remove(component);}

    @Override
    public void display() {
        System.out.println("Avis: " + text);
        for (ReviewComponent child : children) {
            child.display();}}
}

class Commentaire implements ReviewComponent {
    private String text;

    public Commentaire(String text) {
        this.text = text; }

    @Override
    public void display() {
        System.out.println("  Commentaire: " + text);}
}

public class Main {
    public static void main(String[] args) {
        // Créer un avis principal
        Avis avisPrincipal = new Avis("Produit de bonne qualité !");

        // Ajouter des commentaires au premier avis
        Commentaire commentaire1 = new Commentaire("Merci pour cet avis !");
        Commentaire commentaire2 = new Commentaire("Je suis d'accord avec vous.");
        avisPrincipal.add(commentaire1);
        avisPrincipal.add(commentaire2);

        // Ajouter un sous-avis avec un commentaire
        Avis sousAvis = new Avis("Pas d'accord avec cette évaluation.");
        Commentaire sousCommentaire = new Commentaire("Pourquoi pas ? Expliquez-vous.");
        sousAvis.add(sousCommentaire);

        // Ajouter le sous-avis à l'avis principal
        avisPrincipal.add(sousAvis);

        // Afficher la hiérarchie des avis
        avisPrincipal.display();
    }
}

