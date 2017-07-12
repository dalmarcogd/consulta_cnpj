package consulta.cnpj;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Classe dedicada a transformação de String em um documento HTML estruturado para Swing.
 *
 * @author Alexandre Perotto
 */
public class DocumentoHtml {
    /**
     * Método que recebe uma String e a converte para HTMLDocument.
     *
     * @param html
     * @return documento HTML ou nulo
     */
    public static HTMLDocument getHTMLDocument(String html) {
        HTMLEditorKit editorKit = new HTMLEditorKit();
        HTMLDocument document = (HTMLDocument) editorKit.createDefaultDocument();
        document.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
        InputStream inputStream = new ByteArrayInputStream(html.getBytes());
        try {
            editorKit.read(inputStream, document, 0);
        } catch (IOException | BadLocationException ex) {
            return null;
        }
        return document;
    }
}