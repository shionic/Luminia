package luminia.backend.utils;

import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JpaUtils {
    public static <T, D>  List<D> mapIfInitialized(List<T> list, Function<T, D> f, boolean forceFetch) {
        List<D> r;
        if(/*Hibernate.isInitialized(list) ||*/forceFetch) {
            r = new ArrayList<>(list.size());
            for(var a : list) {
                r.add(f.apply(a));
            }
        } else {
            r = null;
        }
        return r;
    }
}
