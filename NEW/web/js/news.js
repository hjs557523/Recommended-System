$(function () {
    getUserInfoByCookie()
    findArticleInfo()
    findCommentContext()
    // submitComment()
    $("#submitComment").on("click", function () {
        submitComment()
    })
})


//从cookie中找是否有登陆状态
function getUserInfoByCookie() {
    if ($.cookie("loginState") == 1) {
        // alert("已登录" + $.cookie("user_account"))
        $("#userInfo").empty()
        $("#userInfo").append("<li><a href='#'>" + $.cookie("user_account") + "</a></li>")
        $("#userInfo").append("<li><a href='#' onclick='logout()'>注销</a></li>")
        $("#submitComment").removeAttr("disabled");
    } else {
        $("#userInfo").empty()
        $("#userInfo").append("<li><a href=\"login.html\">登录</a></li>")
        $("#userInfo").append("<li><a href=\"signup.html\">注册</a></li>")
        $("#submitComment").attr('disabled', "true");
    }
}

//注销
function logout() {
    $.cookie("loginState", 0)
    $.cookie("user_account", null)
    $.cookie("user_password", null)
    alert("拜拜")
    getUserInfoByCookie()
}


//提交评论
function submitComment() {
    var commentText = $("#CommentText").val()

    if (commentText == "" || commentText == null) {
        alert("请输入评论内容！")
        return
    }
    if (commentText.length > 50) {
        alert("评论内容不能超过50字符！")
        return
    }
    var scenic_id = getUrlParamsScenicId();
    var user_id = $.cookie("user_id");
    var src = "article/commitComment?scenic_id=" + scenic_id + "&user_id=" + user_id + "&comment_text=" + commentText
    $.ajax({
        url: src,
        type: "get",
        dataType: "json",
        async: true,
        success: function (res) {
            if (res.resultCode == 0) {
                alert("评论成功")
            } else {
                alert("评论失败")
            }
            findCommentContext()
            $("#CommentText").val("");
        },
        error: function (res, status, xr) {
        },
        complete: function (res) {
        }
    })
}

//获取地址栏文章id参数
function getUrlParamsArticleId() {
    var articleId = baseUtilParams.GetQueryString("articleId")
    return articleId
}

//获取地址栏景点id参数
function getUrlParamsScenicId() {

    var scenicId = baseUtilParams.GetQueryString("scenicId")
    return scenicId
}

//获取景点评论信息
function findCommentContext() {
    var id = getUrlParamsScenicId()
    if (id == "" || id == null) {
        alert("数据异常！")
        window.location.href = "index.html"
    }

    var src = "article/selectAllCommentByScenicId?scenic_id=" + id
    $.ajax({
        url: src,
        type: "get",
        dataType: "json",
        async: true,
        success: function (res) {
            var list = res.data;
            $("#commentTable").empty();
            $("#commentTable").append("<tr><th>评论人</th><th>评论内容</th><th>评论时间</th></tr>")
            for (var i = 0; i < list.length; i++) {
                $("#commentTable").append(
                    "                <tr>\n" +
                    "                    <td width='25%'>" + list[i].commentator_account + "</td>\n" +
                    "                    <td width='50%'>" + list[i].comment_text + "</td>\n" +
                    "                    <td width='25%'>" + list[i].comment_time.substr(0, 19) + "</td>\n" +
                    "                </tr>")
                $("#commentTable").append("<hr>")
            }
        },
        error: function (res, status, xr) {
        },
        complete: function (res) {
        }
    })

}


//查询文章详细信息
function findArticleInfo() {
    var id = getUrlParamsArticleId()
    if (id == "" || id == null) {
        alert("数据异常！")
        window.location.href = "index.html"
    }
    var src = "article/selectArticleById?article_id=" + id
    $("#articleContent").empty()
    $.ajax({
        url: src,
        type: "get",
        dataType: "json",
        async: true,
        success: function (res) {
            var list = res.data;
            $("#articleContent").append("<h1 class=\"title\">" + list.article_title + "</h1>")
            $("#articleContent").append("<p class=\"info\">\n" +
                "            <span>" + list.author_account + "</span> •\n" +
                "            <span>" + list.article_time.substr(0, 19) + "</span>\n" +
                "        </p>")
            $("#articleContent").append("<div class=\"content\">\n" +
                "            <img src=\"img/xihu1.png\" alt=\"\">\n" +
                "            <div>" + list.article_text + "</div></div>")
        },
        error: function (res, status, xr) {
        },
        complete: function (res) {
        }
    });
}