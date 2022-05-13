/*
 * 컨트롤러에서 요청이 파악되면 해당 요청을 처리하는 각 Action클래스를 사용해서 요청 처리
 * 각 요청에 해당하는 Action클래스 객체들을 다형성을 이용해서 동일한 타입인 Action으로 참조하기 위해
 */

package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public interface Action {
	// 추상메서드 : 반드시 자식클래스에서 재정의해야 함
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception; // throws Exception : execute를 호출할 쪽에서 예외를 처리하라
		
}
