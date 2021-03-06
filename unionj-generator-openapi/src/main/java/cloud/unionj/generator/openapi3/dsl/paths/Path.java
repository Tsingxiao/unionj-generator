package cloud.unionj.generator.openapi3.dsl.paths;

import cloud.unionj.generator.openapi3.dsl.Openapi3;
import cloud.unionj.generator.openapi3.expression.paths.PathBuilder;

import java.util.function.Consumer;

/**
 * @author created by wubin
 * @version v0.1
 *   cloud.unionj.generator.openapi3.dsl
 *  date 2020/12/14
 */
public class Path extends Openapi3 {

  protected static PathBuilder pathBuilder;

  public static void path(String endpoint, Consumer<PathBuilder> consumer) {
    pathBuilder = new PathBuilder();
    consumer.accept(pathBuilder);
    pathBuilder.endpoint(endpoint);
    cloud.unionj.generator.openapi3.model.paths.Path path = pathBuilder.build();
    openapi3Builder.paths(path);
  }

}
