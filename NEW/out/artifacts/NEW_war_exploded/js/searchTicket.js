$(function () {
    getUserInfoByCookie()
})

//从cookie中找是否有登陆状态
function getUserInfoByCookie() {
    if ($.cookie("loginState") == 1) {
        console.log(typeof($.cookie("loginState")));
        // alert("已登录" + $.cookie("user_account"))
        $("#userInfo").empty()
        $("#userInfo").append("<li><a href='#'>" + $.cookie("user_account") + "</a></li>")
        $("#userInfo").append("<li><a href='#' onclick='logout()'>注销</a></li>")
    } else {
        $("#userInfo").empty()
        $("#userInfo").append("<li><a href=\"../login.html\">登录</a></li>")
        $("#userInfo").append("<li><a href=\"../signup.html\">注册</a></li>")
    }
}

//注销
function logout() {
    $.cookie("loginState", 0)
    $.cookie("user_id", 0)
    $.cookie("user_account", null)
    $.cookie("user_password", null)
    alert("您已成功退出系统！")
    getUserInfoByCookie();
    console.log($.cookie("user_id"));
    findAllArticle("", 1, 10);
}

function clearCookie(name) {

}