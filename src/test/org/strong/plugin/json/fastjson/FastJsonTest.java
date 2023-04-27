package org.strong.plugin.json.fastjson;

import cn.hutool.core.collection.ListUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strong.plugin.json.entity.User;

import java.util.List;

class FastJsonTest {
    private final Logger logger = LoggerFactory.getLogger(FastJsonTest.class);

    @Test
    void toJsonString() throws JsonProcessingException {
        User contact = new User();
        contact.setPhone("12345678901");
        contact.setName("Glendon");
        contact.setEmail("Glendon@163.com");
        List<User> result = ListUtil.toList(contact);
        JsonMapper jsonMapper = new JsonMapper();
        logger.info("Jackson message:{}", jsonMapper.writeValueAsString(result));
        // 使用fastjson输出信息
        logger.info("Fastjson message:{}", FastJsonDesensitization.toJsonString(result));
    }
}