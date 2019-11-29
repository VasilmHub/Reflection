import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class reflection = Reflection.class;
        Method[] declaredMethods = reflection.getDeclaredMethods();
        List<Method> setters = new ArrayList<>();
        List<Method> getters = new ArrayList<>();
        List<Field> fields=new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            int modifiers = declaredMethod.getModifiers();
            if(isItGetter(declaredMethod)){
                if(!Modifier.isPublic(modifiers)) {
                    getters.add(declaredMethod);
                }
            }else if(isItSetter(declaredMethod)){
                if(!Modifier.isPrivate(modifiers)) {
                    setters.add(declaredMethod);
                }
            }
        }
        Field[] declaredFields = reflection.getDeclaredFields();
        for (Field field : declaredFields) {
            if(!Modifier.isPrivate(field.getModifiers())){
                fields.add(field);
            }
        }

        print(getters, setters,fields);
    }

    private static boolean isItSetter(Method declaredMethod) {
        if (retTypeVoid(declaredMethod) && startWith("set", declaredMethod)) {
           return true;
        }
        return false;
    }

    private static boolean isItGetter(Method declaredMethod) {
        if ((!retTypeVoid(declaredMethod)) && startWith("get", declaredMethod)) {
            return true;
        }
        return false;
    }

    private static void print(List<Method> getters, List<Method> setters, List<Field> fields) {
        fields.stream().sorted((a,b)->a.getName().compareTo(b.getName())).forEach(a-> System.out.println(a.getName()+" must be private!"));
        getters.stream().sorted((a,b)-> a.getName().compareTo(b.getName())).forEach(declaredMethod -> System.out.println(declaredMethod.getName() + " have to be public!" ));
        setters.stream().sorted((a,b)-> a.getName().compareTo(b.getName())).forEach(declaredMethod -> System.out.println(declaredMethod.getName() + " have to be private!"));
    }

    private static boolean retTypeVoid(Method declaredMethod) {
        if (declaredMethod.getReturnType().equals(Void.TYPE)) {
            return true;
        }
        return false;
    }

    private static boolean startWith(String start, Method declaredMethod) {
        if (!declaredMethod.getName().startsWith(start)) {
            return false;
        }
        return true;
    }
}
