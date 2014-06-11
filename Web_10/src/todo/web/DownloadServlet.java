package todo.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDAO;
import todo.vo.TodoValueObject;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/todo/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		TodoDAO dao = new TodoDAO();
		TodoValueObject vo = null;
		try {
			dao.getConnection();
			vo = dao.detail(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}

		String filename = vo.getFilename();
		if (filename == null || "".equals(filename)) {
			request.setAttribute("message", "ファイルは添付されていません");
			request.getRequestDispatcher("/todo/search?id=" + id).forward(request, response);
			return;
		}
		File downloadFile = new File("C:/tmp/" + filename);
		FileInputStream fis = new FileInputStream(downloadFile);
		BufferedInputStream buf = new BufferedInputStream(fis);

		filename = URLEncoder.encode(filename, "utf-8");
		response.setContentType("application/octet-stream; charset=\"utf-8\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

		ServletOutputStream out = response.getOutputStream();
		int length = 0;
		byte[] buffer = new byte[1024];
		while ((length = buf.read(buffer)) >= 0) {
			out.write(buffer, 0, length);
		}
		out.close();
		out.flush();
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
