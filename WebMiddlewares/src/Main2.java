
interface GestionnaireRequete {
    void GestRequete(String requete);
}

class GestionnaireRequeteN1 implements GestionnaireRequete {
    @Override
    public void GestRequete(String requete) {
        System.out.println("Traitement de la requete: " + requete);
    }
}


// Decorator
class MiddlewareAuth implements GestionnaireRequete {
    private GestionnaireRequete Prochain;
    public MiddlewareAuth(GestionnaireRequete Prochain) {
        this.Prochain = Prochain;
    }
    @Override
    public void GestRequete(String requete) {
        if (Authentifier(requete)) {
            System.out.println("Authentification avec success");
            Prochain.GestRequete(requete);
        } else {
            System.out.println("Authentification echouee");
        }
    }
    private boolean Authentifier(String request) {
        return request.contains("Token");
    }
}


public class Main2 {
    public static void main(String[] args) {
        GestionnaireRequete basicHandler = new GestionnaireRequeteN1();
        GestionnaireRequete authHandler = new MiddlewareAuth(basicHandler);
        System.out.println("- requete 1 -");
        authHandler.GestRequete("Token: token valid; data: {\"key\": \"value\"}");
        System.out.println("\n=== requete 2 ===");
        authHandler.GestRequete("data: {\"key\": \"value\"}");
    }
}
