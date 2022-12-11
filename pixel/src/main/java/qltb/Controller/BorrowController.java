package qltb.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import qltb.MyUserDetails;
import qltb.Model.Device;
import qltb.Model.P_TB_id;
import qltb.Model.Phieu;
import qltb.Model.Phieu_ThietBi;
import qltb.Model.User;
import qltb.Service.AccountService;
import qltb.Service.DeviceService;
import qltb.Service.PhieuService;
import qltb.Service.Phieu_ThietBiService;

@Controller
public class BorrowController {

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	PhieuService phieuService;
	
	@Autowired
	Phieu_ThietBiService phieu_thietbiService;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/muon_thiet_bi")
	public String viewBorrowPage(Model model) {
		List<Device> listDevices = deviceService.listAll();
		model.addAttribute("listDevices", listDevices);
		model.addAttribute("phieu", new Phieu());
		return "Borrow";
	}
	
	@RequestMapping(value = "/tao_phieu", method = RequestMethod.POST)
	public String taoPhieu(@RequestParam(name = "thietbi") Integer[] tb,@RequestParam(name = "soluong") Integer[] sl, @ModelAttribute("phieu") Phieu phieu) {
		
		MyUserDetails u = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		phieu.setId(u.getID());
		phieu.setTrangthai("Chờ duyệt");
		phieuService.save(phieu);
		
		List<Phieu> listPhieu = phieuService.listAll();
		Phieu curr = listPhieu.get(listPhieu.size()-1);
		int k=0;
		for(int i: tb) {
			for(int j = k;j<sl.length;j++) {
				if(sl[j] != null) {
					k=j+1;
					P_TB_id id = new P_TB_id(curr.getMaPhieu(), i);
					Phieu_ThietBi p_tb = new Phieu_ThietBi(id, sl[j]);
					phieu_thietbiService.save(p_tb);
					break;
				}
			}
		}
		return "redirect:/thongtin/"+curr.getMaPhieu();	
	}
	
	@RequestMapping("/lich_su")
	public String viewHistoryPage(Model model) {
		MyUserDetails u = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Phieu> listPhieu = phieuService.getByUserID(u.getID());
		model.addAttribute("listPhieu", listPhieu);
		model.addAttribute("role", "user");
		return "history";
	}
	
	@RequestMapping("/thongtin/{id}")
	public String viewDetails(@PathVariable(name = "id") int id, Model model) {
		Phieu phieu = phieuService.get(id);
		
		List<Phieu_ThietBi> listPTB = phieu_thietbiService.getByMaPhieu(id);
		List<Device> listTB = new ArrayList();
		
		for(Phieu_ThietBi i : listPTB) {
			Device d = deviceService.get(i.getId().getMaTB());
			d.setSoluong(i.getSoluong());
			listTB.add(d);
		}
		
		model.addAttribute("listTB", listTB);
		model.addAttribute("phieu", phieu);
		
		return "PhieuDetails";		
	}
	
	@RequestMapping("/huydon/{id}")
	public String huyPhieu(@PathVariable(name = "id") int id) {
		Phieu phieu = phieuService.get(id);
		if(phieu.getTrangthai().equals("Chờ duyệt"))
			phieu.setTrangthai("Đã hủy");
		phieuService.save(phieu);
		return "redirect:/thongtin/"+id;		
	}
	
	@RequestMapping("/admin/duyetdonpage")
	public String duyetDonPage(Model model) {
		List<Phieu> listPhieu = phieuService.getNotFinish();
		model.addAttribute("listPhieu", listPhieu);
		model.addAttribute("title", "DANH SÁCH CÁC PHIẾU ĐANG CHỜ DUYỆT");
		return "Admin_ListPhieu";
	}
	
	@RequestMapping("admin/thongtin/{id}")
	public String viewAdminDetails(@PathVariable(name = "id") int id, Model model) {
		Phieu phieu = phieuService.get(id);
		User u = accountService.get(phieu.getId());
		
		List<Phieu_ThietBi> listPTB = phieu_thietbiService.getByMaPhieu(id);
		List<Device> listTB = new ArrayList();
		
		for(Phieu_ThietBi i : listPTB) {
			Device d = deviceService.get(i.getId().getMaTB());
			d.setSoluong(i.getSoluong());
			listTB.add(d);
		}
		
		model.addAttribute("listTB", listTB);
		model.addAttribute("name", u.getName());
		model.addAttribute("chucvu", u.getChucvu());
		model.addAttribute("phieu", phieu);
		
		return "Admin_PhieuDetails";		
	}
	
	@RequestMapping("admin/huydon/{id}")
	public String adminHuyPhieu(@PathVariable(name = "id") int id) {
		Phieu phieu = phieuService.get(id);
		phieu.setTrangthai("Đã hủy");
		phieuService.save(phieu);
		return "redirect:/admin/duyetdonpage";		
	}
	
	
	@RequestMapping("admin/duyetdon/{id}")
	public String adminDuyetPhieu(@PathVariable(name = "id") int id) {
		Phieu phieu = phieuService.get(id);
		phieu.setTrangthai("Chờ trả");
		phieuService.save(phieu);
		return "redirect:/admin/duyetdonpage";		
	}
	
	@RequestMapping("admin/lich_su")
	public String viewAdminHistoryPage(Model model) {
		List<Phieu> listPhieu = phieuService.listAll();
		model.addAttribute("listPhieu", listPhieu);
		model.addAttribute("title", "LỊCH SỬ PHIẾU MƯỢN");
		model.addAttribute("role", "admin");
		return "history";
	}
	
	@RequestMapping("admin/finish/{id}")
	public String adminFinishPhieu(@PathVariable(name = "id") int id) {
		Phieu phieu = phieuService.get(id);
		phieu.setTrangthai("Kết thúc");
		phieuService.save(phieu);
		return "redirect:/admin/duyetdonpage";		
	}
}
