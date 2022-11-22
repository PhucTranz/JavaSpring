package qltb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@RequestMapping("admin/createaccount")
	public String viewCreateAccountPage(Model model) {
		model.addAttribute("taikhoan", new User());
		return "register";
	}
	
	@RequestMapping(value = "admin/saveaccount", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("taikhoan") User tk) {
		accountservice.save(tk);
		return "redirect:/login";
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
}
