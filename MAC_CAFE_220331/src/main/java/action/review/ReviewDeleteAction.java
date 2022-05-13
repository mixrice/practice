package action.review;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.menu.MenuViewService;
import svc.review.ReviewDeleteService;
import svc.review.ReviewListService;

import vo.ActionForward;
import vo.Menu;
import vo.Review;

public class ReviewDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward=null;
		
		//리뷰삭제를 위해 파라미터로 전송된 값들을 얻어와
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		String u_id = request.getParameter("u_id").trim();
		String m_id = request.getParameter("m_id");
		
		//session영역에 공유한 u_id를 얻어와 check_u_id변수에 저장하고
		HttpSession session = request.getSession();
		String check_u_id =(String)session.getAttribute("u_id");
		
		//session영역에 공유한 u_id(check_u_id)와 파라미터로 전송된 u_id가 같지 않으면
		if(check_u_id == null || !check_u_id.equals(u_id)) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out =response.getWriter();
			out.println("<script>");
			out.println("alert('본인이 작성한 리뷰만 삭제가 가능합니다.')");
			out.println("history.back();");
			out.println("</script>");			
		}else {
			//해당 메뉴에 대한 사용자가 작성한 리뷰를 DB에서 삭제
			ReviewDeleteService reviewDeleteService=new ReviewDeleteService();
			boolean isDeleteReviewSuccess = reviewDeleteService.deleteReview(review_num,u_id);
			
			if(!isDeleteReviewSuccess) {//리뷰삭제 실패하면
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('리뷰삭제실패')");
				out.println("history.back();");
				out.println("</script>");
			}else {//리뷰삭제 성공하면
				//해당 메뉴의 정보를 얻어와  request객체에 공유하고
				MenuViewService menuViewService = new MenuViewService();
				Menu menuInfo = menuViewService.getMenuView(m_id);
				request.setAttribute("menuInfo", menuInfo);
				
				//작성된 리뷰들(사용자가 방금 삭제한 리뷰는 제외된)을 다시 얻어와  request객체에 공유하고
				ReviewListService reviewListService = new ReviewListService();
				ArrayList<Review> reviewList = reviewListService.getReviewList(m_id);
				request.setAttribute("reviewList", reviewList);
				
				//reviewList의 정보를 출력
				request.setAttribute("showReview", "/review/showReview.jsp");					

				request.setAttribute("reviewPage", "/review/reviewTemplate.jsp");					
				
				//menuInfo의 정보와 reviewPage(reviewTemplate.jsp)의 정보를 출력
				request.setAttribute("showMenu", "/kiosk/menuView.jsp");
									
				request.setAttribute("m_id", m_id);
				request.setAttribute("u_id", u_id);
				
				forward = new ActionForward("menuTemplate.jsp", false);
			}
		}
		return forward;
	}

}
