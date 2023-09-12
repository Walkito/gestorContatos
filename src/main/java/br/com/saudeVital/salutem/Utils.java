package br.com.saudeVital.salutem;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static boolean verificaParametros(Object... parametros){
        int cont = 0;
        for(Object parametro : parametros){
            if(parametro == null ||
                    (parametro instanceof String && ((String) parametro).isEmpty()) ||
                    (parametro instanceof Integer && ((Integer) parametro == 0)) ||
                    (parametro instanceof Double && ((Double) parametro == 0.00))
            ){
                cont +=1;
                System.out.println(cont);
            }
        }

        if(cont == parametros.length){
            return true;
        } else {
            return false;
        }
    }

    public static String[] getPropriedadesNulas(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
