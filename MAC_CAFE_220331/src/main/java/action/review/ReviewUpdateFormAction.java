package action.review;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.menu.MenuViewService;
import svc.review.ReviewUpdateFormService;
import vo.ActionForward;
import vo.Menu;
import vo.Review;

public class ReviewUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// 리뷰수정을 위해 파라미터로 전송된 값들을 얻어와
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		String u_id = request.getParameter("u_id").trim();
		String m_id = request.getParameter("m_id");
		
		// session영역에 공유한 u_id를 얻어와
		HttpSession session = request.getSession();
		String check_u_id = (String)session.getAttribute("u_id");
		
		System.out.println("\n*** [ReviewUpdateFormAction]");
		System.out.println(" *** u_id : " + u_id);
		System.out.println(" *** check_u_id : " + check_u_id);
		// 파라미터로 얻어온 u_id와 비교
		if((check_u_id == null) || !(check_u_id.equals(u_id))) { // 같지않으면
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('본인이 작성한 리뷰만 수정이 가능합니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { // 같으면
			// 먼저, 해당 '메뉴정보'얻어와서 request객체에 공유 (리뷰를 수정하더라도 메뉴의 정보는 변하면 안되기때문)
			MenuViewService menuViewService = new MenuViewService();
			Menu menuInfo = menuViewService.getMenuView(m_id); // m_id에 해당하는 메뉴 상세 정보 가져옴
			
			request.setAttribute("menuInfo", menuInfo);
			
			// 해당 리뷰번호(review_num)로 '원본 리뷰의 정보'를 얻어와 request객체에 공유하고 한 후 view페이지(reviewUpdateForm.jsp)에 뿌림
			ReviewUpdateFormService reviewUpdateFormService = new ReviewUpdateFormService();
			Review reviewInfo = reviewUpdateFormService.getReview(review_num);
			
			System.out.println("\n***** 리뷰 내용 :  " + reviewInfo.getText());
			
			// 해당 리류 정보를 request에 공유
			request.setAttribute("reviewInfo", reviewInfo);
			
			// menuInfo의 정보와 reviewPage(reviewUpdateForm.jsp)의 정보 출력
			request.setAttribute("showMenu", "/kiosk/menuView.jsp"); // 상세메뉴보기 (menuInfo의 정보를 출력)
			
			request.setAttribute("reviewPage", "/review/reviewUpdateForm.jsp"); 
			//request.setAttribute("showReview", "/review/showReview.jsp"); // 상세메뉴보기 밑에 리뷰들을 뿌림 (reviewList의 정보를 출력)
			
			request.setAttribute("m_id", m_id);
			request.setAttribute("u_id", u_id);
			
			forward = new ActionForward("menuTemplate.jsp", false);
			
			
		}
		
		return forward;
	}

}
