import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * Ognl工具类，主要是为了在ognl表达式访问静态方法时可以减少长长的类名称编写 Ognl访问静态方法的表达式为: @class@method(args) 示例使用:
 * 
 * <pre>
 * 	<if test="@Ognl@isNotEmpty(userId)">
 * 	    and user_id = #{userId}
 *  </if>
 * </pre>
 * 
 * @author xl.liu
 */
public class Ognl {

    /**
     * 可以用于判断String,Map,Collection,Array是否为空
     * 
     * @param o
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null)
            return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     * 
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNumber(Object o) {
        if (o == null)
            return false;
        if (o instanceof Number) {
            return true;
        }
        if (o instanceof String) {
            String str = (String) o;
            if (str.length() == 0)
                return false;
            if (str.trim().length() == 0)
                return false;
            return StringUtils.isNumeric(str);
        }
        return false;
    }

    /**
     * 判断某字符串是否不为空且长度不为0且不由空白符(whitespace) 构成，等于 !isBlank(String str)
     * 
     * @param o
     * @return
     */
    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isBlank(Object o) {
        if (o == null)
            return true;
        if (o instanceof String) {
            String str = (String) o;
            return isBlank(str);
        }
        return false;
    }

    /**
     * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个字符串相等
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsTo(String str1, String str2) {
        return StringUtils.equals(str1, str2);
    }
}
