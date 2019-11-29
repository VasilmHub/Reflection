import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class reflection = Reflection.class;
        Method[] declaredMethods = reflection.getDeclaredMethods();
        List<Method> setters = new ArrayList<>();
        List<Method> getters = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if ((!retTypeVoid(declaredMethod)) && startWith("get", declaredMethod)) {
                getters.add(declaredMethod);

            } else if (retTypeVoid(declaredMethod) && startWith("set", declaredMethod)) {
                setters.add(declaredMethod);

            }
        }

        print(getters, setters);
    }

    private static void print(List<Method> getters, List<Method> setters) {
        getters.stream().sorted((a,b)-> a.getName().compareTo(b.getName())).forEach(declaredMethod -> System.out.println(declaredMethod.getName() + " will return class " + declaredMethod.getReturnType().getName()));
        setters.stream().sorted((a,b)-> a.getName().compareTo(b.getName())).forEach(declaredMethod -> System.out.println(declaredMethod.getName() + " and will set field of class " + declaredMethod.getParameterTypes()[0].getName()));
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
