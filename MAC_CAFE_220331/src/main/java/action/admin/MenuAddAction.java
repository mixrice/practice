package action.admin;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import svc.admin.MenuAddService;
import svc.menu.MenuViewService;
import vo.ActionForward;
import vo.Menu;

public class MenuAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		int maxSize = 1024 * 1024 * 5;// 한번에 올릴 수 있는 최대용량은 5M로 제한
		
		// 파일에 업로드할 서버상의 디렉토리 경로 (webapp에 있는 images파일)
		String uploadFolder = "/images";
		ServletContext context =  request.getServletContext();
		String realFolder = context.getRealPath(uploadFolder);
		
		MultipartRequest multi =  new MultipartRequest(request, realFolder, maxSize, "utf-8", new DefaultFileRenamePolicy());
		
		// 파라미터로 전송된 m_id로 제품의 상세정보를 얻어와 신메뉴인지 아닌지를 판별한다.
		String m_id = multi.getParameter("m_id");
		MenuViewService menuViewService = new MenuViewService();
		Menu checkMenu = menuViewService.getMenuView(m_id);
		
		String category = multi.getParameter("category");
		String m_name = multi.getParameter("m_name");
		int m_price = Integer.parseInt(multi.getParameter("m_price"));
		String m_detail = multi.getParameter("m_detail");
		String m_status = multi.getParameter("m_status");
		String image = multi.getOriginalFileName("image"); // 서버상에 업로드된 원래파일이름을 얻어와
		
		if(checkMenu == null) { // 신메뉴이면
			Menu newMenu = new Menu(m_id, category, m_name, m_price, m_detail, m_status, image);
			
			MenuAddService menuAddService = new MenuAddService(); // 관리자만 신메뉴를 등록할 수 있으므로 svc.admin 클래스에 넣어야함
			boolean isAddMenuSuccess = menuAddService.addMenu(newMenu);
			
			if(isAddMenuSuccess) { // 메뉴등록에 성공했으면
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
				out.println("alert('메뉴등록에 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			
		}else { // 신메뉴가 아니면
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록하려는 메뉴가 이미 존재합니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		
		return forward;
	}

}
