import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {

  public void cria(InputStream inputStream, String nomeArquivo, String texto, InputStream inputStreamSobreposicao) throws Exception {

    // leitura da imagem
    // InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"))
    // InputStream inputStream = new
    // URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg").openStream();
    BufferedImage imagemOriginal = ImageIO.read(inputStream);

    // cria nova imagem em memória com transparência e com tamanho novo
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();
    int alturaAdicional = 200;
    int novaAltura = altura + alturaAdicional;
    BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);
    
    // copiar imagem original pra nova imagem (em memória)
    Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
    graphics.drawImage(imagemOriginal, 0, 0, null);
    
    BufferedImage imageSobreposicao = ImageIO.read(inputStreamSobreposicao);
    int posicaoImagemSobreposicaoY = novaAltura - imageSobreposicao.getHeight();
    graphics.drawImage(imageSobreposicao, 0, posicaoImagemSobreposicaoY, null);

    // configurar a fonte
    Font fonte = new Font("Impact", Font.BOLD, 80);
    graphics.setColor(Color.YELLOW);
    graphics.setFont(fonte);

    // escrever uma frase na nova imagem
    FontMetrics fontMetrics = graphics.getFontMetrics();
    Rectangle2D retanguloFonte = fontMetrics.getStringBounds(texto, graphics);
    int larguraFonte = (int) retanguloFonte.getWidth();

    // definições do eixos X e Y do texto
    int posicaoTextoX = (largura - larguraFonte) / 2;
    int posicaoTextoY = novaAltura - 100;
    // desenha o texto
    graphics.drawString(texto, posicaoTextoX, posicaoTextoY);
    
    FontRenderContext fontRenderContext = graphics.getFontRenderContext();
    TextLayout textLayout = new TextLayout(texto, fonte,fontRenderContext);
    
    Shape outline = textLayout.getOutline(null);
    AffineTransform transform = graphics.getTransform();
    transform.translate(posicaoTextoX, posicaoTextoY);
    graphics.setTransform(transform);

    BasicStroke outlineStroke = new BasicStroke(largura * 0.004f);
    graphics.setStroke(outlineStroke);

    graphics.setColor(Color.BLACK);
    graphics.draw(outline);
    graphics.setClip(outline);

    // escrever a nova imagem em um arquivo
    ImageIO.write(novaImagem, "png", new File(nomeArquivo));
  }
}
