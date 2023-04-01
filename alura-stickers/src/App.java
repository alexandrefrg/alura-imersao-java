import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
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
    // String apiKey = System.getenv("IMDB_API_KEY");
    // System.out.println(apiKey);
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

    // verifica se o diretório de saída das figurinhas já existe
    File diretorio = new File("figurinhas/");
    diretorio.mkdir();

    // cria instância da geradora de figurinhas
    GeradoraDeFigurinhas geradora =  new GeradoraDeFigurinhas();
    for (Map<String,String> filme : listaDeFilmes) {

      String urlImagem = filme.get("image");
      String titulo = filme.get("title");
      Double classificacao = Double.parseDouble(filme.get("imDbRating"));
      String nomeArquivo = "figurinhas/" + titulo.replaceAll(" ", "_") + ".png";

      String textoFigurinha;
      InputStream inputStreamImagemSobreposicao;
      if (classificacao >= 8.0) {
        textoFigurinha = "TOPZERA";
        inputStreamImagemSobreposicao = new FileInputStream("pastaSobreposicao/thumbs_up.png");
      } else {
        textoFigurinha = "HMMMMM...";
        inputStreamImagemSobreposicao = new FileInputStream("pastaSobreposicao/thumbs_down.png");
      }
      
      InputStream inputStream = new URL(urlImagem).openStream();

      geradora.cria(inputStream, nomeArquivo, textoFigurinha, inputStreamImagemSobreposicao);
      
      System.out.println("\u001b[1mTítulo:\u001b[m " + titulo);
      System.out.println();
    }
  }
}
