# Reflection
In this example I print all getters and setters
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
___________________________________________________

import java.io.Serializable;

public class Reflection implements Serializable {

    private static final String nickName = "Pinguin";
    public String name;
    protected String webAddress;
    String email;
    private int zip;

    public Reflection() {
        this.setName("Java");
        this.setWebAddress("oracle.com");
        this.setEmail("mail@oracle.com");
        this.setZip(1407);
    }

    private Reflection(String name, String webAddress, String email) {
        this.setName(name);
        this.setWebAddress(webAddress);
        this.setEmail(email);
        this.setZip(2300);
    }

    protected Reflection(String name, String webAddress, String email, int zip) {
        this.setName(name);
        this.setWebAddress(webAddress);
        this.setEmail(email);
        this.setZip(2300);
    }

    public final String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    protected String getWebAddress() {
        return webAddress;
    }

    private void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected final int getZip() {
        return zip;
    }

    private void setZip(int zip) {
        this.zip = zip;
    }

    public String toString() {
        String result = "Name: " + getName() + "\n";
        result += "WebAddress: " + getWebAddress() + "\n";
        result += "email: " + getEmail() + "\n";
        result += "zip: " + getZip() + "\n";
        return result;
    }
}
_______________________________
socond main
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

