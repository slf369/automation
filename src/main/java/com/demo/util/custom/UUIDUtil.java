package com.demo.util.custom;
import java.util.UUID;

/**
 * Created by shi.lingfeng on 2018/3/24.
 */
public class UUIDUtil {
    /**
     * 自动生成32位的UUid，用于生产唯一值
     * @return
     */
    public String getUUID() {
        // 去掉"-"符号
        return UUID.randomUUID().toString().replace("-", "");
    }
}
