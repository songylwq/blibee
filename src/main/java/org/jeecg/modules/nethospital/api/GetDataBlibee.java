package org.jeecg.modules.nethospital.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.Data;
import org.jeecg.modules.nethospital.util.PushUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO 便利蜂产品列表获取数据
 *
 * @author abc
 * @version 1.0
 * @date 23/01/17 上午 9:34
 */
public class GetDataBlibee implements Runnable{
    //http://192.168.10.208:10002/uuzz/tmp/bp/prod.html
//    final String filePathAndName = "/prod.html";
//
//    //三庆店
//    String shopName = "三庆店-107000053";
//    String shopCode = "107000053";

//    //博晶店
//    String shopName = "博晶店-107000128";
//    String shopCode = "107000128";

//    //舜泰广场6号楼店
//    String shopName = "舜泰广场6号楼店-107000001";
//    String shopCode = "107000001";

    //汉峪金谷3区店
//    String shopName = "汉峪金谷3区店-107000180";
//    String shopCode = "107000180";

    String shopName;
    String shopCode;

//    public GetDataBlibee(){
//    }

    public GetDataBlibee(String shopName, String shopCode){
        this.shopName = shopName;
        this.shopCode = shopCode;

        init();
    }


    String filePathAndName;

    String HTML_STR;

    String PRODUCT_ITEM;

    private void init(){
        filePathAndName = "E:\\web\\blibee\\prod-"+shopName+".html";

        HTML_STR = "<html>" +
                "<title>"+shopName+"</title>" +
                "<meta charset=\"UTF-8\">" +
                "</head>" +
                "<style>" +
                "*{margin:0; padding:0px;}" +
                "body{width:450px;} " +
                "ul,li{ list-style:none;}" +
                ".left_n{ width:221px; height:340px; float:left;}" +
                ".left_n ul{border:1px solid #CCC;}" +
                ".left_n ul li{line-height:40px; padding-left:30px; background:url(jk.png) no-repeat 5px center; border-bottom:1px solid #eee;}" +
                ".left_n ul li:hover{background:#eee;}" +
                ".left_n_img{ width:150px; height:150px;}" +
                "</style>" +
                "<body>" +
                shopName + "-- nowTime"+
                "itemsHtml" +
                ""+
                "</body>"+
                "</html>";

        PRODUCT_ITEM = "<div>" +
                " <div class=\"left_n\">" +
                "  <ul>" +
                "    <li>" +
                "     <img src=\"headImageUrl\" " +
                "    class=\"left_n_img\" />" +
                "    </li>" +
                "    <li>productName</li>" +
                "    <li><span style=\"color:red;font-weight:bold;\">price元</span> " +
                "/ <del>originPrice</del>元-promotionSourceAmount折</li>" +
                "    <li>规格: netVol/unitName promotionTag</li>" +
                "  </ul>" +
                " </div>" +
                "</div>";
    }






    
    public static void main(String[] args) {
//        System.out.println(sendHttpPostJosn(2));
//    //博晶店
        String shopName = "博晶店-107000128";
        String shopCode = "107000128";
        new Thread(new GetDataBlibee(shopName, shopCode)).start();

    }






    public String sendHttpPostJosn(Integer pageNo) {
        HttpRequest request = HttpRequest.post(FIND_URL);
        request.contentType("application/json");
        request.charset("utf-8");
//        request.header("Referer", "");


        JSONObject param = new JSONObject();
        param.put("categoryUserCodes", new JSONArray());
        param.put("shopCode", shopCode);
        JSONObject page = new JSONObject();
        page.put("pageSize", 100);
        page.put("pageNo", pageNo);
        param.put("page", page);
        request.body(param.toJSONString());
        HttpResponse response = request.send();
        return response.bodyText();
    }



    public String FIND_URL = "https://api.blibee.com/product-api/product/search/promotion/list/v3";

    @Override
    public void run() {
        try {
            do {
                //判断时间6-20点，每个小时更新一次
                String hourTime = new SimpleDateFormat("HHmm").format(new Date());
                if(!("0600".compareTo(hourTime) < 0 && "2200".compareTo(hourTime) > 0)){
                    System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-还没到开始时间");
                    if("0500".compareTo(hourTime) < 0){
                        Thread.sleep(1200_000);
                    } if("0550".compareTo(hourTime) < 0){
                        Thread.sleep(60_000);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-还没到开始时间6-20,运行中...");
                            Thread.sleep(3600_000);
                        }
                    }
                    continue;
                }

                try {
                    //执行任务
                    work();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //执行完成后休眠
                System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-执行完成后休眠,运行中...");
                Thread.sleep(3600_000);
            }while (true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public void work() throws Exception {
        String finalHtml = "";
        int totalPage = 1;

        List<BiliProduct> tempProdList = new ArrayList<>();

        System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-开始查询数据列表");

        for (int page = 1; page <= totalPage; page++) {
            Thread.sleep(2_000);
            System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-开始查询数据页数["+page+"/"+totalPage+"]列表");
            String respStr = sendHttpPostJosn(page);

            JSONObject respObj = JSON.parseObject(respStr);
            //判断获取数据状态
            if(respObj.getInteger("status") != 0){
                System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-获取数据失败");
                return;
            }

            //读取数据总页数
            JSONObject prodObj = respObj.getJSONObject("data").getJSONArray("items").getJSONObject(0);
            totalPage = prodObj.getJSONObject("products").getJSONObject("page").getInteger("totalPage");

            //分析数据获取产品列表
            JSONArray prodJsonList = prodObj.getJSONObject("products").getJSONArray("data");

            //遍历产品列表
            for (int i = 0; i < prodJsonList.size(); i++) {
                BiliProduct prod = prodJsonList.getJSONObject(i).toJavaObject(BiliProduct.class);
                //判断折扣率 只收取折扣率小于5折的
                if(new BigDecimal("9.1").compareTo(prod.promotionSourceAmount) < 0){
                    continue;
                }

                tempProdList.add(prod);
            }
        }

        System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-查询产品数据列表完成，" +
                "符合条件的共"+tempProdList.size()+"条");


        //所有产品排序
        List<BiliProduct> prodList = tempProdList.stream().sorted(Comparator.comparing(BiliProduct::getPromotionSourceAmount)).collect(Collectors.toList());

        StringBuffer itemsHtmlStr = new StringBuffer();
        for (BiliProduct bp : prodList) {
            itemsHtmlStr.append(
                    PRODUCT_ITEM
                            .replaceAll("productName", bp.productName)
                            .replaceAll("unitName", bp.unitName)
                            .replaceAll("netVol", bp.netVol)
                            .replaceAll("headImageUrl", bp.headImageUrl)
                            .replaceAll("price", bp.price.toString())
                            .replaceAll("originPrice", bp.originPrice.toString())
                            .replaceAll("promotionTag", bp.promotionTag)
                            .replaceAll("promotionSourceAmount", bp.promotionSourceAmount.toString())
            );
        }


        //全部html代码填充
        finalHtml = HTML_STR.replaceAll("itemsHtml", itemsHtmlStr.toString());
        //填充当前系统时间
        finalHtml = finalHtml.replaceAll("nowTime",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

//        System.out.println(finalHtml);

        //内容写入文件
        try {
            File file = new File(filePathAndName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(filePathAndName, false);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(finalHtml);
            osw.flush();
            osw.close();

            System.out.println(new SimpleDateFormat().format(new Date())+" -汪蜂-数据写入完成，生成html完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    @Data
    public static class BiliProduct{
       public String productName;
       public String unitName;
       public String netVol;
       public String headImageUrl;
       public BigDecimal price = new BigDecimal("0");
       public BigDecimal originPrice = new BigDecimal("0");
       public String promotionTag;
       public BigDecimal promotionSourceAmount = new BigDecimal("0");
    }

}

