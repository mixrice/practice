package action.menu;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuCartRemoveService;
import vo.ActionForward;

public class MenuCartRemoveAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String m_id = request.getParameter("m_id");
		
		if(m_id == null) { // 해당 m_id가 없으면
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('삭제할 메뉴를 선택해 주세요');");
			out.println("history.back();");
			out.println("</script>");
		}else { // 해당 m_id가 있으면
			MenuCartRemoveService menuCartRemoveService = new MenuCartRemoveService();
			menuCartRemoveService.cartRemove(m_id, request);
			
			forward = new ActionForward("menuCartList.kiosk", true);
		}

		return forward;
	}

}
