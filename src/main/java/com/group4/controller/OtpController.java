package com.group4.controller;


import com.group4.config.Constants;
import com.group4.entity.AddressEntity;
import com.group4.entity.CustomerEntity;
import com.group4.repository.AddressRepository;
import com.group4.repository.CustomerRepository;
import com.group4.repository.UserRepository;
import com.group4.service.IEmailService;
import com.group4.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class OtpController {
    @Autowired
    private final IUserService userService;
    @Autowired
    private final IEmailService emailSenderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    Constants constants = new Constants();
    String email;

    public OtpController(IUserService userService, IEmailService emailSenderService) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

    @RequestMapping(value = "otp-check", method = RequestMethod.GET)
    public String indexOtp() {
        return "auth/otpConfirm";
    }


    @RequestMapping(value = "confirm-otp", method = RequestMethod.POST)
    public String checkOtp(HttpSession session, @RequestParam("otp") String otp, Model model) {
        String otpRegister = (String) session.getAttribute("otp-register");
        if (otp.equals(otpRegister)) {
            // Create a new AddressEntity object
            AddressEntity newAddress = new AddressEntity();
            newAddress.setCommune("x");  // Set street (example)
            newAddress.setCountry("x");      // Set city (example)
            newAddress.setDistrict("x");    // Set state (example)
            newAddress.setOther("x"); // Set country (example)
            newAddress.setProvince("x");       // Set zip code (example)

            // Save the new address
            addressRepository.save(newAddress);

            // Retrieve customer information from session
            String fullName = (String) session.getAttribute("name");
            String email = (String) session.getAttribute("email");
            String password = (String) session.getAttribute("password");

            // Create and populate the customer entity
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setEmail(email);
            customerEntity.setPassword(password);
            customerEntity.setName(fullName);
            customerEntity.setActive(false);

            // Set the newly created address to the customer
            customerEntity.setAddress(newAddress);

            // Save the customer entity
            customerRepository.save(customerEntity);

            // Redirect after successful account creation
            return "redirect:/";
        }

        model.addAttribute("mess","OTP is not correct! Please check your email.");
        return "auth/otpConfirm";
    }




    @RequestMapping(value = "recover", method = RequestMethod.GET)
    public String recover() {
        return "auth/recoverPage";
    }

    @RequestMapping(value = "send-otp-recover", method = RequestMethod.POST)
    public String getMail(@RequestParam("emailaddress") String email, HttpSession session) {
        session.setAttribute("emailToReset", email);

        String otpCode = constants.otpCode();

        String subject = "Mã OTP khôi phục tài khoản";
        String mess = "Chào bạn,\n\nMã OTP của bạn là: " + otpCode + "\n\nVui lòng không chia sẻ mã này với bất kỳ ai.\n\nTrân trọng,\nNhóm hỗ trợ.";

        this.emailSenderService.sendEmail(email, subject, mess);

        session.setAttribute("recoverOtp", otpCode);
        session.setMaxInactiveInterval(360);

        return "auth/confirmOtpRecover";
    }


    @RequestMapping(value = "confirm-otp-recover", method = RequestMethod.POST)
    public String recover( @RequestParam("otp") String otp, Model model,HttpSession session) {
        if (session.getAttribute("recoverOtp").equals(otp)){
            return "auth/confirmNewPassword";
        }
        model.addAttribute("mess","OTP is not correct! Please check your email.");
        return "auth/confirmOtpRecover";
    }

    @RequestMapping(value = "save-new-password", method = RequestMethod.POST)
    public String saveNewPassword(@RequestParam("password") String password, HttpSession session) {
        String email = (String) session.getAttribute("emailToReset");
        userService.recoverPassword(password,email);
        return "redirect:/login";

//        return "auth/confirmNewPassword";
    }


    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    public String confirm() {
        return "auth/confirm";
    }

    @RequestMapping(value = "send-otp-active", method = RequestMethod.POST)
    public String getConfirm(@RequestParam("emailaddress") String email, HttpSession session) {
        session.setAttribute("emailToReset", email);

        String otpCode = constants.otpCode();

        String subject = "Mã OTP kích hoạt tài khoản";
        String mess = "Chào bạn,\n\nMã OTP của bạn là: " + otpCode + "\n\nVui lòng không chia sẻ mã này với bất kỳ ai.\n\nTrân trọng,\nNhóm hỗ trợ.";

        this.emailSenderService.sendEmail(email, subject, mess);
        this.email = email;

        session.setAttribute("recoverOtp", otpCode);
        session.setMaxInactiveInterval(360);

        return "auth/confirmOtp";
    }



    @RequestMapping(value = "confirm-otp-active", method = RequestMethod.POST)
    public String active( @RequestParam("otp") String otp, Model model,HttpSession session) {
        if (session.getAttribute("recoverOtp").equals(otp)){
            CustomerEntity customerEntity = customerRepository.findByEmail(email).get();
            customerEntity.setActive(true);
            customerRepository.save(customerEntity);
            return "login";
        }
        model.addAttribute("mess","OTP is not correct! Please check your email.");
        return "auth/confirmOtp";
    }








}
