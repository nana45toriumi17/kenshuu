package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.TodoDAO;
import todo.util.SimpleMailSender;
import todo.vo.TodoValueObject;

/**
 * Servlet implementation class RegisterServlet
 */

@WebServlet("/todo/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String inputLimitdate = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String task = request.getParameter("task");
		String limitdate = request.getParameter("limitdate");
		String userid = request.getParameter("userid");
		int status = Integer.parseInt(request.getParameter("status"));

		TodoValueObject vo = new TodoValueObject();
		vo.setId(id);
		vo.setTitle(title);
		vo.setTask(task);
		vo.setInputLimit(limitdate);
		vo.setUserid(userid);
		vo.setStatus(status);

		TodoDAO dao = new TodoDAO();
		String message = "";

		try {
			dao.getConnection();
			if (id == 0) {
				dao.registerInsert(vo);
				message = "タスクの新規作成処理が完了しました。";
				setMessage(request, message);
			} else {
				dao.registerUpdate(vo);
				message = "タスク["+ id +"]の更新処理が完了しました。";
				setMessage(request, message);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			dao.closeConnection();
		}
		String toAddr = "databasetest1991@yahoo.co.jp";
		String fromAddr = "databasetest1991@yahoo.co.jp";
		String personName = "toriumi";
		String subject = "TODO管理アプリケーションからの報告です";
		SimpleMailSender mail = new SimpleMailSender();
		try{
			mail.sendMessage(toAddr, fromAddr, personName, subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/todo/search");
		rd.forward(request, response);
	}
	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}
}