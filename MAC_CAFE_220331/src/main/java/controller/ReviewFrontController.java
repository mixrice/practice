package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.review.ReviewDeleteAction;
import action.review.ReviewUpdateAction;
import action.review.ReviewUpdateFormAction;
import action.review.ReviewWriteAction;
import vo.ActionForward;

/**
 * Servlet implementation class UserFrontController
 */
@WebServlet("*.re")
public class ReviewFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewFrontController() {
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
		
		System.out.println("[Review]command : " + command);
		
		/*
		 * 
		 */
		
		/* -- [사용자 모드] '리뷰등록' 요청 -------------------------------------------------*/
		if(command.equals("/reviewWrite.re")) { // '리뷰등록'요청이면
			action = new ReviewWriteAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '리뷰등록' 요청 -------------------------------------------------*/
		else if(command.equals("/reviewWrite.re")) { // '리뷰등록'요청이면
			action = new ReviewWriteAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '리뷰 수정 폼보기' -> 처리요청 -------------------------------------------------*/
		else if(command.equals("/reviewUpdateForm.re")) { // '리뷰 수정 폼보기'요청이면
			action = new ReviewUpdateFormAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/reviewUpdate.re")) { // '리뷰 수정 처리'요청이면
			action = new ReviewUpdateAction(); 
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* -- [사용자 모드] '리뷰 삭체' 처리요청 -------------------------------------------------*/
		else if(command.equals("/reviewDelete.re")) { // '리뷰 삭제 폼보기'요청이면
			action = new ReviewDeleteAction(); 
			
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
