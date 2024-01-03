package org.jeecg.modules.nethospital;

import org.jeecg.modules.nethospital.api.GetDataBlibee;
import org.jeecg.modules.nethospital.api.GetDataJianHang1Yuan;
import org.jeecg.modules.nethospital.api.GetDataJianHang1YuanHuanXin;
import org.jeecg.modules.nethospital.api.GetDataXiaomi5;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author abc
 * @version 1.0
 * @date 23/02/09 下午 3:00
 */
public class StartCore {
    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<String>(){{
            add("京旺店-207000013,207000013");

        }};


        for (int i = 0; i < list.size(); i++) {
            String[] item = list.get(i).split(",");
            //便利蜂
            System.out.println("20秒后开始启动 便利蜂...");
            Thread.sleep(1_000);
            new Thread(new GetDataBlibee(item[0],item[1])).start();
            System.out.println("启动完成 便利蜂...");
        }

    }
}
