$(function () {
    getUserInfoByCookie()
    findAllArticle("", 1, 10);
    $("#searchButton").click(function () {
        findAllArticle($("#searchText").val(), 1, 10);
    })

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
        $("#userInfo").append("<li><a href=\"login.html\">登录</a></li>")
        $("#userInfo").append("<li><a href=\"signup.html\">注册</a></li>")
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


//页面详细信息传参id
function ArticleInfoPage(articleId, scenicId) {
    window.location.href = "news.html?articleId=" + articleId + "&scenicId=" + scenicId
}

//分页点击
function pageNavigation(num) {
    var pageNum = num;
    findAllArticle($("#searchText").val(), pageNum, 10);
}

//查询文章列表
function findAllArticle(title, page, maxSize) {

    var page;
    var maxSize;
    var title;
    var src;
    var userId = $.cookie("user_id")
    if (page == "") {
        page = 1;
    }
    if (maxSize == "") {
        maxSize = 10;
    }

    if(userId == 0){
        console.log("userId确实为null了，下面可以进入")
    }else {
        console.log("userId="+userId);
        console.log(typeof (userId));
    }
    if (userId == "" || userId == 0 || userId == undefined) {
        if (title==null||title == "" || title == "搜索景点") {
            src = "article/selectArticleAll2?page=" + page + "&maxSize=" + maxSize + "&userId=NULL"
            console.log("进入1");
        }else{ src = "article/selectArticleAll2?page=" + page + "&maxSize=" + maxSize + "&title=" + title + "&userId=NULL"
            console.log("进入2");
        }


    } else if (title == "" || title == "搜索景点") {
        src = "article/selectArticleAll?page=" + page + "&maxSize=" + maxSize + "&userId=" + userId
        console.log("进入3");
        console.log("userId=="+userId);
    } else {
        src = "article/selectArticleAll2?title=" + title + "&page=" + page + "&maxSize=" + maxSize + "&userId=" + userId
        console.log("userId=="+userId);
        console.log("进入4");
    }

    $.ajax({
        url: src,
        type: "get",
        dataType: "json",
        async: true,
        success: function (res) {
            var list = res.data.list;
            $("#articleList").empty()
            if (list.length == 0) {
                $("#articleList").append("<div class=\"post-list-item\">暂无推荐信息</div>")
            }
            for (var i = 0; i < list.length; i++) {
                $("#articleList").append("<div class=\"post-list-item\">\n" +
                    "                <div class=\"col-xs-5\">\n" +
                    "                    <img class=\"post-object\" src=\"img/xihu"+i+".png\">\n" +
                    "                </div>\n" +
                    "                <div class=\"col-xs-7\">\n" +
                    "                    <a href='#' onclick='ArticleInfoPage(" + list[i].article_id + "," + list[i].scenic_id + ")'>\n" +
                    "                        <div class=\"post-heading\">" + list[i].article_title + "</div>\n" +
                    "                    </a>\n" +
                    "                    <p>" + list[i].article_text.substr(0, 65) + "...</p>\n" +
                    "                    <p class=\"text-muted\">\n" +
                    "                        <span>\n" +
                    "                            <img class=\"avatar\" src=\"img/logo4.png\">\n" +
                    "                        " + list[i].author_account +
                    "                        </span> ⋅\n" +
                    "                        <span>\n" +
                    "                        " + list[i].article_time.substr(0, 19) +
                    "                        </span> \n" +
                    "                    </p>\n" +
                    "                </div>\n" +
                    "            </div>")
            }

            var pageCount = res.data.total
            $("#pageNavi").empty();
            $("#pageNavi").append("<li>\n" +
                "                    <a href=\"#\" aria-label=\"Previous\">\n" +
                "                        <span aria-hidden=\"true\">&laquo;</span>\n" +
                "                    </a>\n" +
                "                </li>")
            for (var i = 0; i < pageCount / 10; i++) {
                $("#pageNavi").append("<li><a onclick='pageNavigation(" + (i + 1) + ")' href=\"#\" value='" + (i + 1) + "'>" + (i + 1) + "</a></li>")
            }
            $("#pageNavi").append("<li>\n" +
                "                    <a href=\"#\" aria-label=\"Next\">\n" +
                "                        <span aria-hidden=\"true\">&raquo;</span>\n" +
                "                    </a>\n" +
                "                </li>")
        },
        error: function (res, status, xr) {
        },
        complete: function (res) {
        }
    });
}


function clearCookie(name) {

}