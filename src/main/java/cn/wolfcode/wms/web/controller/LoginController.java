package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.service.EmployeeService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private EmployeeService employeeService;

    public LoginController() {
    }

    @ResponseBody
    @RequestMapping({"login"})
    public Object login(String username, String password) {
        JSONResult result = new JSONResult();
        try {
            this.employeeService.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark(e.getMessage());
        }
        return result;
    }

    @RequestMapping({"main"})
    public String main() {
        return "main";
    }

    @RequestMapping({"logout"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }
}
