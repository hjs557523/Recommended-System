package com.dc.service.impl;

import com.dc.mapper.RouteMapper;
import com.dc.mapper.TicketCountMapper;
import com.dc.mapper.TrainMapper;
import com.dc.pojo.Route;
import com.dc.pojo.TicketCount;
import com.dc.pojo.TrainAndTicket;
import com.dc.pojo.TrainInfo;
import com.dc.service.SearchTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:11
 * @Modified By:
 */
@Service
public class SearchTicketServiceImpl implements SearchTicketService {
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private RouteServiceImpl routeServiceImpl;
    @Autowired
    private TicketCountMapper ticketCountMapper;

    /*基本功能：根据车次信息进行余票查询，整合出具体车次信息+余票信息*/
    /*
      用户输入出发站：chufazhan
              目的站：mudizhan
              出发日期：chufashijian
              直达路线查询：flag
     */
    public ArrayList<TrainAndTicket> getTrainAndTicketList(String chufazhan, String mudizhan, Date chufashijian, int flag) {

        ArrayList<TrainAndTicket> resultList = new ArrayList<TrainAndTicket>();//存储车次信息和余票信息
        List<TrainInfo> routeList1 = new ArrayList<TrainInfo>();//存储“出发站-目的站”直达路线信息
        List<List<Route>> routeList2 = new ArrayList<List<Route>>();//存储“出发站-中转站,中转站-目的站”列表信息

        if(flag==1){//中转站路线的车次信息和余票信息整合.
            routeList2 = this.routeServiceImpl.findTransferStation(chufazhan,mudizhan);

            for (int i = 0; i < routeList2.get(0).size(); i=i+2) {

                try {
                    TrainAndTicket trainAndTicket1 = new TrainAndTicket();
                    TrainAndTicket trainAndTicket2 = new TrainAndTicket();

                    TrainInfo trainInfo1 = new TrainInfo();
                    TrainInfo trainInfo2 = new TrainInfo();

                    HashMap<String, Object> input1 = new HashMap<String, Object>();
                    HashMap<String, Object> input2 = new HashMap<String, Object>();

                    Calendar c = Calendar.getInstance();
                    c.setTime(chufashijian);
                    c.add(Calendar.DAY_OF_MONTH,1);
                    input1.put("sTime",chufashijian);
                    input1.put("eTime",c.getTime());//出发日期+1
                    input1.put("tid",routeList2.get(0).get(i).getTrainId());



                    input2.put("sTime",chufashijian);
                    input2.put("eTime",c.getTime());//出发日期+1
                    input2.put("tid",routeList2.get(1).get(i).getTrainId());

                    TicketCount ticketCount1 = this.ticketCountMapper.searchTicket(input1);//中转路线车次1所对应的余票信息
                    TicketCount ticketCount2 = this.ticketCountMapper.searchTicket(input2);//中转路线车次2所对应的余票信息

                    //中转路线车次1信息
                    String trainNum1 = this.trainMapper.selectByPrimaryKey(routeList2.get(0).get(i).getTrainId()).getTrainNum();
                    trainInfo1.setTrainId(routeList2.get(0).get(i).getTrainId());
                    trainInfo1.setTrainNum(trainNum1);
                    trainInfo1.setChufazhan(routeList2.get(0).get(i).getStationName());
                    trainInfo1.setMudizhan(routeList2.get(0).get(i+1).getStationName());
                    trainInfo1.setRouteSeq1(routeList2.get(0).get(i).getRouteSeq());
                    trainInfo1.setRouteSeq2(routeList2.get(0).get(i+1).getRouteSeq());

                    SimpleDateFormat adf1 = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat adf2 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat adf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String sfm1 = adf1.format(routeList2.get(0).get(i).getDepartureTime());//提取出发时间的时分秒
                    String sfm2 = adf1.format(routeList2.get(0).get(i+1).getArriveTime());//提取到达时间的时分秒
                    String nyr = adf2.format(chufashijian);//提取用户输入的年月日
                    String cfsj = nyr+" "+sfm1;//整合出发时间
                    String ddsj = nyr+" "+sfm2;//整合到达时间


                    Date time1 = adf3.parse(cfsj);
                    Date time2 = adf3.parse(ddsj);
                    trainInfo1.setDepartureTime(time1);
                    trainInfo1.setArriveTime(time2);


                    //中转路线车次2信息
                    String trainNum2 = this.trainMapper.selectByPrimaryKey(routeList2.get(1).get(i).getTrainId()).getTrainNum();
                    trainInfo2.setTrainId(routeList2.get(1).get(i).getTrainId());
                    trainInfo2.setTrainNum(trainNum2);
                    trainInfo2.setChufazhan(routeList2.get(1).get(i).getStationName());
                    trainInfo2.setMudizhan(routeList2.get(1).get(i+1).getStationName());
                    trainInfo2.setRouteSeq1(routeList2.get(1).get(i).getRouteSeq());
                    trainInfo2.setRouteSeq2(routeList2.get(1).get(i+1).getRouteSeq());


                    String sfm3 = adf1.format(routeList2.get(1).get(i).getDepartureTime());//提取出发时间的时分秒
                    String sfm4 = adf1.format(routeList2.get(1).get(i+1).getArriveTime());//提取到达时间的时分秒
                    String nyr1 = adf2.format(chufashijian);//提取用户输入的年月日
                    String cfsj1 = nyr1+" "+sfm3;//整合出发时间
                    String ddsj1 = nyr1+" "+sfm4;//整合到达时间


                    Date time3 = adf3.parse(cfsj1);
                    Date time4 = adf3.parse(ddsj1);
                    trainInfo2.setDepartureTime(time3);
                    trainInfo2.setArriveTime(time4);
                    if (time3.before(time2))//如果从车次2的中转站出发时间 > 车次1的中转站到达时间，不进行整合，跳出本次循环
                        break;
                    else {

                        //中转路线车次1及余票整合信息
                        trainAndTicket1.setTicketCount(ticketCount1);
                        trainAndTicket1.setTrainInfo(trainInfo1);

                        //中转路线车次2及余票整合信息
                        trainAndTicket2.setTicketCount(ticketCount2);
                        trainAndTicket2.setTrainInfo(trainInfo2);

                        resultList.add(trainAndTicket1);
                        resultList.add(trainAndTicket2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{//直达路线的车次信息和余票信息整合

            routeList1 =  this.routeMapper.selectByStation(chufazhan,mudizhan);//直达路线车次信息

            for (int i = 0; i < routeList1.size(); i++) {
                TrainAndTicket trainAndTicket = new TrainAndTicket();
                HashMap<String, Object> input = new HashMap<String, Object>();
                input.put("sTime",chufashijian);
                Calendar c = Calendar.getInstance();
                c.setTime(chufashijian);
                c.add(Calendar.DAY_OF_MONTH,1);
                input.put("eTime",c.getTime());//出发日期+1
                input.put("tid",routeList1.get(i).getTrainId());
                TicketCount ticketCount = this.ticketCountMapper.searchTicket(input);//直达路线车次所对应的余票信息
                TrainInfo trainInfo = new TrainInfo();
                trainInfo.setTrainId(routeList1.get(i).getTrainId());
                trainInfo.setTrainNum(routeList1.get(i).getTrainNum());
                trainInfo.setChufazhan(routeList1.get(i).getChufazhan());
                trainInfo.setMudizhan(routeList1.get(i).getMudizhan());

                //我想问问学长这种日期转换的方式是否会很麻烦......
                SimpleDateFormat adf1 = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat adf2 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat adf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sfm1 = adf1.format(routeList1.get(i).getDepartureTime());//提取出发时间的时分秒
                String sfm2 = adf1.format(routeList1.get(i).getArriveTime());//提取到达时间的时分秒
                String nyr = adf2.format(chufashijian);//提取用户输入的年月日
                String cfsj = nyr+" "+sfm1;//整合出发时间
                String ddsj = nyr+" "+sfm2;//整合到达时间
                try {
                    Date time1 = adf3.parse(cfsj);
                    Date time2 = adf3.parse(ddsj);
                    trainInfo.setDepartureTime(time1);
                    trainInfo.setArriveTime(time2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                trainInfo.setRouteSeq1(routeList1.get(i).getRouteSeq1());
                trainInfo.setRouteSeq2(routeList1.get(i).getRouteSeq2());

                trainAndTicket.setTicketCount(ticketCount);
                trainAndTicket.setTrainInfo(trainInfo);

                resultList.add(trainAndTicket);//直达路线的车次信息和余票信息的整合
            }
        }

        return resultList;

    }
}