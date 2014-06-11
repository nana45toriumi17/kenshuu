package todo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import todo.dao.TodoDAO;
import todo.vo.TodoValueObject;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(urlPatterns={"/todo/upload"})
@MultipartConfig(location="C:/tmp/")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Part part = request.getPart("uploadfile");
		String filename = null;
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			cd = cd.trim();

			if (cd.startsWith("filename")) {
				filename = cd.substring(cd.indexOf("=") +1).trim().replace("\"", "");
				break;
			}
		}
		String idStr = request.getParameter("id");
		log("idStr:"+ idStr);
		int id = Integer.parseInt(idStr);

		String message = null;
		if (filename != null) {
			filename = filename.replace("\\", "/");
			int pos = filename.lastIndexOf("/");
			if (pos >= 0) {
				filename = filename.substring(pos+1);
			}
			part.write(filename);

			TodoValueObject vo = new TodoValueObject();
			vo.setId(id);
			vo.setFilename(filename);

			TodoDAO dao = new TodoDAO();
			try {
				dao.getConnection();
				int result = dao.updateUploadInfo(vo);
				vo = dao.detail(result);
				request.setAttribute("vo", vo);
			} catch (Exception e) {
				throw new ServletException(e);
			} finally {
				dao.closeConnection();
			}
			message = "["+ filename +"]のアップロードが完了しました";
		} else {
			message = "アップロードが失敗しました";
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/todo/detail?id=" + id).forward(request, response);
	}
}