package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.admin.MenuDeleteService;
import vo.ActionForward;

public class MenuDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String m_id = request.getParameter("m_id");
		String category = request.getParameter("category");
		
		if(m_id != null) { // 해당 m_id가 있으면
			
			MenuDeleteService menuDeleteService = new MenuDeleteService();
			boolean isMenuDeleteSuccess = menuDeleteService.deleteMenu(m_id);
			
			if(isMenuDeleteSuccess) {
				if(category.equals("set")) {
					forward = new ActionForward("set.adm", true); // true : insert된 값을 ArrayList에 추가한 후 "set.adm"요청 후 새롭게 가져오므로
				}else if(category.equals("burger")) {
					forward = new ActionForward("burger.adm", true); 
				}else if(category.equals("drink")) {
					forward = new ActionForward("drink.adm", true); 
				}else if(category.equals("side")) {
					forward = new ActionForward("side.adm", true); 
				}else if(category.equals("dessert")) {
					forward = new ActionForward("dessert.adm", true); 
				}
			}else {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('메뉴삭제에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			
		}else { // 해당 m_id가 없으면
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제하려는 메뉴는 존재하지않습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		return forward;
	}

}
