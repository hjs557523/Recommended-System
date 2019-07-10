/**
 * @title:<h3> 工具js <h3>
 * @author: Danpil
 * @date: 2018-10-10 15:18
 * @params
 * @return
 **/
var baseUtilParams = {
    /**
     * @title:<h3> 時間格式化 <h3>
     * @author: Danpil
     * @date: 2018-10-15 16:18
     * @params
     * @return
     **/
    simpDataFormat: function (time, mat) {
        if (time == null) {
            return "";
        }
        var date = new Date(time);
        if (mat == null) {
            mat = "yyyy-MM-dd HH:mm:ss";
        }
        var yyyy = date.getFullYear();
        var MM = date.getMonth() + 1;
        if (MM < 10) {
            MM = "0" + MM;
        }
        var dd = date.getDate();
        if (dd < 10) {
            dd = "0" + dd;
        }
        var HH = date.getHours();
        if (HH < 10) {
            HH = "0" + HH;
        }
        var mm = date.getMinutes();
        if (mm < 10) {
            mm = "0" + mm;
        }
        var ss = date.getSeconds();
        if (ss < 10) {
            ss = "0" + ss;
        }
        var result = mat.replace("yyyy", yyyy).replace("MM", MM).replace("dd", dd).replace("HH", HH).replace("mm", mm).replace("ss", ss);
        return result;

    }
    /**
     * @title:<h3>时间格式化，年月日  <h3>
     * @author: Danpil
     * @date: 2018-10-22 13:46
     * @params
     * @return
     **/
    , simpDataForYYYMMDD: function (time) {
        return baseUtilParams.simpDataFormat(time, "yyyy-MM-dd");
    }
    /**
     * @title:<h3> 根据参数名称获得地址栏中的参数 <h3>
     * @author: Danpil
     * @date: 2018-10-17 10:00
     * @params
     * @return
     **/
    , GetQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    /**
     * @title:<h3> 字符串null过滤 <h3>
     * @author: Danpil
     * @date: 2018-10-18 11:00
     * @params
     * @return
     **/
    , strNullFilter: function (str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
    /**
     * @title:<h3> 睡眠1秒 <h3>
     * @author: Danpil
     * @date: 2018-11-23 14:24
     * @params
     * @return
     **/
    , sleep: function (numberMillis) {
        var now = new Date();
        var exitTime = now.getTime() + numberMillis;
        while (true) {
            now = new Date();
            if (now.getTime() > exitTime)
                return;
        }
    }
}