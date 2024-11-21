package com.group4.service;

import com.group4.entity.CustomerEntity;
import com.group4.entity.UserEntity;
import com.group4.model.AddressModel;
import com.group4.model.UserModel;
import com.group4.repository.CustomerRepository;
import com.group4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private static UserRepository userRepository;

    public List<CustomerEntity> searchCustomers(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return customerRepository.findAllCustomers();
        }
        return customerRepository.findByNameContainingOrEmailContaining(keyword, keyword);
    }


    // Thêm mới khách hàng
    public CustomerEntity addCustomer(CustomerEntity customer) {
        return customerRepository.save(customer);
    }


    // Sửa thông tin khách hàng
    public CustomerEntity updateCustomer(Long id, CustomerEntity updatedCustomer) {
        return (CustomerEntity) customerRepository.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress(updatedCustomer.getAddress());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }


    // Xóa tài khoản khách hàng
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Lấy danh sách khách hàng
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAllCustomers();
    }

    // Phương thức lấy thông tin khách hàng theo ID
    public Optional<CustomerEntity> getCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    // Cập nhật trạng thái hoạt động
    public void updateActiveStatus(Long userId, boolean status) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + userId));
        user.setActive(status);
        userRepository.save(user); // Lưu thay đổi
    }

    // Chuyển từ Entity sang Model
    private UserModel mapToModel(UserEntity user) {
        return new UserModel(
                user.getUserID(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                user.isActive(),
                new AddressModel( /* Mapping address fields */ )
        );
    }

    // Xóa tài khoản người dùng
    public void deleteCustomerAccount(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khách hàng với ID: " + id);
        }
        customerRepository.deleteById(id);
    }



}
