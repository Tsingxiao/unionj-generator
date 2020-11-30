package com.tsingxiao.unionj.generator.mock.docparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsingxiao.unionj.generator.mock.docparser.entity.Api;
import com.tsingxiao.unionj.generator.mock.docparser.entity.ApiItem;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author: created by wubin
 * @version: v0.1
 * @description: com.tsingxiao.unionj.generator.mock.docparser
 * @date:2020/11/18
 */
public class MockDocParserTest {

  @SneakyThrows
  @Test
  public void parse() {
    String testFilePath = MockDocParserTest.class.getClassLoader().getResource("petstore3.json").getPath();
    MockDocParser docParser = new MockDocParser(testFilePath);
    Api api = docParser.parse();
    Assert.assertNotNull(api);
    ApiItem apiItem = api.getItems().get("任务大厅").stream().filter(item -> item.getEndpoint().equals("/hall/latestOrder")).findAny().get();
    Assert.assertFalse(apiItem.getResponse().isEmpty());
    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(apiItem));
  }
}
