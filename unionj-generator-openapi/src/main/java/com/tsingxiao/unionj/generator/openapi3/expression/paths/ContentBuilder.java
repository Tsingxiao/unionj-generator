package com.tsingxiao.unionj.generator.openapi3.expression.paths;

import com.tsingxiao.unionj.generator.openapi3.model.paths.Content;
import com.tsingxiao.unionj.generator.openapi3.model.paths.MediaType;

/**
 * @author: created by wubin
 * @version: v0.1
 * @description: com.tsingxiao.unionj.generator.openapi3.expression
 * @date:2020/12/16
 */
public class ContentBuilder {

  private Content content;

  public ContentBuilder() {
    this.content = new Content();
  }

  public void applicationJson(MediaType applicationJson) {
    this.content.setApplicationJson(applicationJson);
  }

  public void applicationXWwwFormUrlencoded(MediaType applicationXWwwFormUrlencoded) {
    this.content.setApplicationXWwwFormUrlencoded(applicationXWwwFormUrlencoded);
  }

  public Content build() {
    return this.content;
  }
}