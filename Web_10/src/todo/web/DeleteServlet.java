package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDAO;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TodoDAO dao = new TodoDAO();
		String paramId = request.getParameter("id");
		try {
			dao.getConnection();
			int id = Integer.parseInt(paramId);
			int result = dao.delete(id);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}

		setMessage(request,"タスク["+ paramId +"]の削除処理が完了しました。");
		RequestDispatcher rd = request.getRequestDispatcher("/todo/search");
		rd.forward(request, response);
	}

	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}
}