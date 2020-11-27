package com.tsingxiao.unionj.generator.client.vue;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author: created by wubin
 * @version: v0.1
 * @description: com.tsingxiao.unionj.generator.client.vue
 * @date:2020/11/26
 */
public class VueProjectGeneratorTest {

  @Test
  public void generate() {
    VueProjectGenerator vueProjectGenerator = new VueProjectGenerator("测试项目");
    String outputFile = vueProjectGenerator.generate();
    File file = new File(outputFile);
    Assert.assertTrue(file.exists());
  }
}