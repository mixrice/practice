package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserViewService;
import vo.ActionForward;
import vo.Address;
import vo.UserBean;

public class UserViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		/*
		 * 현재 사용자가 로그인된 상태인지 알아보기 위해서 session 객체 얻어옴
		 */
		
		HttpSession session = request.getSession();
		String viewId = (String)session.getAttribute("u_id");
		
		if(viewId == null) { // 현재 로그인 상태가 아니면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 필요한 서비스입니다.');"); // 경고창을 띄우고
			out.println("location='userLogin.usr;'");			
			out.println("</script>");
		}else{ // DB와 연결해 userBean에 정보 담아오기 (항상 정보 가져올떄는 DTO 객체 사용)
			UserViewService userViewService = new UserViewService();
	
			UserBean userInfo = userViewService.getUserInfo(viewId);
			Address userAddrInfo = userViewService.getAddressInfo(viewId);
			
			// 가져온 정보를 request에 저장
			request.setAttribute("user", userInfo);
			request.setAttribute("addr", userAddrInfo);
			request.setAttribute("showPage", "user/userView.jsp");
			
			forward = new ActionForward("userTemplate.jsp", false); // 기존요청 그대로
		}
		
		return forward;
	}

}
