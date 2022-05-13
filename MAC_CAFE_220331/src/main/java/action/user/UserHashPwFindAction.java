package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserHashPwFindService;
import util.SHA256;
import vo.ActionForward;
import vo.UserBean;

public class UserHashPwFindAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String u_id = request.getParameter("u_id");
		String u_email = request.getParameter("u_email");
		
		UserHashPwFindService userHashPwFindService = new UserHashPwFindService();
		UserBean userInfo = userHashPwFindService.findHashPw(u_id, u_email);
		
		if(userInfo == null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 또는 이메일정보가 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			/************ 임시비밀번호를 사용자의 메일로 보내주도록 구현하기 ***********/
			String random_password = SHA256.getRandomPassword(8); // 8자리 랜덤 임시 비밀번호를 생성하여
			System.out.println("random_password : " + random_password);
			
			// 발급받은 임시비밀번호를 user_table에 설정
			boolean isSetHashPwSuccess = userHashPwFindService.setHashPw(u_id, u_email, random_password); // 메일이 정확하게 일치하는지 확인하기 위해 메일을 보냄
			
			//if(!isSetHashPwSuccess) {
			if(isSetHashPwSuccess == false){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('아이디 또는 이메일정보가 일치하지 않습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				request.setAttribute("u_id", u_id);
				request.setAttribute("random_password", random_password);
				
				request.setAttribute("showPage", "user/findHashPwComplete.jsp");
				forward = new ActionForward("userTemplate.jsp", false); // 디스패치 방식으로 보냄
			}
			
		}
		
		return forward;
	}

}
