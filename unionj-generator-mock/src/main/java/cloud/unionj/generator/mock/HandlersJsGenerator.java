package cloud.unionj.generator.mock;

import cloud.unionj.generator.ApiItemVo;
import cloud.unionj.generator.GeneratorUtils;
import cloud.unionj.generator.mock.docparser.entity.Api;
import cloud.unionj.generator.mock.docparser.entity.ApiItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author created by wubin
 * @version v0.1
 *   cloud.unionj.generator
 *  date 2020/11/22
 */
public class HandlersJsGenerator extends MockGenerator {

  private Api api;
  private String outputDir = OUTPUT_DIR;

  public HandlersJsGenerator(Api api) {
    this.api = api;
  }

  public HandlersJsGenerator(Api api, String outputDir) {
    this.api = api;
    this.outputDir = outputDir;
  }

  @Override
  public Map<String, Object> getInput() {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("baseUrl", this.api.getBaseUrl());

    List<ApiItemVo> apiItemVoList = this.api.getItems().values().stream().reduce(new ArrayList<>(), (x, y) -> {
      if (y != null) {
        x.addAll(y);
      }
      return x;
    }).stream().map(ApiItem::toApiItemVo).collect(Collectors.toList());

    input.put("apis", apiItemVoList);
    return input;
  }

  @Override
  public String getTemplate() {
    return OUTPUT_DIR + File.separator + "handlers.ts.ftl";
  }

  @Override
  public String getOutputFile() {
    return GeneratorUtils.getOutputDir(this.outputDir) + File.separator + "handlers.ts";
  }

}
