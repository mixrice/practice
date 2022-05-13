package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.user.UserDeleteAction;
import action.user.UserHashPwChangeAction;
import action.user.UserHashPwFindAction;
import action.user.UserIdFindAction;
import action.user.UserJoinAction;
import action.user.UserLoginAction;
import action.user.UserUpdateAction;
import action.user.UserViewAction;
import vo.ActionForward;

/**
 * Servlet implementation class UserFrontController
 */
@WebServlet("*.usr")
public class UserFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response); // doGet이든 doPost든 doProcess에서 처리함
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response); // doGet이든 doPost든 doProcess에서 처리함
	}
	
	// 이 서블릿으로 들어오는 모든 요청은 doProcess()를 호출하여 처리
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // ★ 반드시 첫줄
		
		// 요청객체로부터 '프로젝트명+파일경로'를 가져옴 (예) /project/userLogin.usr 
		String requestURI = request.getRequestURI();
		
		// 요청객체로부터 'contextPath'를 가져옴 (예) /project
		String contextPath = request.getContextPath();
		
		/*
		 * [ dogList.dog(어떤요청이 왔는지 판단을 하는 단서)만 추출하기 위한 작업 ]
		 * 
		 *    /project/userLogin.usr   - /project =  /userLogin.usr 
		 * 
		 */
		
		String command = requestURI.substring(contextPath.length()); // 시작인덱스(index : 8 (두번째 / )) ~ 끝까지 부분문자열 생성
		
		/*
		 * 컨트롤러에서 요청이 파악되면 해당 요청을 처리하는 각 Action클래스를 사용해서 요청 처리
		 * 각 요청에 해당하는 Action클래스 객체들을 다형성을 이용해서 동일한 타입인 Action으로 참조하기 위해
		 */
		Action action = null;  // Action부모 인터페이스 = Action을 구현한 객체
		ActionForward forward = null;
		
		System.out.println("[User]command : " + command);
		
		/*
		 * 
		 */
		if(command.equals("/userMain.usr")) { // index.jsp에서 userMain(사용자모드)을 클릭하면
			forward = new ActionForward("userMain.jsp", false); // 'index.jsp에서 userMain.jsp뷰페이지' 보기 요청이면
		}
		
		/* -- '로그인 폼 보기' -> 처리 -------------------------------------------------*/
		else if(command.equals("/userLogin.usr")) { // '로그인 폼 보기'요청이면
			
			request.setAttribute("showPage", "/user/logingForm.jsp"); // showPage(보여줄 페이지)안에 "/user/logingForm.jsp" 저장
			forward = new ActionForward("userTemplate.jsp", false);
			
		}
		else if(command.equals("/userLoginAction.usr")) { // '로그인 처리'요청이면
			action = new UserLoginAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* -- 'id 찾기' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/userIdFindForm.usr")) { // 'id 찾기 폼보기'요청이면
			
			request.setAttribute("showPage", "user/userIdFind.jsp"); // showPage(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("userTemplate.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/userIdFindAction.usr")) { // '아이디 찾기 처리'요청이면
			action = new UserIdFindAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* -- '암호화된 비밀번호 찾기' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/userHashPwFindForm.usr")) { // '비밀번호 찾기 폼보기'요청이면
			
			request.setAttribute("showPage", "user/userHashPwFind.jsp"); // showPage(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("userTemplate.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/userHashPwFindAction.usr")) { // '비밀번호 찾기 처리'요청이면
			action = new UserHashPwFindAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* -- '암호화된 비밀번호 변경' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/userHashPwChangeForm.usr")) { // '비밀번호 변경 폼보기'요청이면
			
			request.setAttribute("showPage", "user/userHashPwChange.jsp"); // showPage(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("userTemplate.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/userHashPwChangeAction.usr")) { // '비밀번호 찾기 처리'요청이면
			action = new UserHashPwChangeAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* -- '회원가입' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/userJoin.usr")) { // '회원가입 폼보기'요청이면
			
			request.setAttribute("showPage", "user/joinForm.jsp"); // showPage(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("userTemplate.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/userJoinAction.usr")) { // '회원가입'요청이면
			action = new UserJoinAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* -- '로그아웃' -> 처리 후 userMain.jsp -------------------------------------------------*/
		else if(command.equals("/userLogout.usr")) {
			// 세션 영역에 저장된 속성을 제거하기 위해 기존 session 객체를 얻어와
			HttpSession session = request.getSession();
			//session.invalidate(); // 세션의 모든 속성 제거하거나
			session.removeAttribute("u_id"); // 하나씩 제거
			session.removeAttribute("u_password");
			session.removeAttribute("u_grade");
			session.removeAttribute("u_name");
			session.removeAttribute("u_email");
			
			//forward = new ActionForward("userTemplate.jsp", true); // true : 리다이렉트 방식
			forward = new ActionForward("userMain.jsp", true);
		}
		
		/* -- '사용자 한 명 상세 정보 폼 보기' -> 정보 수정 처리 -------------------------------------------------*/
		else if(command.equals("/userView.usr")) { // '사용자 한명 상세 정보 폼 보기'요청이면
			//request.setAttribute("showPage", "user/userView.jsp");
			//forward = new ActionForward("userTemplate.jsp", false);
			action = new UserViewAction();
			
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/userUpdate.usr")) { // '사용자 한 명 상세 정보 수정 요청이면' (Action 빼기)
			action = new UserUpdateAction();
			
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '회원탈퇴' 처리 -------------------------------------------------*/
		else if(command.equals("/userDelete.usr")) { // '회원탈퇴'요청이면
			action = new UserDeleteAction();
			
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		/******************************************************************
		 **   포워딩
		 *******************************************************************/
		if(forward != null) {
			//if(forward.isRedirect())
			if(forward.isRedirect() == true) { // isRedirect() == true이면 리다이렉트(새요청) 방식 -> 기존 request 공유 못함
				response.sendRedirect(forward.getPath());
			}else {
				//RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath()); // *.jsp
				// 기존요청, 기존응답을 매개값으로 그대로 전달하므로 request 공유할 수 있다.
				//dispatcher.forward(request, response);
				
				request.getRequestDispatcher(forward.getPath()).forward(request, response);
			}
		}
		
	}

}
