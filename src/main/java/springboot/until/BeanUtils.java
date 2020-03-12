package springboot.until;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName: BeanUtils
 * @Description: dto转成entity工具
 * @author: lilp
 * @date: 2019/08/28 10:17:26
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 拷呗集合
     * @param sourceList 源集合
     * @param clzz 目标Class
     * @param <E> 输入源泛型
     * @param <T> 输出目标泛型
     * @return
     */
    public static <E,T> List<T> copyList(List<E>  sourceList, Class<T> clzz) {
        List<T> targetList = new CopyOnWriteArrayList<>();
        if (!CollectionUtils.isEmpty(sourceList)) {
            sourceList.forEach(e -> {
                try {
                    T target = clzz.newInstance();
                    BeanUtils.copyProperties(e,target);
                    targetList.add(target);
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            });
        }
        return targetList;
    }
    /**
     * 拷呗集合(需要强转的)
     * @param sourceList 源集合
     * @param clzz 目标Class
     * @return
     */
    public static <T> List<? extends T> copyPropertiesList(List<?> sourceList, Class<T> clzz) {
        List<T> result = new LinkedList<>();
        if (!CollectionUtils.isEmpty(sourceList)) {
            sourceList.forEach(e -> {
                T target = (T)BeanUtils.copy(e.getClass(),clzz,e);
                result.add(target);
            });
        }
        return result;
    }
    /**
     *  拷贝对象(性能更高)
     *
     * @param source 源Class
     * @param target 目标Class
     * @param sourceBean 源对象
     * @return 目标实体
     */
    public static Object copy(Class<?> source, Class<?> target , Object sourceBean){
        //动态代理方向拷呗属性效率相对于spring和apache的反射高出很多
        BeanCopier beanRelationCopier = BeanCopier.create(source, target, false);
        Object t = null;
        try {
            t =  target.newInstance();
            beanRelationCopier.copy(sourceBean, t, null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
