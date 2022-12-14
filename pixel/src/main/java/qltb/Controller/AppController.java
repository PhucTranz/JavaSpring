package qltb.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import qltb.MyUserDetails;

@Controller
public class AppController {
	
	//Hien thi trang login
	@RequestMapping("/login")
	public String viewLoginPage(Model model) {
		return "login";
	}
	
	//Thong bao sai tai khoan or mat khau
	@RequestMapping("/loginerror")
	public String LoginError(Model model) {
		model.addAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu");
		return "login";
	}
	
	//Hien thi trang chu
	@RequestMapping("/home")
	public String viewHomePage(Model model) {
		String role="";
		try {
			MyUserDetails u = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			role = u.getRole();
		}catch(Exception e) {
			;
		}
		if(role.equals("ROLE_ADMIN"))
			return "home";
		return "sthome";
	}
	
	//Hien thi header
	@RequestMapping("/header.html")
	public String viewHeaderPage(Model model) {
		return "header";
	}
	
	@RequestMapping("admin/header.html")
	public String viewHeaderAdminPage(Model model) {
		return "header";
	}
	
	@RequestMapping("thongtin/header.html")
	public String TTHeader(Model model) {
		return "header";
	}
	
	@RequestMapping("admin/duyetdonpage/header.html")
	public String DDHeader(Model model) {
		return "header";
	}
	
	@RequestMapping("admin/thongtin/header.html")
	public String aTTHeader(Model model) {
		return "header";
	}
}
