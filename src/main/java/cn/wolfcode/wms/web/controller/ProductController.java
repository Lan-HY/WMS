package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.service.BrandService;
import cn.wolfcode.wms.service.ProductService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ServletContext context;

    @RequiredPermission("商品列表")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")ProductQueryObject qo) throws Exception {
        PageResult result = productService.query(qo);
        model.addAttribute("brands", brandService.list());
        model.addAttribute("result", result);
        return "product/list";
    }

    @RequiredPermission("编辑商品")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("brands", brandService.list());
        if (id != null) {
            model.addAttribute("entity", productService.get(id));
        }
        return "/product/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Product entity, MultipartFile pic) throws Exception {
        if (pic != null && StringUtils.hasLength(entity.getImagePath())) {
            UploadUtil.deleteFile(context, entity.getImagePath());
        }
        if (!StringUtils.isEmpty(pic)) {
            String imagePath = UploadUtil.upload(pic, context.getRealPath("/upload"));
            entity.setImagePath(imagePath);
        }
        productService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除商品")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id, String imagePath) throws Exception {
        if (StringUtils.hasLength(imagePath)) {
            UploadUtil.deleteFile(context, imagePath);
        }
        productService.delete(id);
        return new JSONResult();
    }

    @RequestMapping("searchProduct")
    public String searchProduct(Model model, @ModelAttribute("qo")ProductQueryObject qo) throws Exception {
        PageResult result = productService.query(qo);
        model.addAttribute("brands", brandService.list());
        model.addAttribute("result", result);
        return "product/searchProduct";
    }

}
