import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDaNasa implements ExtratorDeConteudo{

  public List<Conteudo> extraiConteudos(String json) {

    // extrai só os dados que interessam (titulo, poster, classificação)
    JsonParser parser = new JsonParser();
    List<Map<String, String>> listaDeAtributos = parser.parse(json);

    // popular a lista de conteudos
    return listaDeAtributos.stream()
    .map(atributos -> new Conteudo(atributos.get("title"), atributos.get("url")))
    .toList();
  }
}
