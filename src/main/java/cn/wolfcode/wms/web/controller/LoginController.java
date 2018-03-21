package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.service.EmployeeService;
import cn.wolfcode.wms.util.JSONResult;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private EmployeeService employeeService;

    public LoginController() {
    }

    @ResponseBody
    @RequestMapping("login")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String errMsg = "";
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        if (exceptionClassName!=null) {
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                errMsg = "账号不存在！";
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                errMsg = "密码错误！";
            } else {
                errMsg = "其他异常信息";
            }
        }
        req.setAttribute("errMsg", errMsg);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
    /*public Object login(String username, String password) {
        JSONResult result = new JSONResult();
        try {
            this.employeeService.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark(e.getMessage());
        }
        return result;
    }*/

    @RequestMapping("main")
    public String main() {
        return "main";
    }

    /*@RequestMapping("logout")
    public String logout() {
        return "redirect:/main.do";
    }*/
}
