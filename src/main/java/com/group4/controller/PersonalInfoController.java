package com.group4.controller;

import com.group4.model.AddressModel;
import com.group4.model.UserModel;
import com.group4.service.IAddressService;
import com.group4.service.IPersonalInfoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personal-info")
public class PersonalInfoController {

    @Autowired
    private IPersonalInfoService service;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IPersonalInfoService personalInfoService;

    // Lấy thông tin cá nhân và địa chỉ
    @GetMapping
    public String getPersonalInfo(Model model) {
        UserModel user = service.fetchPersonalInfo(2L); // Gọi Service để lấy thông tin người dùng
        if (user != null) {
            model.addAttribute("user", user); // Thêm thông tin người dùng vào model
        }
        return "personal-info"; // Hiển thị trang personal-info
    }

    // Cập nhật thông tin cá nhân
    @PostMapping("/profile")
    public String updatePersonalInfo(UserModel userModel) {
        Long userID = 2L;
        userModel.setUserID(userID);
        boolean status = service.savePersonalInfo(userModel, userModel.getUserID()); // Lưu thông tin mới
        if (status) {
            return "redirect:/personal-info?success"; // Chuyển hướng với trạng thái thành công
        } else {
            return "redirect:/personal-info?error"; // Chuyển hướng với trạng thái thất bại
        }
    }


    @PostMapping("/address")
    public String updateAddress(@ModelAttribute AddressModel addressModel) {
        Long userID = 2L; // Thay đổi theo user hiện tại từ session/token
        UserModel user = personalInfoService.fetchPersonalInfo(userID);

        AddressModel existingAddress = user.getAddress();
        if (existingAddress != null) {
            addressModel.setAddressID(existingAddress.getAddressID());
        }

        boolean status = addressService.updateAddressForUser(addressModel, addressModel.getAddressID());
        return status ? "redirect:/personal-info?address-success"
                : "redirect:/personal-info?address-error";
    }


    @PostMapping("/password")
    public String changePassword(String currentPassword, String newPassword, String confirmNewPassword, Model model) {
        Long userID = 2L; // ID người dùng hiện tại, thay bằng ID từ session hoặc token

        // Lấy thông tin người dùng từ database
        UserModel user = personalInfoService.fetchPersonalInfo(userID);

        // Kiểm tra nếu không tìm thấy người dùng
        if (user == null) {
            model.addAttribute("error", "Người dùng không tồn tại!");
            return "personal-info";
        }

        // Kiểm tra mật khẩu hiện tại
        if (!currentPassword.equals(user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "personal-info";
        }

        // Kiểm tra mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return "personal-info";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(newPassword);
        boolean status = personalInfoService.savePersonalInfo(user, user.getUserID());

        if (status) {
            model.addAttribute("message", "Thay đổi mật khẩu thành công!");
            return "redirect:/personal-info?success";
        } else {
            model.addAttribute("error", "Có lỗi xảy ra khi thay đổi mật khẩu!");
            return "personal-info";
        }
    }
}
