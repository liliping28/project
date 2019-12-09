package springboot.until;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: PlaceholderUtils
 * @category: TODO
 * @Description: 配置文件或模板中的占位符替换工具类
 * @author: zhoulong
 * @date: 2018年8月4日 下午2:59:51
 */
public class PlaceholderUtils {

    /**
     * Prefix for system property placeholders: "${"
     */
    public static final String PLACEHOLDER_PREFIX = "${";
    /**
     * Suffix for system property placeholders: "}"
     */
    public static final String PLACEHOLDER_SUFFIX = "}";

    public static String resolvePlaceholder(String text, Map<String, String> parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return text;
        }
        StringBuffer buf = new StringBuffer(text);
        int startIndex = buf.indexOf(PLACEHOLDER_PREFIX);
        while (startIndex != -1) {
            int endIndex = buf.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
            if (endIndex != -1) {
                String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();
                try {
                    String propVal = parameter.get(placeholder);
                    if (propVal != null) {
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                        nextIndex = startIndex + propVal.length();
                    } else {

                    }
                } catch (Exception ex) {

                }
                startIndex = buf.indexOf(PLACEHOLDER_PREFIX, nextIndex);
            } else {
                startIndex = -1;
            }
        }
        return buf.toString();
    }
    public static String resolvePlaceholders(String text, List<Map<String, String>> parameters) {
        String result = null;
        for(Map<String, String> parameter:parameters){
            result = PlaceholderUtils.resolvePlaceholder(text, parameter);
        }
        return result;
    }

    public static String getPlaceholders(String str) {
        if(StringUtils.isNotBlank(str)){
            int startIndex = str.indexOf(PLACEHOLDER_PREFIX);
            int endIndex = str.indexOf(PLACEHOLDER_SUFFIX);
            if (startIndex != -1 && endIndex != -1) {
                str = str.substring(startIndex+2,endIndex);
                return str;
            }
        }
        return null;
    }




}
