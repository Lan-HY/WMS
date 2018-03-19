<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validation/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $.ajaxSettings.traditional = true;
        $(function () {
            $("#editForm").validate({
                rules: {
                    sn: {
                        required: true
                    },
                    vdate: {
                        required: true
                    }
                }
            });
            $(".searchproduct").click(function () {
                var currentTr = $(this).closest("tr");
                $.artDialog.open("/product/searchProduct.do",{
                    id : "searchProduct",
                    title : "选择商品",
                    width : 1000,
                    height : 500,
                    opacity: 0.2,
                    lock : true,
                    close : function () {
                        var productInfo = $.artDialog.data("productInfo");
                        if(productInfo) {
                            currentTr.find("[tag=name]").val(productInfo.name);
                            currentTr.find("[tag=pid]").val(productInfo.id);
                            currentTr.find("[tag=brand]").text(productInfo.brandName);
                            currentTr.find("[tag=costPrice]").val(productInfo.costPrice);
                            currentTr.find("[tag=number]").val(1);
                            currentTr.find("[tag=amount]").text((productInfo.costPrice * 1).toFixed(2));
                        }
                        $.artDialog.removeData("productInfo");
                    }
                });
            });

            $("[tag=costPrice], [tag=number]").blur(function () {
                var currentTr = $(this).closest("tr");
                var costPrice = currentTr.find("[tag=costPrice]").val();
                var number = currentTr.find("[tag=number]").val();
                currentTr.find("[tag=amount]").text((costPrice * number).toFixed(2));
            });

            $(".appendRow").click(function () {
                var newTr = $("#edit_table_body tr:first-child").clone(true);
                newTr.find("[tag=name]").val("");
                newTr.find("[tag=pid]").val("");
                newTr.find("[tag=brand]").text("");
                newTr.find("[tag=costPrice]").val("");
                newTr.find("[tag=number]").val("");
                newTr.find("[tag=amount]").text("");
                newTr.find("[tag=remark]").val("");
                newTr.appendTo($("#edit_table_body"));
            });

            //在提交表单的时候,修改每行的表单元素的name属性的值
            $(".this_submit").click(function () {
                if($("[tag=pid]").val()) {
                    $("#edit_table_body tr").each(function (index, item) {
                        $(item).find("[tag=pid]").prop("name", "items["+ index + "].product.id");
                        $(item).find("[tag=costPrice]").prop("name", "items[" + index + "].costPrice");
                        $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
                        $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");
                    });
                    $("#editForm").ajaxForm(function (data) {
                        if(data.success) {
                            showDialog("操作成功", function () {
                                location.href = "/orderBill/list.do";
                            });
                        } else {
                            showDialog("操作失败", data.msg);
                        }
                    });
                    $("#editForm").submit();
                } else {
                    showDialog("请选择至少一条明细！");
                }
            });

            $(".removeItem").click(function () {
                var currentTr = $(this).closest("tr");
                if($("#edit_table_body tr").size() == 1) {
                    currentTr.find("[tag=name]").val("");
                    currentTr.find("[tag=pid]").val("");
                    currentTr.find("[tag=brand]").text("");
                    currentTr.find("[tag=costPrice]").val("");
                    currentTr.find("[tag=number]").val("");
                    currentTr.find("[tag=amount]").text("");
                    currentTr.find("[tag=remark]").val("");
                } else {
                    currentTr.remove();
                }
            });

        });
    </script>
</head>
<body>
<form id="editForm" action="/orderBill/saveOrUpdate.do" method="post">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <input type="hidden" name="inputUser.id" value="${entity.inputUser.id}">
                <tr>
                    <td class="ui_text_rt" width="140">采购单编码</td>
                    <td class="ui_text_lt">
                        <input type="text" name="sn" value="${entity.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <select id="supplier.id" name="supplier.id" class="ui_select03">
                            <c:forEach items="${suppliers}" var="supplier">
                                <option value="${supplier.id}" ${supplier.id == entity.supplier.id ? "selected" : ""}>${supplier.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd" var="vdate"/>
                        <input type="text" name="vdate" id="vdate" value="${vdate}" class="ui_input_txt02"
                            onclick="WdatePicker()"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:if test="${entity.id == null}">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden"  tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><input type="number" tag="costPrice"
                                               class="ui_input_txt02"/></td>
                                    <td><input type="number" tag="number"
                                               class="ui_input_txt02"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><input tag="remark"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${entity.id != null}">
                                <c:forEach items="${entity.items}" var="item">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <input disabled="true" readonly="true" value="${item.product.name}" class="ui_input_txt02" tag="name"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <input type="hidden" value="${item.product.id}"  tag="pid"/>
                                        </td>
                                        <td><span tag="brand">${item.product.brandName}${item.product.brandId}</span></td>
                                        <td><input type="number" tag="costPrice" value="${item.costPrice}" class="ui_input_txt02"/></td>
                                        <td><input type="number" tag="number" value="${item.number}" class="ui_input_txt02"/></td>
                                        <td><span tag="amount">${item.amount}</span></td>
                                        <td><input tag="remark" value="${item.remark}" class="ui_input_txt02"/></td>
                                        <td>
                                            <a href="javascript:;" class="removeItem">删除明细</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="确定保存" class="ui_input_btn01 this_submit"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>