package com.tsingxiao.unionj.generator.mock.schemafaker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import com.tsingxiao.unionj.generator.mock.schemafaker.propertyfaker.Faker;
import com.tsingxiao.unionj.generator.mock.schemafaker.propertyfaker.FakerNotFoundException;
import com.tsingxiao.unionj.generator.mock.schemafaker.propertyfaker.FormatConstants;
import com.tsingxiao.unionj.generator.mock.schemafaker.propertyfaker.PropertyFaker;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.util.SchemaTypeUtil;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Map;

/**
 * @author: created by wubin
 * @version: v0.1
 * @description: com.tsingxiao.unionj.generator.mock.schemafaker
 * @date:2020/11/19
 */
@Data
public class DefaultSchemaFaker implements SchemaFaker {

  private com.github.javafaker.Faker faker = new com.github.javafaker.Faker(Locale.SIMPLIFIED_CHINESE);
  private ObjectMapper objectMapper = new ObjectMapper();
  private Map<String, Schema> schemas;


  public DefaultSchemaFaker(com.github.javafaker.Faker faker) {
    this.faker = faker;
  }

  public DefaultSchemaFaker() {
  }

  @SneakyThrows
  @Override
  public JsonNode fakeFormat(String format) {
    FormatConstants formatConstants = FormatConstants.fromFormat(format);
    Faker faker = formatConstants.getClass().getField(formatConstants.name()).getAnnotation(Faker.class);
    if (faker == null) {
      throw new FakerNotFoundException(format + " has no faker found");
    }
    Object concreteFaker = faker.value().getConstructor(com.github.javafaker.Faker.class).newInstance(this.faker);
    PropertyFaker propertyFaker = (PropertyFaker) concreteFaker;
    return propertyFaker.fake();
  }

  @Override
  public JsonNode fakePrimitiveType(String type) {
    JsonNode jsonNode = null;
    switch (type) {
      case SchemaTypeUtil.BOOLEAN_TYPE: {
        jsonNode = BooleanNode.valueOf(this.faker.bool().bool());
        break;
      }
      case SchemaTypeUtil.INTEGER_TYPE: {
        jsonNode = IntNode.valueOf(this.faker.random().nextInt(0, 10000));
        break;
      }
      case SchemaTypeUtil.NUMBER_TYPE: {
        jsonNode = DoubleNode.valueOf(this.faker.random().nextDouble());
        break;
      }
      default: {
        jsonNode = TextNode.valueOf(this.faker.lorem().sentence());
        break;
      }
    }
    return jsonNode;
  }

  private JsonNode convertSchema2JsonNode(Schema value) {
    JsonNode result = null;
    String format = value.getFormat();
    if (StringUtils.isNotBlank(format)) {
      result = this.fakeFormat(format);
    } else {
      if (StringUtils.isNotBlank(value.get$ref())) {
        result = this.convertSchema2JsonNode(this.getSchemaByRef(value.get$ref()));
      } else {
        String type = value.getType();
        if (StringUtils.isNotBlank(type)) {
          if (type.equals("array")) {
            ArraySchema arraySchema = (ArraySchema) value;
            Schema<?> items = arraySchema.getItems();
            ArrayNode arrayNode = this.objectMapper.createArrayNode();
            for (int i = 0; i < 15; i++) {
              arrayNode.add(this.convertSchema2JsonNode(items));
            }
            result = arrayNode;
          } else if (type.equals(SchemaTypeUtil.OBJECT_TYPE)) {
            ObjectNode rootObjectNode = this.objectMapper.createObjectNode();
            Map<String, Schema> properties = value.getProperties();
            if (properties != null) {
              properties.forEach((k, v) -> {
                rootObjectNode.set(k, this.convertSchema2JsonNode(v));
              });
            }
            result = rootObjectNode;
          } else {
            result = this.fakePrimitiveType(type);
          }
        }
      }
    }
    return result;
  }

  @Override
  public JsonNode fakeObject(Schema schema) {
    return this.convertSchema2JsonNode(schema);
  }

  @SneakyThrows
  @Override
  public Schema getSchemaByRef(String ref) {
    if (MapUtils.isEmpty(this.schemas)) {
      throw new SchemasNullException("schemas is empty or null");
    }
    ref = ref.substring(ref.lastIndexOf("/") + 1);
    return this.schemas.get(ref);
  }


}