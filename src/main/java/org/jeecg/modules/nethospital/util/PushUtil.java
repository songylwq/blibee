package org.jeecg.modules.nethospital.util;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.math.BigDecimal;

/**
 * 推送类
 * Author:木芒果
 */
public class PushUtil {

    public static void main(String[] args) {
        push("123", "测试消息", "22",
                "https://baidu.com");
    }

    /**
     * 消息推送主要业务代码
     */
    public static String push(String price,
                              String prodName,
                              String storage,
                              String address) {
        String appId = "wx40132ce81a0a00fc";
        String secret = "d5bd732c9f4987ddcda38c5c61b1a3a3";
        String templateId = "LHT-e94POc-7Rws2Vgukv1qMTTkHwHYP9Mpe-pcN49s";
        String userId = "oS1EF6UlJyuCZMCECtTxVJOz13Fo";

        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userId)
                .templateId(templateId)
                .build();


        // 配置你的信息
//        templateMessage.addData(new WxMpTemplateData("userName", "王二麻子","#FF1493"));
//        templateMessage.addData(new WxMpTemplateData("prodName", "大乐透视","#173177"));
//        templateMessage.addData(new WxMpTemplateData("score", "223","#EE212D"));

        templateMessage.addData(new WxMpTemplateData("price", price.toString(),"#FF1493"));
        templateMessage.addData(new WxMpTemplateData("prodName", prodName,"#173177"));
        templateMessage.addData(new WxMpTemplateData("storage", storage.toString(),"#EAAA2D"));
        templateMessage.addData(new WxMpTemplateData("address", address,"#EE212D"));

        System.out.println("待发送数据: -> " + templateMessage.toJson());
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            return "推送失败：" + e.getMessage();
        }
        return "推送成功!";
    }


//    /**
//     * 消息推送主要业务代码
//     */
//    public static String push() {
//        //1，配置
//        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
//        wxStorage.setAppId(PushConfigure.getAppId());
//        wxStorage.setSecret(PushConfigure.getSecret());
//        WxMpService wxMpService = new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(wxStorage);
//        // 推送消息
//        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                .toUser(PushConfigure.getUserId())
//                .templateId(PushConfigure.getTemplateId())
//                .build();
//
//
//        // 配置你的信息
//        long loveDays = MemoryDayUtil.calculationLianAi(PushConfigure.getLoveDate());
//        long birthdays = MemoryDayUtil.calculationBirthday(PushConfigure.getBirthday());
//        Weather weather = WeatherUtil.getWeather();
//        if (weather == null) {
//            templateMessage.addData(new WxMpTemplateData("weather", "***", "#00FFFF"));
//        } else {
//            templateMessage.addData(new WxMpTemplateData("date", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
//            templateMessage.addData(new WxMpTemplateData("weather", weather.getText_now(), "#00FFFF"));
//            templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#173177"));
//            templateMessage.addData(new WxMpTemplateData("temp", weather.getTemp() + "", "#EE212D"));
//            templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
//            templateMessage.addData(new WxMpTemplateData("city", weather.getCity() + "", "#173177"));
//        }
//
//        templateMessage.addData(new WxMpTemplateData("loveDays", loveDays + "", "#FF1493"));
//        templateMessage.addData(new WxMpTemplateData("birthdays", birthdays + "", "#FFA500"));
//
//        String remark = "亲爱的乖乖宝贝，早上好!记得要吃早餐哦，今天也要开心哦 =^_^= ";
//        if (loveDays % 365 == 0) {
//            remark = "\n今天是恋爱" + (loveDays / 365) + "周年纪念日!";
//        }
//        if (birthdays == 0) {
//            remark = "\n今天是生日,生日快乐呀!";
//        }
//        if (loveDays % 365 == 0 && birthdays == 0) {
//            remark = "\n今天是生日,也是恋爱" + (loveDays / 365) + "周年纪念日!";
//        }
//
//        templateMessage.addData(new WxMpTemplateData("remark", remark, "#FF1493"));
//        templateMessage.addData(new WxMpTemplateData("rainbow", RainbowUtil.getRainbow(), "#FF69B4"));
//        System.out.println(templateMessage.toJson());
//        try {
//            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
//        } catch (Exception e) {
//            System.out.println("推送失败：" + e.getMessage());
//            return "推送失败：" + e.getMessage();
//        }
//        return "推送成功!";
//    }
}
