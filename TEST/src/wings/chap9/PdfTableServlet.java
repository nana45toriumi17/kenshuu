package wings.chap9;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PdfTableServlet
 */
@WebServlet("/PdfTableServlet.pdf")
public class PdfTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Document doc = new Document(PageSize.A4, 50,20,270,20);
			PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
			doc.open();
			Font fTitle = new Font(BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED), 11, Font.BOLD);
			Font fData = new Font(BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-H", BaseFont.NOT_EMBEDDED), 10, Font.BOLD);
			PdfPTable tb1 = new PdfPTable(4);
			int[] ws = {55, 10, 20, 15};
			tb1.setWidths(ws);
			String[] headers = {"書名", "価格", "出版社", "刊行日"};
			for (int i = 0; i < headers.length; i++) {
				PdfPCell c = new PdfPCell(new Phrase(headers[i],fTitle));
				c.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
				tb1.addCell(c);
			}
			tb1.setHeaderRows(1);

			InitialContext context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/dbtest");
			db = ds.getConnection();
			ps = db.prepareStatement("SELECT title, price, publish, published FROM book ORDER BY published DESC");
			rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();

			while (rs.next()) {
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					PdfPCell c = new PdfPCell(new Phrase(rs.getString(i), fData));
					tb1.addCell(c);
				}
			}
			doc.add(tb1);
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}