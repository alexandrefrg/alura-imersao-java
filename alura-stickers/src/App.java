import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
  public static void main(String[] args) throws Exception {

    // fazer uma conexão HTTP e buscar os top 250 filmes
    // Top 10 Movies
    // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
    // Most popular videos
    String apiKey = System.getenv("IMDB_API_KEY");
    System.out.println(apiKey);
    String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
    URI endereco = URI.create(url);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    String body = response.body();

    // pegar só os dados que interessam (titulo, poster, classificação)
    JsonParser parser = new JsonParser();
    List<Map<String, String>> listaDeFilmes = parser.parse(body);

    // exibir e manipular os dados
    for (Map<String,String> filme : listaDeFilmes) {
      System.out.println("\u001b[1mTítulo:\u001b[m " + filme.get("title"));
      System.out.println("\u001b[1mURL da imagem:\u001b[m " + filme.get("image"));
      double classificacao = Double.parseDouble(filme.get("imDbRating"));
      int numeroEstrelinhas = (int)classificacao;
      for (int n = 1; n <= numeroEstrelinhas ; n++) {
        System.out.print("⭐️");
      }
      System.out.println("\n");
    }
  }
}
