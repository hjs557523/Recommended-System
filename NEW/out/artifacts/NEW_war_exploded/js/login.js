$(function () {
    getSignUpAccount()
    $("#loginButton").on("click", function () {
        Login()
    })
})

//获取注册成功账号
function getSignUpAccount() {
    var user_account = $.cookie("user_account")
    if (user_account = "null") {
        return
    }
    if (user_account == null) {
        return
    }
    if (user_account != "" || user_account != null) {
        $("#user_account").val(user_account)
    }
}


//为空判断
function isNull(str) {
    if (str == null || str == "" || str.length <= 0) {
        return false;
    }
    return true;
}

//登录
function Login() {
    var user_account = $("#user_account").val()
    var user_password = $("#user_password").val()
    if (!isNull(user_account)) {
        alert("账号密码不能为空")
        return
    }
    if (!isNull(user_password)) {
        alert("账号密码不能为空")
        return
    }
    var src = "user/login?user_account=" + user_account + "&user_password=" + user_password
    $.ajax({
        url: src,
        type: "get",
        dataType: "json",
        async: true,
        success: function (res) {
            if (res.resultCode == 0) {
                if (user_account == 'admin1'){
                    alert("欢迎管理员！~")
                    window.location.href = "/admin/form-amazeui.jsp"
                    return

                }
                console.log("useraccount = " + user_account);
                alert("登录成功，根据您的喜好，系统即将为您生成如下10个旅游景点推荐~")
                $.cookie("loginState", 1)
                $.cookie("user_id", res.data[0].user_id)
                $.cookie("user_account", user_account)
                $.cookie("user_password", user_password)
                window.location.href = "index.html"
            } else {
                alert("账号密码错误或账号不存在")
            }
            $("#user_account").val("")
            $("#user_password").val("")
        },
        error: function (res, status, xr) {
        },
        complete: function (res) {
        }
    })
}