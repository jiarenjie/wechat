package com.jiarj.ctl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiarj.entity.Result;
import com.jiarj.entity.SingleAccessToken;
import com.jiarj.entity.WechatMsg;
import com.jiarj.enums.ResultEnum;
import com.jiarj.exceptions.BusinessException;
import com.jiarj.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.jiarj.service.WechatService;
import com.jiarj.utils.RestUtils;
import com.jiarj.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@Transactional
@RequestMapping()
public class WechatCtrl {
    private static Logger LOGGER = LoggerFactory.getLogger(WechatCtrl.class);

    @Value("${wechat.token}")
    private String token;
    @Value("${wechat.encodingAesKey}")
    private String encodingAesKey;
    @Value("${wechat.appId}")
    private String appId;

    @Resource(name = "wechatService")
    private WechatService wechatService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Result<Object> verify() {
        return ResultUtil.success("hello world !");
    }

    @RequestMapping(value = "/wechat",method = RequestMethod.GET)
    public String verify(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp
            ,@RequestParam("nonce") String nonce,@RequestParam("echostr") String echostr) {
        String s = "";
        try {
            WXBizMsgCrypt mc = new WXBizMsgCrypt(token,encodingAesKey,appId);
            s = mc.verify(signature,timestamp,nonce,echostr);

        } catch (Exception e) {
            throw new BusinessException(ResultEnum.ERROR);
        }
        return s;
    }

    @RequestMapping(value = "/wechat",method = RequestMethod.POST)
    public String message(@RequestBody String postData) {
        try {
            WechatMsg wechatMsg = wechatService.wechatMsg(postData);
            String str  = wechatService.replay(wechatMsg);
            return str;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.ERROR);
        }
    }

    @RequestMapping(value = "/menu/create",method = RequestMethod.GET)
    public Result createMenu() {
        try {
            SingleAccessToken instance = SingleAccessToken.getInstance();
            String accesstoken = instance.getAccesstoken();

            String postParam = "{\"button\":[{\"type\":\"view\",\"name\":\"主页\",\"url\":\"http://47.110.40.135/static\"},{\"name\":\"一级菜单2\",\"sub_button\":[{\"type\":\"click\",\"name\":\"点击1\",\"key\":\"V1001_click1\"},{\"type\":\"click\",\"name\":\"点击2\",\"key\":\"V1001_click2\"}]}]}\n";

            String tmp = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%1$s";
            String url = String.format(tmp, accesstoken);
            String result = RestUtils.post(url, postParam);
            LOGGER.debug("postUrl:{},postBody:{},result:{}",url,postParam,result);

            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(result, Map.class);
            return ResultUtil.success(map);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.ERROR);
        }
    }



}
