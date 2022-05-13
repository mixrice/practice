package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuViewService;
import svc.review.ReviewListService;
import vo.ActionForward;
import vo.Menu;
import vo.Review;

public class MenuViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String m_id = request.getParameter("m_id");
		
		/*--- 메뉴 정보 상세보기 ----------------------------*/
		MenuViewService menuViewService = new MenuViewService();
		Menu menuInfo = menuViewService.getMenuView(m_id);
		
		request.setAttribute("menuInfo", menuInfo);
		
		/*--- 리뷰 ---------------------------------------*/
		ReviewListService reviewListService = new ReviewListService();
		ArrayList<Review> reviewList = reviewListService.getReviewList(m_id);
		
		request.setAttribute("m_id", m_id); // reviewTemplate.jsp, writeReview.jsp에 사용
		
		request.setAttribute("reviewList", reviewList);		
		request.setAttribute("reviewPage", "/review/reviewTemplate.jsp");
		request.setAttribute("showReview", "/review/showReview.jsp"); // reviewList에 대한 정보 출력
		
		request.setAttribute("showMenu", "/kiosk/menuView.jsp"); // menuInfo에 대한 정보 출력
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
