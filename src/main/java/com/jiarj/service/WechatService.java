package com.jiarj.service;

import com.jiarj.entity.WechatMsg;
import com.jiarj.enums.ResultEnum;
import com.jiarj.exceptions.BusinessException;
import com.jiarj.mapper.WechatMsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by jiarj on 2018-11-12.
 */

@Service(value = "wechatService")
public class WechatService {

    @Autowired
    private WechatMsgMapper wechatMsgMapper;

    public WechatMsg wechatMsg(String str) {
        try {
            WechatMsg wechatMsg = getWechatMsg(str);
            wechatMsgMapper.insert(wechatMsg);
            return wechatMsg;

        } catch (Exception E) {
            throw new BusinessException(ResultEnum.UNKONW_ERROR);
        }


    }

    public WechatMsg getWechatMsg(String str) throws Exception {

        WechatMsg wechatMsg = new WechatMsg();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(str);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);
        Element root = document.getDocumentElement();

        NodeList toUserName = root.getElementsByTagName("ToUserName");
        String textToUserName = toUserName.item(0).getTextContent();
        wechatMsg.setToUserName(textToUserName);

        NodeList fromUserName = root.getElementsByTagName("FromUserName");
        String textFromUserName = fromUserName.item(0).getTextContent();
        wechatMsg.setFromUserName(textFromUserName);

        NodeList createTime = root.getElementsByTagName("CreateTime");
        String textCreateTime = createTime.item(0).getTextContent();
        wechatMsg.setCreateTime(textCreateTime);

        NodeList msgId = root.getElementsByTagName("MsgId");
        String textMsgId = msgId.item(0).getTextContent();
        wechatMsg.setMsgid(textMsgId);

        NodeList msgType = root.getElementsByTagName("MsgType");
        String textMsgType = msgType.item(0).getTextContent();
        wechatMsg.setMsgType(textMsgType);

        if (textMsgType != null && textMsgType != "") {
            if (textMsgType.equals("text")) {
                NodeList content = root.getElementsByTagName("Content");
                String textContent = content.item(0).getTextContent();
                wechatMsg.setContent(textContent);
            } else if (textMsgType.equals("image")) {
                NodeList picUrl = root.getElementsByTagName("PicUrl");
                String textPicUrl = picUrl.item(0).getTextContent();
                wechatMsg.setPicurl(textPicUrl);

                NodeList mediaId = root.getElementsByTagName("MediaId");
                String textMediaId = mediaId.item(0).getTextContent();
                wechatMsg.setMediaid(textMediaId);

            } else if (textMsgType.equals("voice")) {

                NodeList mediaId = root.getElementsByTagName("MediaId");
                String textMediaId = mediaId.item(0).getTextContent();
                wechatMsg.setMediaid(textMediaId);

                NodeList format = root.getElementsByTagName("Format");
                String textFormat = format.item(0).getTextContent();
                wechatMsg.setFormat(textFormat);

                NodeList recognition = root.getElementsByTagName("Recognition");
                if(recognition.getLength()>0){
                    String textRecognition = recognition.item(0).getTextContent();
                    wechatMsg.setRecognition(textRecognition);
                }
            } else if (textMsgType.equals("video")) {
                NodeList mediaId = root.getElementsByTagName("MediaId");
                String textMediaId = mediaId.item(0).getTextContent();
                wechatMsg.setMediaid(textMediaId);

                NodeList thumbMediaId = root.getElementsByTagName("ThumbMediaId");
                String textThumbMediaId = thumbMediaId.item(0).getTextContent();
                wechatMsg.setThumbMediaid(textThumbMediaId);
            } else if (textMsgType.equals("shortvideo")) {
                NodeList mediaId = root.getElementsByTagName("MediaId");
                String textMediaId = mediaId.item(0).getTextContent();
                wechatMsg.setMediaid(textMediaId);

                NodeList thumbMediaId = root.getElementsByTagName("ThumbMediaId");
                String textThumbMediaId = thumbMediaId.item(0).getTextContent();
                wechatMsg.setThumbMediaid(textThumbMediaId);
            } else if (textMsgType.equals("location")) {
                NodeList location_X = root.getElementsByTagName("Location_X");
                String textLocation_X = location_X.item(0).getTextContent();
                wechatMsg.setLocationx(textLocation_X);

                NodeList location_Y = root.getElementsByTagName("Location_Y");
                String textLocation_Y = location_Y.item(0).getTextContent();
                wechatMsg.setLocationy(textLocation_Y);

                NodeList scale = root.getElementsByTagName("Scale");
                String textScale = scale.item(0).getTextContent();
                wechatMsg.setScale(textScale);

                NodeList label = root.getElementsByTagName("Label");
                String textLabel = label.item(0).getTextContent();
                wechatMsg.setLabel(textLabel);
            } else if (textMsgType.equals("link")) {

                NodeList title = root.getElementsByTagName("Title");
                String textTitle = title.item(0).getTextContent();
                wechatMsg.setTitle(textTitle);

                NodeList description = root.getElementsByTagName("Description");
                String textDescription = description.item(0).getTextContent();
                wechatMsg.setDescription(textDescription);

                NodeList url = root.getElementsByTagName("Url");
                String textUrl = url.item(0).getTextContent();
                wechatMsg.setUrl(textUrl);
            }else if(textMsgType.equals("event")){
                NodeList event = root.getElementsByTagName("Event");
                String textEvent = event.item(0).getTextContent();
                wechatMsg.setEvent(textEvent);
                if(textEvent.equals("subscribe")){
                    NodeList eventKey = root.getElementsByTagName("EventKey");
                    if(eventKey.getLength()>0){
                        String textEventKey = eventKey.item(0).getTextContent();
                        wechatMsg.setEventKey(textEventKey);

                        NodeList ticket = root.getElementsByTagName("Ticket");
                        String textTicket = ticket.item(0).getTextContent();
                        wechatMsg.setTicket(textTicket);
                    }
                }else if(textEvent.equals("unsubscribe")){

                }else if(textEvent.equals("SCAN")){
                    NodeList eventKey = root.getElementsByTagName("EventKey");
                    String textEventKey = eventKey.item(0).getTextContent();
                    wechatMsg.setEventKey(textEventKey);

                    NodeList ticket = root.getElementsByTagName("Ticket");
                    String textTicket = ticket.item(0).getTextContent();
                    wechatMsg.setTicket(textTicket);


                }else if(textEvent.equals("LOCATION")){
                    NodeList latitude = root.getElementsByTagName("Latitude");
                    String textLatitude = latitude.item(0).getTextContent();
                    wechatMsg.setLatitude(textLatitude);

                    NodeList longitude = root.getElementsByTagName("Longitude");
                    String textLongitude = longitude.item(0).getTextContent();
                    wechatMsg.setLongitude(textLongitude);

                    NodeList precision = root.getElementsByTagName("Precision");
                    String textPrecision = precision.item(0).getTextContent();
                    wechatMsg.setPrecision(textPrecision);

                }else if(textEvent.equals("CLICK")){
                    NodeList eventKey = root.getElementsByTagName("EventKey");
                    String textEventKey = eventKey.item(0).getTextContent();
                    wechatMsg.setEventKey(textEventKey);

                }else if(textEvent.equals("VIEW")){
                    NodeList eventKey = root.getElementsByTagName("EventKey");
                    String textEventKey = eventKey.item(0).getTextContent();
                    wechatMsg.setEventKey(textEventKey);
                }

            }
        }


        return wechatMsg;
    }

    public String replay(WechatMsg wechatMsg) {

        String str= "";
        try {
            if(wechatMsg.getMsgType().equals("text")){
                String ToUserName = wechatMsg.getFromUserName();
                String FromUserName = wechatMsg.getToUserName();
                String CreateTime = wechatMsg.getCreateTime();
                String Content = wechatMsg.getContent();
                str = generate(ToUserName,FromUserName,CreateTime,"text",Content);
            }
            return str;
        }catch (Exception e ){
            throw new BusinessException(ResultEnum.UNKONW_ERROR);
        }
    }
    public String generate(String ToUserName, String FromUserName, String CreateTime, String MsgType,String Content) {

        String format = "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
                + "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
                + "<CreateTime>%3$s</CreateTime>\n" + "<MsgType><![CDATA[%4$s]]></MsgType>\n"
                + "<Content><![CDATA[%5$s]]></Content>\n"
                +"</xml>";
        return String.format(format, ToUserName, FromUserName, CreateTime, MsgType,Content);

    }
}
