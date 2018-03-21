package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ClientService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequiredPermission("客户列表")
    @RequiresPermissions("client:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) throws Exception {
        PageResult result = clientService.query(qo);
        model.addAttribute("result", result);
        return "client/list";
    }

    @RequiredPermission("编辑客户")
    @RequiresPermissions("client:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("entity", clientService.get(id));
        }
        return "/client/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Client entity) throws Exception {
        clientService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除客户")
    @RequiresPermissions("client:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        clientService.delete(id);
        return new JSONResult();
    }

}
