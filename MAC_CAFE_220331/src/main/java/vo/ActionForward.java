/*
 * 포워딩 정보를 저장할 수 있는 클래스
 * 컨트롤러에서 클라이언트의 각 요청을 받아서 처리한 후
 * 최종적으로 뷰 페이지(.jsp)로 포워딩(이동) 처리 시
 * 이동할 뷰 페이지의 URL(경로), 포워딩방식(디스패치: 기존요청, 리다이렉트: 새요청)이 필요하다.
 * 이 두정보를 편리하게 다루기 위해서 ActionForward 클래스를 설계
 */

package vo; // DTO 객체 : 폼으로부터 사용자가 입력한 값이나, DB결과를 담는 곳 

public class ActionForward {
	// 컨트롤러에서 요청 처리 후 포워딩 될 최종 뷰 페이지의 URL 저장
	private String path = null;
	
	// 포워딩 방식 저장. false이면 디스패치방식(기존요청), true면 리다이렉트(새요청)
	private boolean isRedirect = false; // false : 대부분 기존요청을 처리하는 부분이 많기 때문

	// 매개변수가 없는 생성자를 반드시 존재해야하므로 수동으로 생성해줘야함 (매개변수가 있는 생성자가 존재하면 기본생성자가 자동으로 생성되지않음)
	public ActionForward() {}
	
	// 매개변수가 있는 생성자
	public ActionForward(String path, boolean isRedirect) {
		super();
		this.path = path;
		this.isRedirect = isRedirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
