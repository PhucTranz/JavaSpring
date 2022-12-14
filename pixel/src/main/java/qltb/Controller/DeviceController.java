package qltb.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import qltb.Model.Device;
import qltb.Model.User;
import qltb.Service.DeviceService;

@Controller
public class DeviceController {
	@Autowired 
	private DeviceService deviceService;
	
	private static boolean error = false;
	
	//Hien thi trang quan ly thiet bi
	@RequestMapping("admin/device")
	public String viewDevicePage(Model model) {
		if(error==true) {
			model.addAttribute("message", "Thêm/Sửa không thành công");
			error=false;
		}
		List<Device> listDevice = deviceService.listAll();
		model.addAttribute("listDevice", listDevice);
		model.addAttribute("device", new Device());
		return "DeviceManagement";
	}
	
	//Xoa thiet bi
	@RequestMapping("/admin/deleteDevice/{id}")
	public String deleteDevice(@PathVariable(name = "id") int id) {
		deviceService.delete(id);
		return "redirect:/admin/device";		
	}
	
	//Them thiet bi
	@RequestMapping(value = "admin/saveDevice", method = RequestMethod.POST)
	public String saveDevice (@ModelAttribute("device") Device d, Model model) {
		try {
			deviceService.save(d);
		}catch(Exception e) {
			error = true;
		}
		return "redirect:/admin/device";
	}
	
	//Sua thiet bi
	@RequestMapping(value = "admin/updateDevice", method = RequestMethod.POST)
	public String editAccount(@ModelAttribute("device") Device d, Model model) {
		try {
			Device device = deviceService.get(d.getMaTB());
			device.setTen(d.getTen());
			device.setSoluong(d.getSoluong());
			deviceService.save(device);
		}catch(Exception e) {
			error = true;
		}
		return "redirect:/admin/device";
	}
}
