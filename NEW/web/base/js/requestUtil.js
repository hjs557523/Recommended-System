/**
 * @title:<h3> 网络请求工具js <h3>
 * @author: Danpil
 * @date: 2018-10-10 15:13
 * @params
 * @return
 **/
var requestUtilParams = {
    pageSize: 10
    , host: "/"
    //,host:"http://localhost:63342/webapps/day20181023/webapp/"
    , staticJson: false//加载静态json数据
    , token: ""
    , ajaxSetting: {
        //ajax默认参数
        async: true
        , type: "post"
    }

    , queryCondition: ""//查询条件
    //, host: "http://172.16.26.47:13080/ssmService/"
    //,host:"files/data/"
    /**
     * @title:<h3>  公共的ajax请求<h3>
     * @author: Danpil
     * @date: 2018-10-18 15:42
     * @params
     * @return
     **/
    , xhr: function (actionName, data, setting) {
        //设置ajax默认参数
        if (setting == null) {
            setting = requestUtilParams.ajaxSetting;
        }
        $.each(requestUtilParams.ajaxSetting, function (k, v) {
            if (setting[k] == null) {
                setting[k] = v;
            }
        });
        requestUtilParams.getToke();//获得token
        if (data == "" || data == null) {
            data = "token=" + requestUtilParams.token;
        } else if (typeof data == "string") {
            data += "&token=" + requestUtilParams.token;
        } else if (typeof data == "object") {
            data["token"] = requestUtilParams.token;
        }
        var sendUrl = actionName;
        if (requestUtilParams.staticJson) {
            //说明加载静态json数据
            sendUrl = "files/data/" + actionName + ".json";
        }
        var xhrReq = $.ajax({
            url: requestUtilParams.host + sendUrl,
            type: setting.type,
            dataType: "json",
            async: setting.async,
            data: data,
            success: function (res) {
                requestUtilParams.removePermissionBtns(res.permission_btns);
            }, error: function (res, status, xr) {
                requestUtilParams.ajaxError(res, status, xr)
            },
            complete: function (res) {
                requestUtilParams.getToke(1);
            }
        });
        return xhrReq;
    }
    /**
     * @title:<h3> xhr的get方法 <h3>
     * @author: Danpil
     * @date: 2018-10-23 13:44
     * @params
     * @return
     **/
    , xhrGet: function (actionName, setting) {
        if (setting == null) {
            setting = {};
        }
        setting.type = "get";
        return requestUtilParams.xhr(actionName, "", setting)
    }
    /**
     * @title:<h3>文件上传xhr  <h3>
     * @author: Danpil
     * @date: 2018-10-24 11:29
     * @params
     * @return
     **/
    , xhrUpload: function (actionName, formNode) {
        var data = new FormData($(formNode)[0]);
        $(formNode).attr("enctype", "multipart/form-data");
        data.append("token", requestUtilParams.getToke());
        var xhrReq = $.ajax({
            url: requestUtilParams.host + actionName,
            type: "post",
            contentType: false,
            processData: false,
            data: data,
            dataType: "json",
            success: function (res) {
            }, error: function (res, status, xr) {
                requestUtilParams.ajaxError(res, status, xr)
            },
            complete: function (res) {
                requestUtilParams.getToke(1);
            }
        });
        return xhrReq;
    }
    /**
     * @title:<h3> ajax异常处理 <h3>
     * @author: Danpil
     * @date: 2018-10-24 11:30
     * @params
     * @return
     **/
    , ajaxError: function (res, status, xr) {
        if (res.responseJSON != null) {
            console.log("请求失败：" + res.responseJSON.message);
            alert(res.responseJSON.message)
        } else {
            alert("请求失败");
        }
    }
    /**
     * @title:<h3>获得token值  <h3>
     * @author: Danpil
     * @date: 2018-10-23 15:48
     * @params [isrefresh==1刷新token]
     * @return
     **/
    , getToke: function (isrefresh) {
        if (isrefresh == 1) {
            requestUtilParams.token = new Date().getTime();
        } else if (requestUtilParams.token == null || requestUtilParams.token == "") {
            requestUtilParams.token = new Date().getTime();
        }
        return requestUtilParams.token;
    }
    /**
     * @title:<h3> 转化文件路径 <h3>
     * @author: Danpil
     * @date: 2018-10-26 10:23
     * @params
     * @return
     **/
    , convertFilePath: function (save_path) {
        return requestUtilParams.host + "files/upload/" + save_path;
    }
    /**
     * @title:<h3> 分页查询公共方法 <h3>
     * @author: Danpil
     * @date: 2018-10-18 9:15
     * @params
     * @return
     **/
    , findPageData: function (actionName, cpage, tableNode, printPageNode, jumpMethod) {
        if (cpage == null) {
            if ($("#" + printPageNode + " .layui-laypage-curr").length > 0) {//如果存在当前页，则页码为当前页
                cpage = $("#" + printPageNode + " .layui-laypage-curr").text()
            } else {
                cpage = 1;
            }
        }
        var data = "baseModel.queryParams.curr_page=" + cpage + "&baseModel.queryParams.page_size=" + requestUtilParams.pageSize;
        data += requestUtilParams.queryCondition;
        var xhr = requestUtilParams.xhr(actionName, data).done(function (res) {
            //alert("请求成功")
            var list = res.data.list;
            var $tbody = $(tableNode + " tbody");
            $(tableNode + " thead th>input[type='checkbox']").prop("checked", false);
            $tbody.empty();
            layui.use('laypage', function () {
                var laypage = layui.laypage;
                //执行一个laypage实例
                laypage.render({
                    elem: printPageNode //注意，这里的 test1 是 ID，不用加 # 号
                    , count: res.data.total //数据总数，从服务端得到
                    , curr: res.data.prePage + 1//当前页
                    , limit: res.data.pageSize//每页显示的记录数
                    , layout: ['prev', 'page', 'next', 'refresh']
                    , jump: function (obj, first) {//回调函数
                        //obj包  含了当前分页的所有参数，比如：
                        //console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        //console.log(obj.limit); //得到每页显示的条数
                        //首次不执行
                        if (!first) {
                            //do something
                            jumpMethod(obj.curr);
                        }
                    }
                });
            });
        });

        return xhr;

    }
    /**
     * @title:<h3> 删除数据 <h3>
     * @author: Danpil
     * @date: 2018-10-26 14:09
     * @params
     * @return
     **/
    , deleteData: function (actionName, pk, tableNode, callbackMethod) {
        if (pk == null || pk == "") {//如果未传入pk主键，说是批量删除
            var ids = "";
            $(tableNode + " tbody input.inpDel:checked").each(function () {
                ids += "," + $(this).val();
            });
            if (ids.length > 0) {
                pk = ids.substr(1);
            } else {
                alert("请至少选择一条删除的数据");
                return false;
            }
        }
        if (confirm("确认删除？")) {
            requestUtilParams.xhrGet(actionName + pk).done(function (res) {
                alert(res.message);
                callbackMethod();
            });
        }
    }

    /**
     * @title:<h3> 删除有权限按钮的隐藏class <h3>
     * @author: Danpil
     * @date: 2018-11-20 15:22
     * @params
     * @return
     **/
    , removePermissionBtns: function (node) {
        if (node != null && node != "") {
            $(node).removeClass("hidden_per");
        }
    }

    /**
     * 下载文件
     */
    , downFile: function (actionName, params) {
        var form = $("<form action='" + requestUtilParams.host + actionName
            + "' method='post' target='' style='display:none;'></form>");
        if (params != null && params != "") {
            var arrs = params.split("&");
            for (var i = 0; i < arrs.length; i++) {
                var name = arrs[i].substr(0, arrs[i].indexOf("="));
                var value = arrs[i].substr(arrs[i].indexOf("=") + 1);
                form.append("<input type='hidden' name='" + name + "' value='" + value + "'>");
            }
        }
        $('body').append(form); // 将表单放置在web中
        form.submit();
        form.remove();
    }
    /**
     * @title:<h3> 导出 <h3>
     * @author: Danpil
     * @date: 2018-11-21 11:39
     * @params
     * @return
     **/
    , expData: function (actionName, cpage) {
        if (cpage == null || cpage == "") {
            cpage = 1;
        }
        var data = "baseModel.queryParams.curr_page=" + cpage + "&baseModel.queryParams.page_size="
            + requestUtilParams.pageSize+ requestUtilParams.queryCondition;
        requestUtilParams.downFile(actionName, data);
    }
    /**
     * @title:<h3> 导入 <h3>
     * @author: Danpil
     * @date: 2018-11-21 14:27
     * @params
     * @return
     **/
    ,impData: function (actionName) {
        var node = "#formImportTemp";
        if ($("#formImportTemp").length == 0) {//判断form表单是否存在
            $("body").append("<form id='" + node.substr(1) + "' class='hidden'></form>");
        }
        if ($("#importTempFile").length == 0) {//判断选择文件input是否存在
            $(node).append("<input id='importTempFile' type='file' name='baseModel.tempMFile'>");
            $("#importTempFile").change(function () {//触发文件上传请求
                requestUtilParams.xhrUpload(actionName,"#formImportTemp").done(function (res) {
                    alert(res.message);
                });
            });
        }
        $("#importTempFile").click();//点击选择文件
    }
    /**
     * @title:<h3>创建高级查询对话框  <h3>
     * @author: Danpil
     * @date: 2018-10-10 15:16
     * @params 【model:查询参数模型,dialogId：对话框id】
     * @return
     **/
    , createAdvancedQueryDialog: function (model, dialogId, callbackMethod) {
        if (dialogId == null) {
            dialogId = "#dialogAdvancedQuery_" + new Date().getTime();//设置高级查询对话框唯一id
        }
        if ($(dialogId).length > 0) {
            layui.use(["layer"], function () {
                var layer = layui.layer;
                var index = layer.open({
                    type: 1,
                    content: $(dialogId)
                });
                layer.style(index, {
                    width: '700px',
                });
            });
            return;
        }

        //如果不存在对话框,则创建对话框
        $("body").append('<div class="auxiliaryGroup"><div class="layui-tab dialogAdvancedQuery" id="' + dialogId.substr(1) + '">\n' +
            '  <ul class="layui-tab-title">\n' +
            '    <li class="layui-this">快速查询</li>\n' +
            '    <li>高级查询</li>\n' +
            '  </ul>\n' +
            '  <div class="layui-tab-content">\n' +
            '    <div class="layui-tab-item layui-show"><form class="layui-form formQueryGroup"><div class="queryItemGroup layui-form-item"></div>' +
            '  <div class="layui-form-item">\n' +
            '    <div class="layui-input-block">\n' +
            '      <button class="layui-btn btnSearch" type="button">搜索</button>\n' +
            '      <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
            '    </div>\n' +
            '  </div></form></div>\n' +
            '    <div class="layui-tab-item"><form class="layui-form formAdvancedQuery"><div class="advancedQueryItemGroup layui-form-item"></div>' +
            '  <div class="layui-form-item">\n' +
            '    <div class="layui-input-block">\n' +
            '      <button class="layui-btn btnAddQueryItem" type="button">追加条件</button>\n' +
            '      <button class="layui-btn" type="button">搜索</button>\n' +
            '      <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
            '    </div>\n' +
            '  </div></form></div>\n' +
            '  </div>\n' +
            '</div></div>');
        var $queryGroup = $(dialogId + " .queryItemGroup");
        //添加快速查询项
        $.each(model, function (k, v) {
            var $item = $("<div class='layui-col-xs6 layui-col-sm6 layui-col-md6'><label class='layui-form-label'>" + v.comment + "</label><div class='layui-input-inline inputGroup'></div></div>");
            if (v.item != null) {//如果是下拉选项
                var $select = $("<select name='" + k + "' class='search'><option value=''>不限</option></select>");
                $.each(v.item, function (kk, vv) {
                    $select.append("<option value='" + kk + "'>" + vv + "</option>");
                });
                $item.find(".inputGroup").append($select);
            } else if (v.type == "date") {//如果是日期
                $item.find(".inputGroup").append(" <input class='layui-input searchItemDate search' readonly type='text' name='" + k + "'>");
            } else if (v.type == "datetime") {
                $item.find(".inputGroup").append(" <input class='layui-input searchItemDatetime search' readonly type='text' name='" + k + "'>");
            } else {
                $item.find(".inputGroup").append(" <input class='layui-input search' type='text' name='" + k + "'>");
            }
            $queryGroup.append($item);
        });
        $queryGroup.append("<div class='layui-col-xs6 layui-col-sm6 layui-col-md6'><label class='layui-form-label'>分页大小</label><div class='layui-input-inline'><input class='layui-input page_size' type='text' name='page_size'></div></div>");
        $queryGroup.append($queryGroup);//添加快速查询搜索内容
        //添加高级查询项
        var $advancedQueryGroup = $(dialogId + " .advancedQueryItemGroup");//高级查询条件容器
        //定义添加高级查询项方法
        function addAdvancedItem() {
            var $item = $("<div class='layui-form-item'></div>");
            //添加下拉框（且、或）
            $item.append("<div class='layui-inline logicalGroup'><select class='logical'><option>且</option><option>或</option></select></div>");
            //添加下拉框字段选择
            var $selectField = $("<select class='field'></select>");
            $.each(model, function (k, v) {
                $selectField.append("<option value='" + k + "'>" + v.comment + "</option>");
            });
            $item.append($("<div class='layui-inline fieldGroup'></div>").append($selectField));
            //添加下拉菜单关系运算符
            var $selectRelational = $("<select class='relational'></select>");
            $selectRelational.append("<option value=''>包含</option>");
            $selectRelational.append("<option value=''>以...开始</option>");
            $selectRelational.append("<option value=''>以...结尾</option>");
            $selectRelational.append("<option value=''>等于</option>");
            $selectRelational.append("<option value=''>大于</option>");
            $selectRelational.append("<option value=''>大于等于</option>");
            $selectRelational.append("<option value=''>小于</option>");
            $selectRelational.append("<option value=''>小于等于</option>");
            $selectRelational.append("<option value=''>不等于</option>");
            $selectRelational.append("<option value=''>IN</option>");
            $item.append($("<div class='layui-inline relationalGroup'></div>").append($selectRelational));
            //添加搜索值输入框
            $item.append("<div class='layui-inline'><input class='layui-input' type='text'></div>");
            //添加排序
            $item.append("<a class='searchSorting'><i class='fa fa-arrow-up'></i></a><a class='searchSorting'><i class='fa fa-arrow-down'></i></a>");
            $advancedQueryGroup.append($item);//添加高级查询项
            layui.use("form", function () {
                var form = layui.form;
                form.render(); //刷新渲染表单
            });
        }

        addAdvancedItem();//添加高级查询项

        //生成高级查询对话框
        layui.use(["layer", "element", "form", "laydate"], function () {
            var layer = layui.layer;
            var element = layui.element;
            var form = layui.form;
            var laydate = layui.laydate;
            var index = layer.open({
                title: "高级查询",
                type: 1,
                content: $(dialogId) //这里content是一个普通的String
            });
            layer.style(index, {
                width: '700px',
            });
            form.render(); //刷新渲染表单
            //日期选择器
            laydate.render({
                elem: dialogId + ' .queryItemGroup input.searchItemDate'
                , type: 'date'
            });
            //日期选择器
            laydate.render({
                elem: dialogId + ' .queryItemGroup input.searchItemDatetime'
                , type: 'datetime'
            });
            $(dialogId + " .btnAddQueryItem").click(function () {//高级查询框追加查询项
                addAdvancedItem();
            });
            $(dialogId).on("click", ".searchSorting", function () {//排序事件
                $(this).siblings(".searchSorting").removeClass("selected");//删除同级选中效果
                $(this).addClass("selected");//添加当前选中效果
            });
            //快速查询搜索事件
            $(dialogId + " .btnSearch").on("click", function () {
                requestUtilParams.searchForm(dialogId + " .formQueryGroup");
                if ($(dialogId + " .page_size").val() != "") {
                    requestUtilParams.pageSize = $(dialogId + " .page_size").val();
                }
                callbackMethod();//分页查询方法
            });
        });

    }
    /**
     * @title:<h3>表单快速查询  <h3>
     * @author: Danpil
     * @date: 2018-10-29 14:17
     * @params 【formNode：表单id】
     * @return
     **/
    , searchForm: function (formNode) {
        var arr = [];
        //[["su.account","like","zhang"],["su.name","like","张"]]
        $(formNode + " :input.search").each(function () {
            var name = $(this).attr("name");//获得查询字段的name
            var value = $(this).val();//获得查询字段的值
            var item = [name, "", value];//设置查询字段
            //su.account like '%zhang%'
            arr.push(item);
        });
        requestUtilParams.setAdvancedQuery(arr);
    }
    /**
     * @title:<h3> 解析二维数组，设置高级查询条件 <h3>
     * @author: Danpil
     * @date: 2018-10-29 14:28
     * @params
     * @return
     **/
    , setAdvancedQuery: function (queryArr) {
        var queryCondition = "";
        for (var i = 0; i < queryArr.length; i++) {
            // queryArr[i]=["su.account","like","zhang"]
            queryCondition += "&baseModel.listAdvancedQuery[" + i + "].fieldName=" + queryArr[i][0];
            if (queryArr[i][1] != "") {
                queryCondition += "&baseModel.listAdvancedQuery[" + i + "].tempOperator=" + queryArr[i][1];
            }
            if (queryArr[i][2] != "") {
                queryCondition += "&baseModel.listAdvancedQuery[" + i + "].fieldValue=" + queryArr[i][2];
            }
        }
        requestUtilParams.queryCondition = queryCondition;
        //console.log(queryCondition);
    }
}