package action.menu;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.menu.MenuCartOrderService;
import svc.user.UserGradeService;
import vo.ActionForward;
import vo.Cart;
import vo.Menu;
import vo.Order;

public class MenuCartOrderAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// 파라미터로 전송된 totalMoney를 아래에세 세일된 값을 계산하기 위해
		int totalMoney = Integer.parseInt(request.getParameter("totalMoney"));
		
		// session영역에 공유한 '가장 마지막 주문'을 삭제하고
		HttpSession session = request.getSession();
		session.removeAttribute("latestOrder"); // 기존에 존재한 가장 마지막 주문내역을 지우고 구매처리를 한 제품을 lastOrder로 세션영역에 저장해줘야함
		
		// cartList(장바구니 목록)객체를 얻어옴
		ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
		
		if(cartList == null) { // cartList(장바구니 목록) 없으면
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('주문할 제품을 선택해주세요');");
			out.println("history.back();");
			out.println("</script>");
		}else { // cartList(장바구니 목록) 있으면
			// cartList(장바구니 목록)에서 항목을 하나씩 가져와 Menu 객체를 생성 후
			ArrayList<Menu> menuList = new ArrayList<Menu>();
			for(int i=0; i <cartList.size(); i++) {
				Cart cart = cartList.get(i);
				Menu menu = new Menu(cart.getM_id(), 
									 cart.getCategory(), 
									 cart.getName(), 
									 cart.getPrice(), 
									 cart.getQty()
							);
				
				menuList.add(menu); // menuList에 추가
			}
			
			/* session영역에 공유한 u_id와 u_email를 얻어와
			 * u_email을 얻어오는 이유 ?
			 * 향후 회원탈퇴 후 같은 id를 다른 사람이 사용할 수 있으므로 u_email을 포함시켜 
			 * 아이디는 같아도 서로 다른 고객임을 구분하기 위해
			 */
			
			String u_id = (String) session.getAttribute("u_id");
			String u_email = (String) session.getAttribute("u_email");
			
			if(u_id == null) {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('로그인 해주세요.');");
				out.println("location.href = 'userLogin.usr';");
				out.println("</script>");
			}else {
				// session영역에 공유한 u_grade를 얻어와 등급별 세일비율을 얻어옴
				String u_grade = (String)session.getAttribute("u_grade");
				UserGradeService userGradeService = new UserGradeService();
				double saleRate = userGradeService.getSaleRate(u_grade);
				
				System.out.println("u_grade : " + u_grade + ", saleRate : " + saleRate);
				
				// 얻어온 등급별 세일비율로 세일된 가격을 계산(이때, 실수 -> 정수)
				int saleTotalMoney = (int) (totalMoney * (1.0-saleRate)); // 10000 * (1.0-0.1) = 10000*0.9 = 9000
				System.out.println("totalMoney : " + totalMoney + ", saleTotalMoney : " + saleTotalMoney);
				
				MenuCartOrderService menuCartOrderService = new MenuCartOrderService();
				boolean isOrderMenuSuccess = menuCartOrderService.orderMenu(u_id, u_email, menuList, saleTotalMoney);
				
				if(isOrderMenuSuccess == false) { // 주문 실패하면
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("alert('주문실패');"); // 알림창 띄우고
					out.println("history.back();"); // 이전 상태로 돌아가고
					out.println("</script>");
				}else { // 주문 성공하면
					// session영역에 주문한 상세내역(menuList)을 추가하고
					session.setAttribute("menuList", menuList);
					session.setAttribute("totalMoney", totalMoney);
					session.setAttribute("saleTotalMoney", saleTotalMoney);
					
					// 장바구니에 담긴 물건을 [구매하기]가 끝나면 session영역에 '장바구니 목록(cartList)'은 삭제해야 함
					session.removeAttribute("cartList");
					
					// u_id로 해당 사용자의 '가장 마지막 주문내역'을 조회한 결과를 반환받아
					Order latestOrder = menuCartOrderService.userLastOrder(u_id);
					session.setAttribute("latestOrder", latestOrder);
					
					forward = new ActionForward("successOrder.jsp", true);
				}
			}
		
		}
		
		return forward;
	}

}
