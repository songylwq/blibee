package org.jeecg.modules.nethospital.util.huanxin;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户mq通讯的用户购买vip数据类
 *
 * @author abc
 * @version 1.0
 * @date 2023/7/29 14:16
 */
@Data
public class BaseMq implements Serializable {
    private String msgId;

    /**用户编号*/
    private Long uid;
}
