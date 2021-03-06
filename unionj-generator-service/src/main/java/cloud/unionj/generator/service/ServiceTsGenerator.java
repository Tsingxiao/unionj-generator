package cloud.unionj.generator.service;

import cloud.unionj.generator.GeneratorUtils;
import cloud.unionj.generator.service.docparser.entity.BizService;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author created by wubin
 * @version v0.1
 * cloud.unionj.generator
 * date 2020/11/22
 */
public class ServiceTsGenerator extends ServiceGenerator {

  private BizService bizService;
  private String outputDir = OUTPUT_DIR;

  public ServiceTsGenerator(BizService bizService) {
    this.bizService = bizService;
  }

  public ServiceTsGenerator(BizService bizService, String outputDir) {
    this.bizService = bizService;
    this.outputDir = outputDir;
  }

  @Override
  public Map<String, Object> getInput() {
    Map<String, Object> input = new HashMap<>();
    input.put("name", StringUtils.capitalize(this.bizService.getName()));
    input.put("module", StringUtils.capitalize(this.bizService.getModule()));
    input.put("types", this.bizService.getTypes());
    input.put("routers", this.bizService.getRouters());
    return input;
  }

  @Override
  public String getTemplate() {
    return OUTPUT_DIR + "/Service.ts.ftl";
  }

  @Override
  public String getOutputFile() {
    return GeneratorUtils.getOutputDir(this.outputDir) + File.separator + StringUtils.capitalize(this.bizService.getName()) + ".ts";
  }

}
