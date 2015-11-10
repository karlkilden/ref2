package com.kildeen.gv.poll;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.deltaspike.core.util.ExceptionUtils;

import com.kildeen.gv.DomainEntity;

public class BaseEntityUtil {

    public static void setId(Long id, Object entity) {
        try {
            Field f = ReflectionUtils.findField("id", entity.getClass());
            f.setAccessible(true);
            f.set(entity, id);
        } catch (Exception e) {
            ExceptionUtils.throwAsRuntimeException(e);
        }
    }

    public static void setId(Object entity) {
    	setId(RandomUtils.nextLong(1, 1000000), entity);
    }

    public static void setIds(List<Long> ids, List<Object> entities) {
    	for (int i = 0; i < ids.size(); i++) {
			setId(ids.get(i), entities.get(i));
		}
    }
    public static void setIds(List<? extends DomainEntity> entities) {
    	for (int i = 0; i < entities.size(); i++) {
    		setId(entities.get(i));
    	}
    }

    public static Object getId(Object entity) throws IllegalAccessException {
        Object id = null;
        Field f = ReflectionUtils.findField("id", entity.getClass());
        if (f != null) {
            id = f.get(entity);
            if (id == null) {
                return null;
            }
        }
        return id;
    }
}
