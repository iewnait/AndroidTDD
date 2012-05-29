import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

/**
 * Provides access to private members in classes.
 */
public class PrivateAccessor {

    public static Object getPrivateField(Object instanceOfClass, String fieldName) {
        // Check we have valid arguments...
        Assert.assertNotNull(instanceOfClass);
        Assert.assertNotNull(fieldName);

        // Find private field.
        final Field fields[] = instanceOfClass.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if (fieldName.equals(fields[i].getName())) {
                try {
                    fields[i].setAccessible(true);
                    return fields[i].get(instanceOfClass);
                } catch (IllegalAccessException ex) {
                    Assert.fail("IllegalAccessException accessing "
                            + new Object[0]);
                }
            }
        }
        Assert.fail("Field '" + fieldName + "' not found");
        return null;
    }

    public static void setPrivateField(Object instanceOfClass, String fieldName, Object value) {
        // Check we have valid arguments...
        Assert.assertNotNull(instanceOfClass);
        Assert.assertNotNull(fieldName);
        Assert.assertNotNull(value);

        // Find private field.
        final Field fields[] = instanceOfClass.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if (fieldName.equals(fields[i].getName())) {
                try {
                    // Set private field with value provided.
                    fields[i].setAccessible(true);
                    fields[i].set(instanceOfClass, value);
                    return;
                } catch (IllegalAccessException ex) {
                    Assert.fail("IllegalAccessException accessing "
                            + new Object[0]);
                }
            }
        }
        Assert.fail("Field '" + fieldName + "' not found");
    }

    public static Object invokePrivateMethod(Object instanceOfClass, String methodName,
            Object[] params) {

        // Check for invalid arguments.
        Assert.assertNotNull(instanceOfClass);
        Assert.assertNotNull(methodName);

        // Find private method.
        final Method methods[] = instanceOfClass.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (methodName.equals(methods[i].getName())) {
                try {
                    methods[i].setAccessible(true);
                    // invoke private method with provided params.
                    if (params == null) {
                        return methods[i].invoke(instanceOfClass);
                    } else {
                        return methods[i].invoke(instanceOfClass, params);
                    }
                } catch (IllegalAccessException ex) {
                    Assert.fail("IllegalAccessException accessing "
                            + methodName);
                } catch (InvocationTargetException ite) {
                    Assert.fail("InvocationTargetException accessing "
                            + methodName);
                }
            }
        }
        Assert.fail("Method '" + methodName + "' not found");
        return null;
    }
}
