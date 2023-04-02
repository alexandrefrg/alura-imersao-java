import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
  public static void main(String[] args) throws Exception {

    API api = API.NASA;
    // API api = API.IMDB_TOP_MOVIES;

    String url = api.getUrl();
    ExtratorDeConteudo extrator = api.getExtrator();

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

      String nomeArquivo = "figurinhas/" + conteudo.titulo().replaceAll(" ", "_") + ".png";
      InputStream inputStream = new URL(conteudo.urlImagem()).openStream();

      geradora.cria(inputStream, nomeArquivo);

      System.out.println("\u001b[1mTítulo:\u001b[m " + conteudo.titulo());
      System.out.println();
    }
  }
}
