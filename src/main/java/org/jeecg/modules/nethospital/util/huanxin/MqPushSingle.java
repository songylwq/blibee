package org.jeecg.modules.nethospital.util.huanxin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 推送--根据用户 imid 推送
 *
 * @author abc
 * @version 1.0
 * @date 2023/7/29 14:16
 */
@Data
public class MqPushSingle extends BaseMq implements Serializable {

    //用户 imid
    private List<String> targets;

    //推送的消息
    private Map<String, Object> pushMessage;

    //推送策略
    private Integer strategy;
}
