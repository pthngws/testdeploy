package com.group4.controller.admin;

import com.group4.entity.RateEntity;
import com.group4.service.impl.RateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FeedbackController {
    @Autowired
    private RateServiceImpl rateServiceImpl;

//    @PostMapping("/respondToRate/{rateID}")
//    public String respondToRate(@PathVariable Long rateID, @RequestParam String response, Model model) {
//        // Kiểm tra và lưu phản hồi
//        rateService.respondToRate(rateID, response);
//        return "redirect:/product/{productID}";
//    }

    @GetMapping("/admin/rate-list")
    public String getRateList(Model model) {
        List<RateEntity> rates = rateServiceImpl.getAllRates(); // Lấy tất cả đánh giá
        model.addAttribute("rates", rates);
        return "BeeRateList"; // Trả về view rate-list.html
    }

    @GetMapping("/admin/respond/{rateID}")
    public String getRateResponseForm(@PathVariable("rateID") Long rateID, Model model) {
        RateEntity rate = rateServiceImpl.getRateById(rateID); // Lấy đánh giá dựa trên rateID
        model.addAttribute("rate", rate);
        return "BeeRateResponse"; // Trả về view rate-response.html
    }

    @PostMapping("/admin/respond/{rateID}")
    public String submitResponse(@PathVariable("rateID") Long rateID, @RequestParam("response") String response) {
        RateEntity rate = rateServiceImpl.getRateById(rateID);
        rate.setResponse(response); // Lưu phản hồi vào đánh giá
        rateServiceImpl.saveRate(rate); // Lưu lại vào cơ sở dữ liệu
        return "redirect:/admin/view-response/{rateID}"; // Quay lại danh sách đánh giá
    }

    // Xử lý hiển thị chi tiết phản hồi của Admin
    @GetMapping("/admin/view-response/{rateID}")
    public String viewResponse(@PathVariable("rateID") Long rateID, Model model) {
        RateEntity rate = rateServiceImpl.getRateById(rateID); // Lấy thông tin đánh giá
        model.addAttribute("rate", rate);
        return "BeeViewResponse"; // Chuyển tới view để xem phản hồi
    }
}
