package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserUpdateService;
import vo.ActionForward;
import vo.Address;
import vo.UserBean;

public class UserUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// [ id값 가져오는 방법 2가지 ] --------------------------------
		// 1. Session영역에서 가져오기
		// HttpSession session = request.getSession();
		// String u_id = (String)session.getAttribute("u_id");
		 
		
		// 2. 파라미터(request에 담겨져온)로 전송받은값 가져오기
		String u_id = request.getParameter("u_id");
		String u_name = request.getParameter("u_name");
		String u_email = request.getParameter("u_email");
		String u_call = request.getParameter("u_call");
		
		int postcode = Integer.parseInt(request.getParameter("postcode"));
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		
		// 기본값으로 채워진 UserBean객체 생성
		UserBean user = new UserBean();
		// 파라미터로 전송된 값들로 다시 채움
		user.setU_id(u_id);
		user.setU_name(u_name);
		user.setU_email(u_email);
		user.setU_call(u_call);
		
		// 만들어진 생성자 사용 (값을 바로 넣을수 있어 편리함)
		Address addr = new Address(u_id, postcode, address1, address2);
		
		UserUpdateService userUpdateService = new UserUpdateService();
		boolean isUserUpdateSuccess = userUpdateService.userUpdate(user, addr);
		
		if(isUserUpdateSuccess == false) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원정보 수정에 실패했습니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			// 로그인이되고있는동안 회원이름을 변경하면 페이지마다 보이는 회원이름이 변경되어야함 -> 세션영역에 넣음
			HttpSession session = request.getSession();
			session.setAttribute("u_name", u_name);
			
			request.setAttribute("u_name", u_name);
			request.setAttribute("showPage", "user/userUpdateComplete.jsp");
			
			forward = new ActionForward("userTemplate.jsp", false); // 디스패치방식으로 포워딩
		}
		
		return forward;
	}

}
