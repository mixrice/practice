package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;

import action.menu.BurgerListAction;
import action.menu.DessertListAction;
import action.menu.DrinkListAction;
import action.menu.MenuAction;
import action.menu.MenuCartAddAction;
import action.menu.MenuCartListAction;
import action.menu.MenuCartOrderAction;
import action.menu.MenuCartQtyDownAction;
import action.menu.MenuCartQtyUpAction;
import action.menu.MenuCartRemoveAction;
import action.menu.MenuCartRemoveAllAction;
import action.menu.MenuViewAction;
import action.menu.SetListAction;
import action.menu.SideListAction;
import vo.ActionForward;

/**
 * Servlet implementation class UserFrontController
 */
@WebServlet("*.kiosk")
public class MenuFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuFrontController() {
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
		
		System.out.println("[Menu]command : " + command);
		
		/*
		 * 
		 */
		
		/* -- [사용자 모드] '주문하기' 요청 -------------------------------------------------*/
		if(command.equals("/menu.kiosk")) { // '주문하기'요청이면
			action = new MenuAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '주문하기' 뷰페이지(menuTemplate.jsp)에서 '세트' 클릭하면 ----------------------*/
		else if(command.equals("/set.kiosk")) { // '사용자 모드 : 'set 주문하기' 요청이면
			action = new SetListAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '주문하기' 뷰페이지(menuTemplate.jsp)에서 '버거' 클릭하면 ----------------------*/
		else if(command.equals("/burger.kiosk")) { // '사용자 모드 : 'burger 주문하기' 요청이면
			action = new BurgerListAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '주문하기' 뷰페이지(menuTemplate.jsp)에서 '음료' 클릭하면 ----------------------*/
		else if(command.equals("/drink.kiosk")) { // '사용자 모드 : 'drink 주문하기' 요청이면
			action = new DrinkListAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '주문하기' 뷰페이지(menuTemplate.jsp)에서 '사이드' 클릭하면 ----------------------*/
		else if(command.equals("/side.kiosk")) { // '사용자 모드 : 'side 주문하기' 요청이면
			action = new SideListAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '주문하기' 뷰페이지(menuTemplate.jsp)에서 '디저트' 클릭하면 ----------------------*/
		else if(command.equals("/dessert.kiosk")) { // '사용자 모드 : 'dessert 주문하기' 요청이면
			action = new DessertListAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '주문하기' 뷰페이지(menuTemplate.jsp)에서 '해당 상품 이미지'를 클릭 -> 해당 상품의 자세한 정보보기 + 해당 상품의 리뷰 ----*/
		else if(command.equals("/menuView.kiosk")) { // '사용자 모드 : '주문하기'의 '해당상품의 자세한 정보보기' + '해당상품의 리뷰' 요청이면
			action = new MenuViewAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuView.jsp)에서 '장바구니 담기'를 클릭하면 ----*/
		else if(command.equals("/menuCartAdd.kiosk")) { // 사용자 모드 : '장바구니 담기'
			action = new MenuCartAddAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuView.jsp)에서 '장바구니 목록보기'를 클릭하면 ----*/
		else if(command.equals("/menuCartList.kiosk")) { // 사용자 모드 : '장바구니 목록보기'
			action = new MenuCartListAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuCartList.jsp)에서 '수량 1증가 ▲' 요청이면 ----*/
		else if(command.equals("/menuCartQtyUp.kiosk")) { // 사용자 모드 : '수량 1증가 ▲' 요청
			action = new MenuCartQtyUpAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuCartList.jsp)에서 '수량 1감소 ▼' 요청이면 ----*/
		else if(command.equals("/menuCartQtyDown.kiosk")) { // 사용자 모드 : '수량 1감소 ▼' 요청
			action = new MenuCartQtyDownAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuCartList.jsp)에서 '전체삭제' 요청이면 ----*/
		else if(command.equals("/menuCartRemoveAll.kiosk")) { // 사용자 모드 : '전체 삭제' 요청
			action = new MenuCartRemoveAllAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuCartList.jsp)에서 '하나의 메뉴 삭제' 요청이면 ----*/
		else if(command.equals("/menuCartRemove.kiosk")) { // 사용자 모드 : '하나의 메뉴 삭제' 요청
			action = new MenuCartRemoveAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] 뷰페이지(menuCartList.jsp)에서 '[구매하기]' 요청이면 ----*/
		else if(command.equals("/menuCartOrder.kiosk")) { // 사용자 모드 : '[구매하기]' 요청
			action = new MenuCartOrderAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
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
