package com.msystem;

import com.msystem.entity.*;
import com.msystem.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Optional<Employee> employee = employeeService.queryAllEmployeeById(5);
        System.out.println(employee.orElse(null));

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

}
