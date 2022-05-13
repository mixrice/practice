package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import action.admin.AdminDeleteAction;
import action.admin.AdminHashPwChangeAction;
import action.admin.AdminHashPwFindAction;
import action.admin.AdminIdFindAction;
import action.admin.AdminJoinAction;
import action.admin.AdminLoginAction;
import action.admin.AdminUpdateAction;
import action.admin.AdminViewAction;
import action.admin.BurgerAdminListAction;
import action.admin.DessertAdminListAction;
import action.admin.DrinkAdminListAction;
import action.admin.MenuAddAction;
import action.admin.MenuAddFormAction;
import action.admin.MenuDeleteAction;
import action.admin.MenuManageAction;
import action.admin.MenuUpdateAction;
import action.admin.MenuUpdateFormAction;
import action.admin.SetAdminListAction;
import action.admin.SideAdminListAction;
import vo.ActionForward;

/**
 * Servlet implementation class UserFrontController
 */
@WebServlet("*.adm")
public class AdminFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFrontController() {
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
		
		System.out.println("[Admin]command : " + command);
		
		/*
		 * 
		 */
		if(command.equals("/adminMain.adm")) { // index.jsp에서 userMain(관리자모드)을 클릭하면
			forward = new ActionForward("adminMain.jsp", false); // 'index.jsp에서 adminMain.jsp뷰페이지' 보기 요청이면
		}
		
		/* -- '관리자 로그인 폼 보기' -> 처리 -------------------------------------------------*/
		else if(command.equals("/adminLogin.adm")) { // 관리자'로그인 폼 보기'요청이면
			
			request.setAttribute("showAdmin", "/admin/adminLoginForm.jsp"); // showAdmin(보여줄 페이지)안에 "/user/logingForm.jsp" 저장
			forward = new ActionForward("adminMain.jsp", false); // ※ user와 다른점 : adminTemplate.jsp를 만들지 않고 adminMain.jsp에서 바로 처리하도록 만들었음
			
		}
		else if(command.equals("/adminLoginAction.adm")) { // 관리자'로그인 처리'요청이면
			action = new AdminLoginAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* -- 'id 찾기' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/userIdFindForm.adm")) { // 'id 찾기 폼보기'요청이면
			
			request.setAttribute("showAdmin", "admin/adminIdFind.jsp"); // showAdmin(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("adminMain.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/adminIdFindAction.adm")) { // '아이디 찾기 처리'요청이면
			action = new AdminIdFindAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* -- '암호화된 비밀번호 찾기' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/adminHashPwFindForm.adm")) { // '비밀번호 찾기 폼보기'요청이면
			
			request.setAttribute("showAdmin", "admin/adminHashPwFind.jsp"); // showAdmin(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("adminMain.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/adminHashPwFindAction.adm")) { // '비밀번호 찾기 처리'요청이면
			action = new AdminHashPwFindAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* -- '암호화된 비밀번호 변경' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/adminHashPwChangeForm.adm")) { // '비밀번호 변경 폼보기'요청이면
			
			request.setAttribute("showAdmin", "admin/adminHashPwChange.jsp"); // showAdmin(보여줄 페이지)안에 "user/logingIdFind.jsp" 저장
			forward = new ActionForward("adminMain.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/adminHashPwChangeAction.adm")) { // '비밀번호 찾기 처리'요청이면
			action = new AdminHashPwChangeAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* -- '관리자가입' 폼보기 -> 처리 -------------------------------------------------*/
		else if(command.equals("/adminJoin.adm")) { // '관리자가입 폼보기'요청이면
			
			request.setAttribute("showAdmin", "admin/adminJoinForm.jsp");
			forward = new ActionForward("adminMain.jsp", false); // false : 요청을 그대로 전달해야지 action에서 처리가 가능하기 때문
			
		}
		else if(command.equals("/adminJoinAction.adm")) { // '회원가입'요청이면
			action = new AdminJoinAction(); // Action 인터페이스를 부모로해서 실행메서드(DogListAction.java의 execute())를 재정의함
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* -- '로그아웃' -> 처리 후 adminMain.jsp -------------------------------------------------*/
		else if(command.equals("/adminLogout.adm")) {
			// 세션 영역에 저장된 속성을 제거하기 위해 기존 session 객체를 얻어와
			HttpSession session = request.getSession();
			//session.invalidate(); // 세션의 모든 속성 제거- 금지 (세션에 저장된 사용자 속성도 제거되므로)
			session.removeAttribute("a_id"); // 하나씩 제거
			session.removeAttribute("a_password");
			session.removeAttribute("a_grade");
			session.removeAttribute("a_name");
			
			//forward = new ActionForward("userTemplate.jsp", true); // true : 리다이렉트 방식
			forward = new ActionForward("adminMain.jsp", true);
		}
		
		/* -- '관리자 한 명 상세 정보 폼 보기' -> 정보 수정 처리 -------------------------------------------------*/
		else if(command.equals("/adminView.adm")) { // '사용자 한명 상세 정보 폼 보기'요청이면
			//request.setAttribute("showAdmin", "user/userView.jsp");
			//forward = new ActionForward("userTemplate.jsp", false);
			action = new AdminViewAction();
			
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/adminUpdate.adm")) { // '사용자 한 명 상세 정보 수정 요청이면' (Action 빼기)
			action = new AdminUpdateAction();
			
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '관리자탈퇴' 처리 -------------------------------------------------*/
		else if(command.equals("/adminDelete.adm")) { // '회원탈퇴'요청이면
			action = new AdminDeleteAction();
			
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/******************************************************************
		 **   메뉴관리
		 *******************************************************************/
		
		/* -- '메뉴관리' 처리 -------------------------------------------------*/
		else if(command.equals("/menuManage.adm")) { // '메뉴관리'요청이면
			/* 메뉴관리 요청은은 Admin만 할수 있도록 세션영역의 grade를 얻어와 Admin인지 확인 */
			action = new MenuManageAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴관리' -> set 요청이면 -------------------------------------------------*/
		else if(command.equals("/set.adm")) { // '메뉴관리 중 set'요청이면
			
			action = new SetAdminListAction(); // 관리자가 등록한 setList가 보여져야함.
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴관리' -> burger 요청이면 -------------------------------------------------*/
		else if(command.equals("/burger.adm")) { // '메뉴관리 중 burger'요청이면
			
			action = new BurgerAdminListAction(); // 관리자가 등록한 burgerList가 보여져야함.
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴관리' -> drink 요청이면 -------------------------------------------------*/
		else if(command.equals("/drink.adm")) { // '메뉴관리 중 drink'요청이면
			
			action = new DrinkAdminListAction(); // 관리자가 등록한 drinkList가 보여져야함.
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		/* -- '메뉴관리' -> side 요청이면 -------------------------------------------------*/
		else if(command.equals("/side.adm")) { // '메뉴관리 중 side'요청이면
			
			action = new SideAdminListAction(); // 관리자가 등록한 sideList가 보여져야함.
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴관리' -> dessert 요청이면 -------------------------------------------------*/
		else if(command.equals("/dessert.adm")) { // '메뉴관리 중 dessert'요청이면
			
			action = new DessertAdminListAction(); // 관리자가 등록한 dessertList가 보여져야함.
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴관리' 클릭-> 메뉴추가 폼보기 요청 -> 메뉴추가'처리'요청이면 -------------------------------------------------*/
		else if(command.equals("/menuAddForm.adm")) { // '메뉴관리 중 메뉴추가 폼보기'요청이면
			
			action = new MenuAddFormAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/menuAdd.adm")) { // '메뉴추가 처리' 요청이면
			
			action = new MenuAddAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴수정' 클릭 -> '메뉴수정' 폼보기 요청(이때, '해당 메뉴'의 정보를 얻어와 폼에 셋팅) -> 메뉴수정 '처리' 요청 ----------------------------*/
		else if(command.equals("/menuUpdateForm.adm")) { // '메뉴관리' -> '메뉴추가' -> '메뉴수정 폼' 보기요청이면
			
			action = new MenuUpdateFormAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/menuUpdate.adm")) { // '메뉴수정 처리' 요청이면
			
			action = new MenuUpdateAction();
			try {
				forward = action.execute(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- '메뉴삭제' 클릭 -> '메뉴삭제' 처리 요청 ----------------------------*/
		else if(command.equals("/menuDelete.adm")) { // '메뉴관리' -> '메뉴삭제 처리' 요청이면
			
			action = new MenuDeleteAction();
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
