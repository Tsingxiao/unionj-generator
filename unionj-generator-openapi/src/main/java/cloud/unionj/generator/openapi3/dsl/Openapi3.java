package cloud.unionj.generator.openapi3.dsl;

import cloud.unionj.generator.openapi3.expression.Openapi3Builder;

import java.util.function.Consumer;

/**
 * @author created by wubin
 * @version v0.1
 * cloud.unionj.generator.openapi3.dsl
 * date 2020/12/15
 */
public class Openapi3 {

  protected static Openapi3Builder openapi3Builder;

  public static cloud.unionj.generator.openapi3.model.Openapi3 openapi3(Consumer<Openapi3Builder> consumer) {
    if (openapi3Builder == null) {
      openapi3Builder = new Openapi3Builder();
    }
    consumer.accept(openapi3Builder);
    return openapi3Builder.build();
  }
}
