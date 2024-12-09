
class Requete {
    String body;
    String endpoint;
    public Requete(String body, String endpoint) {
        this.body = body;
        this.endpoint = endpoint;
    }
}

class ParsedRequete {
    String parsedBody;
    String endpoint;

    public ParsedRequete(String parsedBody, String endpoint) {
        this.parsedBody = parsedBody;
        this.endpoint = endpoint;
    }
}

// Adapter
class Parser {
    public ParsedRequete parseRequest(Requete rawRequest) {
        String parsedBody = rawRequest.body.replaceAll("\\s+", " ").trim();
        return new ParsedRequete(parsedBody, rawRequest.endpoint);
    }
}

// Adapter
class Router {
    public void route(ParsedRequete parsedRequest) {
        System.out.println("endpoint: " + parsedRequest.endpoint);
        System.out.println("body: " + parsedRequest.parsedBody);
    }
}

public class Main {
    public static void main(String[] args) {
        Requete rawRequest = new Requete(" {  \"key\":  \"valeur\" }   ", "/api/resource");
        Parser bodyParser = new Parser();
        ParsedRequete parsedRequest = bodyParser.parseRequest(rawRequest);
        Router router = new Router();
        router.route(parsedRequest);
    }
}
