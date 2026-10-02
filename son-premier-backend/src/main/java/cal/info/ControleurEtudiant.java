package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ControleurEtudiant implements HttpHandler {

    ObjectMapper objectMapper = new ObjectMapper();
    List<Etudiant> listeTemporaire = new ArrayList<>();

    @Override
    public void handle(HttpExchange echange) throws IOException {

        // On regarde le TYPE de requête pour savoir quoi faire
        String typeRequete = echange.getRequestMethod();

        switch (typeRequete) {
            case "POST":
                ajouterEtudiant(echange);
                break;            // ⚠️ Ne pas oublier le break !
            case "GET":

                String filtre = echange.getRequestURI().getQuery();
                if (filtre != null && filtre.contains("idHackathon=")) {
                    String param= filtre.split("idHackathon=")[1];
                    int idHackathon = Integer.parseInt(param);
                    rechercheParHackathons(echange, idHackathon);
                } else {
                    afficherEtudiants(echange);
                }
                break;
            case "PATCH":
                ajouterPreferences(echange);
                break;
            case "DELETE":
                supprimerEtudiant(echange);
                break;
            default:
                break;
        }
    }

    public void recupEntrees(HttpExchange echange) throws  IOException {
        InputStream fluxEntree = echange.getRequestBody();
    }

    public void ajouterEtudiant(HttpExchange echange) throws IOException {

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
        System.out.println("Mon etudiant vont être ajoutés");

        // 5. Construisons notre objet :
        Etudiant etudiantRecu = objectMapper.readValue(corpsRecu, Etudiant.class);

        listeTemporaire.add(etudiantRecu);

        //REPONSE
        // ÉTAPE 1 : préparer le texte de la réponse
        String reponse = "Etudiant ajouté avec succès ! : " + etudiantRecu.toString() ;
        byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);

        // ÉTAPE 2 : envoyer les en-têtes (code 200 = OK, + la longueur du corps)
        echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        echange.sendResponseHeaders(200, octetsReponse.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(octetsReponse);
        fluxSortie.close();

    }

    private void rechercheParHackathons(HttpExchange echange, int idHackathon) throws IOException {
        System.out.println("Recherche d'étudiants par id d'hackathon (" + idHackathon + ")");
        byte[] etudiantConverti = objectMapper.writeValueAsBytes(listeTemporaire);

        echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        echange.sendResponseHeaders(200, etudiantConverti.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(etudiantConverti);
        fluxSortie.close();

    }


    public void afficherEtudiants(HttpExchange echange) throws IOException {

        System.out.println("Mes etudiants vont être affichés");

//        Etudiant etudiantTest = new Etudiant("Yohann Ritter àéî()", 43, "Techniques de l'Informatique", 12345);
//        Hackathon hackathonTest = new Hackathon("Hack Canada àéî()", "2027-01-08", "Guelph, Ontario", "https://hackcanada.org");
//        etudiantTest.ajouterUnePreference(hackathonTest);
//        etudiantTest.ajouterUnePreference(hackathonTest);
        byte[] etudiantConverti = objectMapper.writeValueAsBytes(listeTemporaire);

        echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        echange.sendResponseHeaders(200, etudiantConverti.length);

        // ÉTAPE 3 : écrire le corps dans le tuyau de sortie, puis fermer
        OutputStream fluxSortie = echange.getResponseBody();
        fluxSortie.write(etudiantConverti);
        fluxSortie.close();
    }

    public void ajouterPreferences(HttpExchange echange) throws IOException {

        String filtre = echange.getRequestURI().getQuery();
        if (filtre != null && filtre.contains("matricule=")) {
            String matricule= filtre.split("matricule=")[1];
            System.out.println("L'étudiant " + matricule + " va être modifié");

            List<Integer> preferences = objectMapper.readValue(echange.getRequestBody().readAllBytes(), new TypeReference<>() {});
            // Poutine

            String reponse = "Préférences " + preferences + " ajoutées avec succès à l'étudiant " + matricule + " !";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.sendResponseHeaders(200, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        } else {
            String reponse = "Matricule étudiant manquant.";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.sendResponseHeaders(400, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        }

    }

    public void supprimerEtudiant(HttpExchange echange) throws IOException {
        String filtre = echange.getRequestURI().getQuery();
        if (filtre != null && filtre.contains("idEtudiant=")) {
            String param= filtre.split("idEtudiant=")[1];
            int idEtudiant = Integer.parseInt(param);
            System.out.println("L'étudiant " + idEtudiant + " va être supprimé");

            // Poutine

            String reponse = "Étudiant supprimé avec succès !";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            echange.sendResponseHeaders(200, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        } else {
            String reponse = "Identifiant étudiant manquant.";
            byte[] octetsReponse = reponse.getBytes(StandardCharsets.UTF_8);
            echange.sendResponseHeaders(400, octetsReponse.length);
            OutputStream fluxSortie = echange.getResponseBody();
            fluxSortie.write(octetsReponse);
            fluxSortie.close();
        }
    }

}
