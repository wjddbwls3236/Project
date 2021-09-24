package com.naver.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.naver.service.MemberService;
import com.naver.vo.MemberVO;

import pwdconv.PwdChange;

@Controller
public class HomeController {

	@Autowired
	MemberService memberService;

	// 회원가입 페이지
	@RequestMapping("/Join")
	public ModelAndView Join() {
		ModelAndView joinM = new ModelAndView();
		joinM.setViewName("main/Join");
		return joinM;
	}

	// 로그인 페이지
	@RequestMapping("/Login")
	public ModelAndView Login() {
		ModelAndView loginM = new ModelAndView();
		loginM.setViewName("main/Login"); // 뷰페이지
		return loginM;
	}

	// 이메일 중복체크
	@RequestMapping("/member_mailcheck")
	public String member_idcheck(String mail_id, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		MemberVO db_mail = this.memberService.mailcheck(mail_id);// 이메일 중복검색

		int re = -1;// 중복 이메일이 없을때 반환값

		if (db_mail != null) { // 중복아이디가 있을때
			re = 1;
		}
		out.println(re);// 값 반환
		return null;
	}

	// 닉네임 중복체크
	@RequestMapping("/member_nickcheck")
	public String member_nickcheck(String mem_nic, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		MemberVO db_nick = this.memberService.nickcheck(mem_nic);// 이메일 중복검색

		int re = -1;// 중복 이메일이 없을때 반환값

		if (db_nick != null) { // 중복아이디가 있을때
			re = 1;
		}
		out.println(re);// 값 반환
		return null;
	}

	// 회원저장
	@RequestMapping("/member_join_ok")
	public String member_join_ok(MemberVO m, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		m.setMem_pwd(PwdChange.getPassWordToXEMD5String(m.getMem_pwd()));// 비번 암호화
		this.memberService.insertMember(m); // 회원저장

		out.println("<script>");
		out.println("alert('회원가입 되었습니다.');");
		out.println("location='Login';");
		out.println("</script>");

		return null; // 회원저장후 로그인 매핑주소로 이동

	}

	// 로그인 인증
	@RequestMapping("/member_login_ok")
	public String member_login_ok(String login_id, String login_pwd, HttpSession session, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		MemberVO dm = this.memberService.loginCheck(login_id);
		// 가입 회원 1인경우만 로그인 인증처리

		if (dm == null) {
			out.println("<script>");
			out.println("alert('가입 안된 회원입니다');");
			out.println("history.back();");
			out.println("</script>");
		} else {
			if (!dm.getMem_pwd().equals(PwdChange.getPassWordToXEMD5String(login_pwd))) { // 비번이 같지 않으면(암호화)
				out.println("<script>");
				out.println("alert('비번이 다릅니다');");
				out.println("history.back();");
				out.println("</script>");
			} else { // 비번이 같은경우
				session.setAttribute("id", login_id); // 세션 아이디 저장
				return "redirect:/main2";
			}
		}
		return null;
	}

	// 메인
	@GetMapping("/main2")
	public ModelAndView index(HttpServletResponse response, HttpSession session) throws Exception {

		String id = (String) session.getAttribute("id"); // 세션 아이디 저장할때 키이름
		

		if (id != null) {
			MemberVO m = this.memberService.getMember(id);
			ModelAndView am = new ModelAndView();
			am.setViewName("main/main2");
			am.addObject("m", m);
			return am;
		} else {
			ModelAndView am = new ModelAndView();
			am.setViewName("main/main2");
			return am;
		}
		
	}

	// member_logout
	@RequestMapping("/member_logout") // 서버에 정보를 브라우저에 응답할때 리스폰즈씀
	public String member_logout(HttpServletResponse response, HttpSession session) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		session.invalidate();// 세션 만료(즉 로그아웃)
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다.');");
		out.println("location='Login';");
		out.println("</script>");

		return null;
	}

	// 정보수정
	@RequestMapping("/member_edit")
	public ModelAndView member_edit(HttpServletResponse response, HttpSession session) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		String mail_id = (String) session.getAttribute("id");
		if (mail_id == null) {
			out.println("<script>");
			out.println("alert('다시 로그인 하세요');");
			out.println("location='Login';");
			out.println("</script>");
		} else {

			// 오라클로 부터 mail_id에 해당하는 회원정보 가져옴
			MemberVO m = this.memberService.getMember(mail_id);

			ModelAndView em = new ModelAndView("main/member_edit");// 수정폼
			em.addObject("m", m);
			return em;

		}
		return null;
	}

	// 정보 수정 완료
	@RequestMapping("/member_edit_ok")
	public String member_edit_ok(MemberVO m, HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		String mail_id = (String) session.getAttribute("id");
		if (mail_id == null) {
			out.println("<script>");
			out.println("alert('다시 로그인 하세요');");
			out.println("location='Login';");
			out.println("</script>");
		} else {
			m.setMail_id(mail_id);
			m.setMem_pwd(PwdChange.getPassWordToXEMD5String(m.getMem_pwd()));// 정식 비번 암호화

			this.memberService.editMember(m);// 정보수정완료

			out.println("<script>");
			out.println("alert('정보 수정했습니다.');");
			out.println("location='mypage';");
			out.println("</script>");
		}
		return null;
	}

	// 회원탈퇴 폼
	@RequestMapping("/member_del")
	public ModelAndView member_del(HttpServletResponse response, HttpSession session) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		String mail_id = (String) session.getAttribute("id");
		if (mail_id == null) {
			out.println("<script>");
			out.println("alert('다시 로그인 하세요');");
			out.println("location='Login';");
			out.println("</script>");
		} else {
			MemberVO dm = this.memberService.getMember(mail_id);// 회원정보를 가져옴

			ModelAndView m = new ModelAndView();
			m.addObject("m", dm);
			m.setViewName("main/member_del");
			return m;
		}
		return null;
	}

	// 탈퇴완료
	@RequestMapping("/member_del_ok")
	public String member_del_ok(HttpServletResponse response, HttpSession session, String mem_pwd, String del_cont)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();// 출력스트림 생성

		String mail_id = (String) session.getAttribute("id");
		if (mail_id == null) {
			out.println("<script>");
			out.println("alert('다시 로그인 하세요');");
			out.println("location='Login';");
			out.println("</script>");
		} else {
			mem_pwd = PwdChange.getPassWordToXEMD5String(mem_pwd);
			MemberVO db_pwd = this.memberService.getMember(mail_id);

			if (!db_pwd.getMem_pwd().equals(mem_pwd)) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다.');");
				out.println("history.back();");
				out.println("</script>");
			} else {
				MemberVO dm = new MemberVO();
				dm.setMail_id(mail_id);
				dm.setMem_delcont(del_cont);
				this.memberService.delMem(dm);// 회원탈퇴

				session.invalidate();// 세션 만료(로그아웃)

				out.println("<script>");
				out.println("alert('탈퇴 되었습니다');");
				out.println("location='Login';");
				out.println("</script>");
			}
		}

		return null;
	}

	// 메인 페이지
	@RequestMapping("/")
	public String home() throws Exception {
		return "main/main2";

	}

	//비번찾기 폼
	@RequestMapping("/pwd_find")
	public String pwd_find() {
		return "main/pwd_find";
	}
}
