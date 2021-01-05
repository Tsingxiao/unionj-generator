package cloud.unionj.generator.service.apicloud.config;/**
 * @author dingxu
 * @version 1.0
 * date: 2021/1/5 09:36
 */

import cloud.unionj.generator.service.apicloud.utils.PropertiesToObject;
import java.io.File;
import java.io.InputStream;

/**
 * @version v0.1 cloud.unionj.generator
 * @author: created by Johnny Ting
 * @description: description
 * @date: 2021-01-05 09:36
 **/
public class ConfigLoad {

    public static <T> T load(String path, Class<T> clazz){
        return PropertiesToObject.parse(path, clazz);
    }

    public static <T> T load(InputStream is, Class<T> clazz){
        return PropertiesToObject.parse(is, clazz);
    }

    public static <T> T load(File file, Class<T> clazz){
        return PropertiesToObject.parse(file, clazz);
    }
}
