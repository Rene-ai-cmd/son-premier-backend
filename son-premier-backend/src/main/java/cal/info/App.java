package cal.info;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello la belle gang que vous etes." );

        // Création du serveur HTTP qui écoutera sur le port 8000
        HttpServer serveur = HttpServer.create(new InetSocketAddress(8000), 0);

        // Première route "/accueil" :
        serveur.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange echange) throws IOException {

                String response = "Une belle application en devenir";
                echange.sendResponseHeaders(200, response.length());
                OutputStream os = echange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        serveur.createContext("/etudiant", new ControleurEtudiant());
        serveur.createContext("/hackathon", new ControleurHackathon());

        // Démarrer le serveur
        serveur.setExecutor(null); // Créer un exécuteur par défaut
        serveur.start();

        System.out.println("Serveur démarré et en écoute sur le port 8000");
        System.out.println("http://localhost:8000/");

    }
}
