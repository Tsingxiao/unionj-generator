package cloud.unionj.generator.openapi3.dsl.paths;

import cloud.unionj.generator.openapi3.expression.paths.ResponsesBuilder;

import java.util.function.Consumer;

/**
 * @author created by wubin
 * @version v0.1
 * cloud.unionj.generator.openapi3.dsl.paths
 * date 2020/12/19
 */
public class Responses extends Operation {

  public static void responses(Consumer<ResponsesBuilder> consumer) {
    ResponsesBuilder responsesBuilder = new ResponsesBuilder();
    consumer.accept(responsesBuilder);
    cloud.unionj.generator.openapi3.model.paths.Responses responses = responsesBuilder.build();
    operationBuilder.responses(responses);
  }
}
