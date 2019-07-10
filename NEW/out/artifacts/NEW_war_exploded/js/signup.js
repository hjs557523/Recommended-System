$(function () {
    $("#signUpButton").on("click", function () {
        signUp()
    })
})

//为空判断
function isNull(str) {
    if (str == null || str == "" || str.length <= 0) {
        return false;
    }
    return true;
}

//验证账号是否合理
function checkUser(str){
    var reg=/^[a-zA-Z][a-zA-Z0-9]{3,15}$/;
    if(reg.test(str)==false){
        return false;
    }
    return true;
}


//验证密码是否合理
function checkPwd(str){
    var reg=/^[a-zA-Z0-9]{4,10}$/;
    if(reg.test(str)==false){
        return false;
    }
    return true;
}



//注册
function signUp() {
    var user_account = $("#user_account").val()
    var user_password = $("#user_password").val()
    var re_user_password = $("#re_user_password").val()
    if (!isNull(user_account)) {
        alert("账号不能为空")
        return
    }
    
    if (!checkUser(user_account)) {
        alert("账号不合理")
        return;
    }

    if(!checkPwd(user_password)){
        alert("密码不合理")
        return
    }
    
    if (!isNull(user_password)) {
        alert("密码不能为空")
        return
    }
    if (!isNull(re_user_password)) {
        alert("请在次输入密码")
        return
    }
    if (user_password != re_user_password) {
        alert("两次密码不一致")
        return
    }
    var src = "user/register?user_account=" + user_account + "&user_password=" + user_password
    $.ajax({
        url: src,
        type: "get",
        dataType: "json",
        async: true,
        success: function (res) {
            if (res.resultCode == 0) {
                alert("注册成功")
                $.cookie("user_account", user_account)
                window.location.href = "login.html"
            } else {
                alert("账号已存在")
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