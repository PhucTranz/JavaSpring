package qltb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qltb.Model.User;
import qltb.Service.AccountService;

public class AccountController {
	
	@Autowired
	private AccountService accountservice;
	
	@RequestMapping("/admin/delete/{id}")
	public String deleteAccount(@PathVariable(name = "id") int id) {
		accountservice.delete(id);
		return "redirect:/admin/createaccount";		
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
}
