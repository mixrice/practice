package action.review;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuViewService;
import svc.review.ReviewListService;
import svc.review.ReviewUpdateService;
import vo.ActionForward;
import vo.Menu;
import vo.Review;

public class ReviewUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// hidden으로 전송된 파라미터 값들
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		String u_id = request.getParameter("u_id");
		String m_id = request.getParameter("m_id");
		
		int rating = Integer.parseInt(request.getParameter("rating"));
		String text = request.getParameter("text");
		
		 Review review = new Review(review_num, u_id, m_id, rating, text);
		 
		 if(rating == 0) {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('평점을 선택해주세요.');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				// 해당 메뉴에 사용자가 작성한 리뷰수정
				ReviewUpdateService reviewUpdateService = new ReviewUpdateService(); 
				boolean isReviewWriteSuccess = reviewUpdateService.updateReviewList(review);
				
				if(!isReviewWriteSuccess) { // 리뷰수정 실패하면
					response.setContentType("text/html; charset=utf-8;");
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("alert('리뷰수정 실패!');");
					out.println("history.back();");
					out.println("</script>");
				}else { //리뷰수정 성공하면
					// 해당메뉴의 정보를 얻어와 request객체에 공유하고
					MenuViewService menuViewService = new MenuViewService();
					Menu menuInfo = menuViewService.getMenuView(m_id);
					request.setAttribute("menuInfo", menuInfo);
					
					// 작성된 리뷰들(사용자가 방금 등록한 리뷰를 포함한)을 얻어와 request객체에 공유하고
					ReviewListService reviewListService = new ReviewListService();
					ArrayList<Review> reviewList = reviewListService.getReviewList(m_id); // 사용자가 입력한 리뷰들을 ArrayList로 가져옴
					
					request.setAttribute("reviewList", reviewList);
					
					request.setAttribute("showMenu", "/kiosk/menuView.jsp"); // 상세메뉴보기 (menuInfo의 정보를 출력)
					
					request.setAttribute("reviewPage", "/review/reviewTemplate.jsp"); 
					request.setAttribute("showReview", "/review/showReview.jsp"); // 상세메뉴보기 밑에 리뷰들을 뿌림 (reviewList의 정보를 출력)
					
					request.setAttribute("m_id", m_id);
					request.setAttribute("u_id", u_id);
					
					forward = new ActionForward("menuTemplate.jsp", false);
				}
			}
		
		return forward;
	}

}
