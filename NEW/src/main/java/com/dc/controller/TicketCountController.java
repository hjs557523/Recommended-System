package com.dc.controller;

import com.dc.base.controller.BaseController;
import com.dc.service.TicketCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 17:59
 * @Modified By:
 */

@Controller
public class TicketCountController{


    @Autowired
    private TicketCountService ticketCountService;

    @RequestMapping(value = "/refreshticket", method = RequestMethod.GET)
    public String refreshTicket() {
        ticketCountService.generateTicket();

        return "admin/form-amazeui.jsp";
    }
}
