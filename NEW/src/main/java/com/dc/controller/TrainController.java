package com.dc.controller;

import com.dc.base.controller.BaseController;
import com.dc.base.util.DateUtil;
import com.dc.base.util.StringUtil;
import com.dc.pojo.TrainAndTicket;
import com.dc.service.SearchTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 17:59
 * @Modified By:
 */


@Controller
public class TrainController{
    @Autowired
    private SearchTicketService searchTicketService;

    /**
     * 根据出发站、目的站和出发日期查询符合条件的车次
     * (包含标准查询和中转站查询)
     * @author CrazyWalker
     * @param session http session
     * @param beginStation 出发地站点名称
     * @param targetStation 目的地站点名称
     * @param targetDate 出发日期
     * @param flag 查询标志 是否为高级查询
     * @return 无论是否成功均返回到信息查询页，若成功查询则返回对应数据
     */
    @RequestMapping(value = "/ticketSearch", method = RequestMethod.POST)
    public String ticketSearch(HttpSession session, String beginStation, String targetStation,
                               String targetDate, Integer flag) {
        /*三者值均不能为空*/
        if(StringUtil.isEmpty(beginStation) || StringUtil.isEmpty(targetDate) ||
                StringUtil.isEmpty(targetStation)) {
            if (flag == 1) {
                return "tots/advancedsearch.jsp";
            } else {
                System.out.println("111");
                return "tots/searchTicket.jsp";

            }
        }

        Date tDate = DateUtil.transFormDateToDate(targetDate);

        if (tDate != null) {
            List<TrainAndTicket> trainAndTicketList =
                    searchTicketService.getTrainAndTicketList(beginStation, targetStation, tDate, flag);
            System.out.println("test param1:"+beginStation);
            System.out.println("test param2:"+targetStation);
            System.out.println("test param3:"+tDate);
            System.out.println("test param4:"+flag);
            if (flag == 1) {
                session.setAttribute("adtatList", trainAndTicketList);
                session.setAttribute("isAdPostResponse", true);
            } else {
                session.setAttribute("tatList", trainAndTicketList);
                session.setAttribute("isPostResponse", true);
                System.out.println(trainAndTicketList);
                System.out.println("busy....");
            }

        }

        if (flag == 1) {
            return "tots/advancedsearch.jsp";
        } else {
            System.out.println("222");
            return "tots/searchTicket.jsp";
        }
    }
}
