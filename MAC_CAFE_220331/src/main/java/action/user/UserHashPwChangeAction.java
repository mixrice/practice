package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserHashPwChangeService;
import vo.ActionForward;
import vo.UserPwChange;

public class UserHashPwChangeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String u_id = request.getParameter("u_id"); // 파라미터로 전송된 아이디(hidden)와
		String pre_password = request.getParameter("pre_password");// 이전 비밀번호와
		String new_password = request.getParameter("new_password");// 새 비밀번호를 얻어와
		
		// [방법 1] : 미리 비번 암호화해서 객체 생성(u_id, pre_password, 암호화된 new_password)해서 암호화된 비밀번호로 업데이트
		// UserPwChange 새 DTO 생성 (새 비밀번호 값을 담아야할 변수가 필요하기 때문)
		//UserPwChange userPwChange = new UserPwChange(u_id, pre_password, SHA256.encodeSHA256(new_password)); // SHA256.encodeSHA256(new_password) : 미리 암호화한 비번을 넣음
		
		// [방법 2] : 비번을 암호화 하지않고 객체(u_id, pre_password, 암호화안된new_password) 생성   (DTO에 가서 암호화해서 업데이트) 
		UserPwChange userPwChange = new UserPwChange(u_id, pre_password, new_password); // new_password : 암호화되지않은 비번 넣음
		
		// [방법 3] : 생성자에서 비번을 암호화해서 객체(u_id, pre_password, 암호화안된 new_password) 생성
		//UserPwChange userPwChange = new UserPwChange(u_id, pre_password, new_password); // new_password : 암호화되지않은 비번 넣음
		
		UserHashPwChangeService userHashPwChangeService = new UserHashPwChangeService();
		boolean isChangePwSuccess = userHashPwChangeService.changePw(userPwChange);
		
		if(isChangePwSuccess == false) {
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요');");
			out.println("history.back();");
			out.println("</script>");
			
		}else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('비밀번호 변경에 성공했습니다. 다시 로그인해주세요');");
			out.println("location.href ='userLogin.usr'"); //[방법1] 다시 로그인 요청 (forward값은 null로 전송됨) 
			out.println("</script>");
			
			//forward = new ActionForward("userLogin.usr", true); // [방법2] 리다이렉트 방식으로 포워딩 (DB를 수정했기 때문)
		}
		
		return forward;
	}

}
