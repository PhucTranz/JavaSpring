package qltb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {
	@Autowired
	private AccountService accountservice;
	
	@RequestMapping("/login")
	public String viewLoginPage(Model model) {
		return "login";
	}
	
	@RequestMapping("/loginerror")
	public String LoginError(Model model) {
		model.addAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu");
		return "login";
	}
	
	@RequestMapping("admin/createaccount")
	public String viewCreateAccountPage(Model model) {
		List<User> listUser = accountservice.listAll();
		model.addAttribute("listUser", listUser);
		model.addAttribute("taikhoan", new User());
		return "register";
	}
	
	@RequestMapping(value = "admin/saveaccount", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("taikhoan") User tk) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String p = tk.getPassword();
			String pass= encoder.encode(p);
			tk.setPassword(pass);
			tk.setRole("ROLE_USER");
			accountservice.save(tk);
		return "redirect:/admin/createaccount";
	}
	
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
	
	@RequestMapping("/header.html")
	public String viewHeaderPage(Model model) {
		return "header";
	}
	
	@RequestMapping("admin/header.html")
	public String viewHeaderAdminPage(Model model) {
		return "header";
	}
}
