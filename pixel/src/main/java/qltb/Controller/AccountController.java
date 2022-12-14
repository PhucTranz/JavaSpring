package qltb.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import qltb.MyUserDetails;
import qltb.Model.Phieu;
import qltb.Model.User;
import qltb.Repository.UserRepository;
import qltb.Service.AccountService;
import qltb.Service.PhieuService;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountservice;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PhieuService phieuService;
	
	private static boolean error = false;
	
	//Knows url when param is admin/account and get list for html used.
	@RequestMapping("admin/account")
	public String viewCreateAccountPage(Model model) {
		if(error==true) {
			model.addAttribute("message", "Thêm/Sửa không thành công");
			error=false;
		}
		List<User> listUser = accountservice.listAll();
		model.addAttribute("listUser", listUser);
		model.addAttribute("taikhoan", new User());
		return "AccountManagement";
	}
	
	//Xoa tai khoan
	@RequestMapping("/admin/deleteAccount/{id}")
	public String deleteAccount(@PathVariable(name = "id") int id) {
		accountservice.delete(id);
		return "redirect:/admin/account";		
	}
	
	//Sua tai khoan
	@RequestMapping(value = "admin/updateAccount", method = RequestMethod.POST)
	public String editAccount(@ModelAttribute("taikhoan") User tk, Model model) {
		try {
			User u = accountservice.get(tk.getId());
			u.setName(tk.getName());
			u.setChucvu(tk.getChucvu());
			accountservice.save(u);
		}catch(Exception e) {
			error = true;
		}
		return "redirect:/admin/account";
	}
	
	//Them tai khoan
	@RequestMapping(value = "admin/saveaccount", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("taikhoan") User tk, Model model) {
		try {
			if(tk.getPassword() != null) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String p = tk.getPassword();
				String pass= encoder.encode(p);
				tk.setPassword(pass);
			}
			tk.setRole("ROLE_USER");
			tk.setEnabled(true);
			accountservice.save(tk);
		}catch(Exception e) {
			error = true;
		}
		return "redirect:/admin/account";
	}
	
	//Hien thi trang doi mat khau
	@RequestMapping("/changepassword")
	public String viewChangePassPage(Model model) {
		return "ChangePassword";
	}
	
	//Luu mat khau moi
	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	public String savePassword(@RequestParam(name = "password") String p,@RequestParam(name = "newpassword") String np,@RequestParam(name = "cfnewpassword") String cfp, Model model) {
		if(p.equals("") || np.equals("") || cfp.equals(""))
			model.addAttribute("errorMessage", "Không được bỏ trống");
		try {
			MyUserDetails u = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String pass = u.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if(encoder.matches(p, pass)) {
				if(np.equals(cfp)) {
					User user = userRepository.getUserByUsername(u.getUsername());
					user.setPassword(encoder.encode(np));
					accountservice.save(user);
					return "redirect:/home";
				}else {
					model.addAttribute("errorMessage", "Xác nhận mật khẩu chưa chính xác");
				}
			}else {
				model.addAttribute("errorMessage", "Sai mật khẩu");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "ChangePassword";	
	}
	
	//Xem thong tin tai khoan
	@RequestMapping("/accountdetails")
	public String accountDetailsPage(Model model) {
		MyUserDetails u = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", u);
		List<Phieu> listPhieu = phieuService.getByUserID(u.getID());
		model.addAttribute("sl", listPhieu.size());
		return "AccountDetails";
	}

	@RequestMapping("/changepassworded")
	public String viewChangePasswordPage(Model model) {
		return "ChangePassword";
	}
}
