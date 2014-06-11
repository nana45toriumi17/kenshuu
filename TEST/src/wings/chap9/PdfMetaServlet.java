package wings.chap9;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PdfMetaServlet
 */
@WebServlet("/PdfMetaServlet.pdf")
public class PdfMetaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Document doc = new Document(PageSize.A4, 50,20,270,20);
			PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());

			doc.addTitle("iTextサンプル");
			doc.addSubject("iTextのサンプルです。");
			doc.addAuthor("YAMADA, Yoshihiro");
			doc.addCreator("iText");
			doc.addKeywords("iText, JSP, サーブレット");
			doc.open();
			Font fnt = new Font(BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED), 18, Font.BOLD);
			doc.add(new Paragraph("こんにちは、iText！", fnt));
			doc.close();
		} catch (DocumentException e) {
			throw new ServletException(e);
		}
	}
}
