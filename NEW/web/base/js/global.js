/**
 * @title:<h3> 全局应用js <h3>
 * @author: Danpil
 * @date: 2018-10-10 15:17
 * @params
 * @return
 **/
var globalParams = {
    /**
     * @title:<h3> iframe内部选项卡切换 <h3>
     * @author: Danpil
     * @date: 2018-10-9 14:26
     * @params pageInfoArr【"userAdd.html","userEdit","编辑用户"】pk【主键id】
     * @return
     **/
    tabInnerChange: function (pageInfoArr,pk) {
        var $navTepm = parent.$("#nav_temp");
        if(pk==null){
            pk="";
        }else{//编辑页面
            parent.indexParams.tabInnerClose("lid_"+pageInfoArr[1]);
        }
        $navTepm.attr("data-url", pageInfoArr[0]+pk).attr("data-id", pageInfoArr[1]).text(pageInfoArr[2]);
        $navTepm.click();
    },

}
$(function () {
    //关闭当前选项卡
    $("#btnClose").click(function () {
        parent.$("#tabClose_temp").click();
    });
    //列表批量删除全选
    $("table.layui-table>thead>tr>th>input.inpDel[type='checkbox']").click(function () {
        $(this).parent().parent().parent().siblings("tbody").find("input.inpDel[type='checkbox']").prop("checked", $(this).prop("checked"));
    });
    //layui优化后的列表批量删除全选
    $("table.layui-table>thead>tr>th").on("click",".layui-form-checkbox",function () {
        var checkboxs= $(this).parent().parent().parent().siblings("tbody").find("input.inpDel[type='checkbox']");
        if( $(this).siblings("input.inpDel").prop("checked")){//如果是全选
            checkboxs.prop("checked", true);
            checkboxs.siblings(".layui-form-checkbox").addClass("layui-form-checked");
        }else{//如果是反选
            checkboxs.prop("checked", false);
            checkboxs.siblings(".layui-form-checkbox").removeClass("layui-form-checked")
        }
    });
    //绑定编辑页面编辑按钮事件
    $("#btnEdit").click(function () {
        $(this).addClass("hidden");//隐藏编辑按钮
        //显示取消按钮 ,显示修改按钮
        $("#btnCancel,#btnSubmitUpdate").removeClass("hidden");
        $(this).parents("form").find(":input").not(":button").prop("disabled", false);
        $(this).parents("form").find(".layui-disabled").removeClass("layui-disabled");
    });
    //绑定编辑页面取消按钮事件
    $("#btnCancel").click(function () {
        //隐藏取消按钮，隐藏修改按钮
        $(this).addClass("hidden");
        $("#btnSubmitUpdate").addClass("hidden");
        //显示编辑按钮
        $("#btnEdit").removeClass("hidden");
        $(this).parents("form").find(":input").not(":button").prop("disabled", true);
        $(this).parents("form").find(".layui-checkbox-disbaled").removeClass("layui-checkbox-disbaled");
        $(this).parents("form").find(".layui-radio-disbaled").removeClass("layui-radio-disbaled");
    })
});
