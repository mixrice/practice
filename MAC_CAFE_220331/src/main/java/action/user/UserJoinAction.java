package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserJoinService;
import vo.ActionForward;
import vo.Address;
import vo.UserBean;

public class UserJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String u_id = request.getParameter("u_id");
		String u_grade = request.getParameter("u_grade");
		String u_password = request.getParameter("u_password");
		String u_name = request.getParameter("u_name");
		String u_email = request.getParameter("u_email");
		String u_call = request.getParameter("u_call");
		
		int postcode = Integer.parseInt(request.getParameter("postcode")); // ★ 우편번호는 int타입
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		
		System.out.println("[UserJoinAction]");
		System.out.println("u_id : " + u_id); // 넘어온 값 확인
		
		// [방법1] 기본값으로 채워진 UserBean객체2 생성

		/*
		UserBean user = new UserBean(); // 기본값으로 채워진 UserBean객체 생성
		
		user.setU_id(u_id);
		user.setU_grade(u_grade); // Normal
		user.setU_password(SHA256.encodeSHA256(u_password)); // 암호화된 비번값 셋팅
		user.setU_name(u_name);
		user.setU_email(u_email);
		user.setU_call(u_call);
		*/
		
		// [방법2] DAO에서 암호화안된 비번을 INSERT하기 전 암호화시킨 후 INSERT
		
		// [방법3] 생성자에서 비번을 암호화시킴
		UserBean user = new UserBean(u_id, u_grade, u_password, u_name, u_email, u_call);
		
		Address addr = new Address(u_id, postcode, address1, address2);
		
		UserJoinService userJoinService = new UserJoinService();
		boolean isJoinSuccess = userJoinService.join(user, addr);
		
		if(isJoinSuccess == false) {
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('회원등록을 실패했습니다. 다시 시도해주세요');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('회원등록을 성공했습니다. 반갑습니다!');");
			// out.println("location.href ='userLogin.usr';"); //[방법1] 로그인 요청 (forward값은 null로 전송됨) 
			out.println("</script>");
			
			forward = new ActionForward("userLogin.usr", true); // [방법2] 리다이렉트 방식으로 포워딩(DB를 수정했기 때문)
		}
		
		return forward;
	}

}
