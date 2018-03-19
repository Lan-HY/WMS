/** table鼠标悬停换色* */
$.ajaxSettings.traditional = true;
$(function () {
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({
            background: "#CDDAEB"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#1D1E21"
            });
        });
    }).mouseout(function () {
        $(this).css({
            background: "#FFF"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#909090"
            });
        });
    });

    //跳转到编辑的界面中
    $(".btn_input").click(function () {
        var url = $(this).data("url");
        location.href = url;
    });

    //翻页操作
    $(".btn_page").click(function () {
        //获取当前按钮的页码
        var pageNo = $(this).data("page") || $("input[name='currentPage']").val() || 1;
        //修改当前页号码
        $("input[name='currentPage']").val(pageNo);
        //提交表单
        $("#searchForm").submit();
    });

    //改变页面容量提交表单
    $("#pageSize").change(function () {
        $("input[name='currentPage']").val(1);
        $("#searchForm").submit();
    });

    //点击删除按钮
    $(".btn_delete").click(function () {
        var url = $(this).data("url");
        var reload = $(this).data("reload");
        //弹出对话框
        showDialog("你确定要删除吗?",function () {
            //发送ajax请求
            $.get(url,function (data) {
                //如果操作成功,自动刷新页面
                if (data.success) {
                    showDialog("删除成功",function () {
                        location.href=reload;
                    });
                } else {
                    showDialog("操作失败", data.msg);
                }
            },"json");
        },true);
    });

    $(".btn_submit").click(function () {
        var url = $(this).data("url");
        $("#editForm").ajaxForm(function (data) {
            if(data.success) {
                showDialog("操作成功", function () {
                    location.href = url;
                });
            } else {
                showDialog("操作失败", data.msg);
            }
        });
        $("#editForm").submit();
    });

    $(function () {
        //给批量删除按钮绑定点击事件
        $(".btn_batchDelete").click(function () {
            var url = $(this).data("url");

            //弹出确定删除的对话框
            showDialog("确定要批量删除吗?",function () {
                //获取到选中的数据的ID
                var ids = $.map($(".acb:checked"),function (item) {
                    return $(item).data("eid");
                });

                //获取到所有的被选中的checkbox
                if(ids.length > 0) {
                    $.post(url,{ids:ids},function (data) {
                        if (data.success) {
                            showDialog("批量删除成功",function () {
                                location.reload();
                            });
                        }
                    },"json");
                } else { //一个都没有选
                    showDialog("至少选中1条数据");
                }
            },true);
        });

        //全选 / 全不选
        $("#all").click(function () {
            $(".acb").prop("checked",this.checked);
        });
    });

});

//对话框弹出函数
function showDialog(content, fn, cancel) {
    $.dialog({
        title: "温馨提示",
        icon: "face-smile",
        lock: true,
        content: content,
        ok: fn || true,
        cancel: cancel
    });
}
