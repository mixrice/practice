package action.menu;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.menu.MenuCartAddService;
import vo.ActionForward;
import vo.Menu;

public class MenuCartAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// '장바구니 담기'위해 로그인된 상태인지 확인해야 함
		HttpSession session = request.getSession();
		String u_id = (String)session.getAttribute("u_id");
		String m_id = request.getParameter("m_id");
		
		if(u_id == null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('로그인 후 이용해 주세요');");
			out.println("location.href = 'userLogin.usr'");
			out.println("</script>");
		}else {
			MenuCartAddService menuCartAddService = new MenuCartAddService();
			Menu menuInfo = menuCartAddService.getMenuView(m_id);
			
			// request 전송이유 ? 장바구니 하목을 유지하기 위해 session 영역에 추가해야하므로
			menuCartAddService.addCart(request, menuInfo); // 장바구니 항목을 세션에 담아야하므로 request를 전송해야함
			
			// ★ 반드시 '리다이렉트'로 포워딩 : 장바구니 항목에 새롭게 추가했으므로
			forward = new ActionForward("menuCartList.kiosk", true); // 장바구니 항목보기
		}
		
		return forward;
	}
}
