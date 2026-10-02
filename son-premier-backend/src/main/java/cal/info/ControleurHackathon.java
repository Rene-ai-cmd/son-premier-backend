package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ControleurHackathon implements HttpHandler {

    ObjectMapper objectMapper = new ObjectMapper();
    List<Hackathon> listeTemporaire = new ArrayList<>();

    @Override
    public void handle(HttpExchange echange) throws IOException {

        String typeRequete = echange.getRequestMethod();

        switch (typeRequete) {
            case "POST":
                ajouterHackathon(echange);
                break;
            case "GET":
                listeHackathons(echange);
                break;
            case "PUT":
                modifierHackathon(echange);
                break;
            case "DELETE":
                supprimerHackathon(echange);
                break;
            default:
                String reponse = "Fonctionnalité non prise en charge.";
                byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
                echange.sendResponseHeaders(501, octetsReponse.length);
                OutputStream fluxSortie = echange.getResponseBody();
                fluxSortie.write(octetsReponse);
                fluxSortie.close();
                break;
        }
    }

    public void listeHackathons(HttpExchange echange) throws IOException {
        System.out.println("Mes hackathons vont être listés");

//        Hackathon hackathonTest = new Hackathon("Hack Canada àéî()", "2027-01-08", "Guelph, Ontario", "https://hackcanada.org");
        byte[] hackathonSerialise = objectMapper.writeValueAsBytes(listeTemporaire);

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
//        String reponse = "Liste des Hackathons !";
//        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, hackathonSerialise.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(hackathonSerialise);
        fluxSortie.close();
    }

    public void ajouterHackathon(HttpExchange echange) throws IOException {
        // 1. On récupère le tuyau d'entrée (les données du client)
        InputStream fluxEntree = echange.getRequestBody();

        // 2. On lit TOUT le contenu du tuyau et on le transforme en texte
        String corpsRecu = new String(
                fluxEntree.readAllBytes(),
                StandardCharsets.UTF_8
        );

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);

        // 5. Je Déserialise
        Hackathon hackathonRecu = objectMapper.readValue(corpsRecu, Hackathon.class);
        listeTemporaire.add(hackathonRecu);

        System.out.println("Mon hackathon va etre ajouté");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon ajouté avec succès : " + hackathonRecu.toString();
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();

    }

    public void modifierHackathon(HttpExchange echange) throws IOException {

        // 1. On récupère le tuyau d'entrée (les données du client)
        InputStream fluxEntree = echange.getRequestBody();

        // 2. On lit TOUT le contenu du tuyau et on le transforme en texte
        String corpsRecu = new String(
                fluxEntree.readAllBytes(),
                StandardCharsets.UTF_8
        );

        // 3. On ferme le tuyau (bonne pratique)
        fluxEntree.close();

        // 4. On affiche ce qu'on a reçu pour vérifier
        System.out.println("Données reçues du client : " + corpsRecu);

        // 5. Je Déserialise
        Hackathon hackathonRecu = objectMapper.readValue(corpsRecu, Hackathon.class);


        System.out.println("Mon hackathon va être modifié");

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Hackathon modifié avec succès : " + hackathonRecu.toString();
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();
    }

    public void supprimerHackathon(HttpExchange echange) throws IOException {

        String filtre = echange.getRequestURI().getQuery();
        if (filtre != null && filtre.contains("idHackathon=")) {
            String param= filtre.split("idHackathon=")[1];
            int idHackathon = Integer.parseInt(param);
            System.out.println("Le hackathon " + idHackathon + " va être supprimé");

            // Poutine

            String reponse = "Hackathon supprimé avec succès !";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.sendResponseHeaders(200, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        } else {
            String reponse = "Identifiant Hackathon manquant.";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.sendResponseHeaders(400, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        }
    }

}
