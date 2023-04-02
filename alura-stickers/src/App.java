import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
  public static void main(String[] args) throws Exception {

    // fazer uma conexão HTTP e buscar os top 250 filmes

    // Top 10 Movies
    // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";

    // Most popular videos
    // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
    // ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

    // Nasa
    String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-06-12&end_date=2022-06-14";
    ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

    var http = new ClienteHttp();
    String json = http.buscaDados(url);
    
    // exibir e manipular os dados
    List<Conteudo> conteudos = extrator.extraiConteudos(json);

    GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();

    // verifica se o diretório de saída das figurinhas já existe
    File diretorio = new File("figurinhas/");
    diretorio.mkdir();

    for (int i = 0; i < 3; i++) {

      Conteudo conteudo = conteudos.get(i);

      String nomeArquivo = "figurinhas/" + conteudo.getTitulo().replaceAll(" ", "_") + ".png";
      InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();

      geradora.cria(inputStream, nomeArquivo);

      System.out.println("\u001b[1mTítulo:\u001b[m " + conteudo.getTitulo());
      System.out.println();
    }
  }
}
