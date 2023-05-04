package com.msystem;

import com.msystem.entity.*;
import com.msystem.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class MsystemApplicationTests {

    @Autowired
    private GoodService goodService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReturnGoodService returnGoodService;
    @Autowired
    private AccountService accountService;

    @Test
//    @Transactional
    void contextLoads() {
//        Optional<Good> good = goodService.queryGoodById(2);
//        System.out.println(good.orElse(null));
        List<Good> goods = goodService.queryAllGood();
        System.out.println(goods);

    }
    @Test
//    @Transactional
    void testCompany() {
        Optional<Company> company = companyService.queryCompanyById(1);
        System.out.println(company.orElse(null));
        List<Company> companyList = companyService.queryAllCompany();
        System.out.println(companyList);

    }
    @Test
//    @Transactional
    void testEmployee() {
//        List<Employee> employeeList = employeeService.queryAllEmployee();
//        System.out.println(employeeList);
//        Optional<Employee> employee = employeeService.queryEmployeeById(5);
//        System.out.println(employee.orElse(null));
//        Employee employee1 = new Employee();
//        employee1.setGender("0");
//        employee1.setEName("tes1");
//        employeeService.updateEmployee(employee1);
        int page = 0;
        //默認每頁顯示10個數據
        int size = 10;
        //默認排序依照id
        String sortBy = "eId";
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Employee> containing = employeeService.findByENameContainingOrAddressContainingOrPhoneContaining("新", "新", "新", pageable);
        System.out.println(containing);
    }
    @Test
    @Transactional
    void testOrder() {
        List<Order> orderList = orderService.queryAllOrder();
        System.out.println(orderList);
//        Optional<Order> order = orderService.queryOrderById(3);
//        System.out.println(order.orElse(null));

    }
    @Test
//    @Transactional
    void testReturnGood() {
        Optional<ReturnGood> returnGood = returnGoodService.queryReturnGoodById(1);
        System.out.println(returnGood.orElse(null));
        List<ReturnGood> returnGoodList = returnGoodService.queryAllReturnGood();
        System.out.println(returnGoodList);
    }
    @Test
//    @Transactional
    void testAccount() {
        Account account = accountService.login("admin", "admin");
        System.out.println(account);
    }

}
