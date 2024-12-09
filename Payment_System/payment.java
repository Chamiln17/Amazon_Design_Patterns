

// Classe PayPal.java
class PayPal {
    public void connecter() {
        System.out.println("Connexion à PayPal.");
    }

    public void autoriser(double montant) {
        System.out.println("Autorisation de $" + montant + " via PayPal.");
    }

    public void capturer(double montant) {
        System.out.println("Capture de $" + montant + " via PayPal.");
    }

    public void rembourser(double montant) {
        System.out.println("Remboursement de $" + montant + " via PayPal.");
    }
}
// Interface PaymentAmazon.java
interface PaymentAmazon {
    void autoriserPaiement(double montant);
    void capturerPaiement(double montant);
    void rembourserPaiement(double montant);
}
// Classe StripeAdapter.java
class StripeAdapter implements PaymentAmazon {
    private Stripe stripe;

    public StripeAdapter(Stripe stripe) {
        this.stripe = stripe;
    }

    @Override
    public void autoriserPaiement(double montant) {
        stripe.initialiser();
        stripe.reserver(montant);
    }

    @Override
    public void capturerPaiement(double montant) {
        stripe.charger(montant);
    }

    @Override
    public void rembourserPaiement(double montant) {
        stripe.rembourserTransaction(montant);
    }
}

// Classe PayPalAdapter.java
class PayPalAdapter implements PaymentAmazon {
    private PayPal paypal;

    public PayPalAdapter(PayPal paypal) {
        this.paypal = paypal;
    }

    @Override
    public void autoriserPaiement(double montant) {
        paypal.connecter();
        paypal.autoriser(montant);
    }

    @Override
    public void capturerPaiement(double montant) {
        paypal.capturer(montant);
    }

    @Override
    public void rembourserPaiement(double montant) {
        paypal.rembourser(montant);
    }
}

// Classe Stripe.java
class Stripe {
    public void initialiser() {
        System.out.println("Initialisation de Stripe.");
    }

    public void reserver(double montant) {
        System.out.println("Montant de $" + montant + " réservé via Stripe.");
    }

    public void charger(double montant) {
        System.out.println("Montant de $" + montant + " débité via Stripe.");
    }

    public void rembourserTransaction(double montant) {
        System.out.println("Remboursement de $" + montant + " via Stripe.");
    }
}

// Classe ServicePayment.java
class ServicePayment {
    private PaymentAmazon processeurDePaiement;

    public ServicePayment(PaymentAmazon processeurDePaiement) {
        this.processeurDePaiement = processeurDePaiement;
    }

    public void traiterPaiement(double montant) {
        processeurDePaiement.autoriserPaiement(montant);
        processeurDePaiement.capturerPaiement(montant);
        System.out.println("Paiement de $" + montant + " traité avec succès !");
    }

    public void traiterRemboursement(double montant) {
        processeurDePaiement.rembourserPaiement(montant);
        System.out.println("Remboursement de $" + montant + " effectué avec succès !");
    }
}

// Classe Main.java
class Main {
    public static void main(String[] args) {
        // Utilisation de PayPal
        PayPal paypal = new PayPal();
        PaymentAmazon adaptateurPayPal = new PayPalAdapter(paypal);

        ServicePayment servicePaiementPayPal = new ServicePayment(adaptateurPayPal);
        servicePaiementPayPal.traiterPaiement(100.0);
        servicePaiementPayPal.traiterRemboursement(50.0);

        // Utilisation de Stripe
        Stripe stripe = new Stripe();
        PaymentAmazon adaptateurStripe = new StripeAdapter(stripe);

        ServicePayment servicePaiementStripe = new ServicePayment(adaptateurStripe);
        servicePaiementStripe.traiterPaiement(200.0);
        servicePaiementStripe.traiterRemboursement(75.0);
    }
}
