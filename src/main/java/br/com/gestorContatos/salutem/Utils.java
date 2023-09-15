package br.com.gestorContatos.salutem;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean verificaParametros(Object... parametros){
        int cont = 0;
        for(Object parametro : parametros){
            if(parametro == null ||
                    (parametro instanceof String && ((String) parametro).isEmpty()) ||
                    (parametro instanceof Integer && ((Integer) parametro == 0)) ||
                    (parametro instanceof Double && ((Double) parametro == 0.00)) ||
                    (parametro instanceof Long && ((Long) parametro == 0L))
            ){
                cont +=1;
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

    public static boolean verificarCPF(String cpf){
        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> errosCPF = cpfValidator.invalidMessagesFor(cpf);
        if(errosCPF.size() > 0){
            return false;
        } else {
            return true;
        }
    }

    public static <T> Iterable<T> transformaOptional(Optional<T> optional){
        try {
            return optional.map(Collections::singletonList).orElse(Collections.emptyList());
        } catch (Exception e){
            throw e;
        }
    }

    public static Pageable montarPagina(int numPagina, int tamanhoPagina){
        numPagina = numPagina == 0 ? 0 : numPagina;
        tamanhoPagina = tamanhoPagina == 0 ? 10 : tamanhoPagina;
        return PageRequest.of(numPagina, tamanhoPagina, Sort.by(Sort.Order.asc("id")));
    }

    public static boolean validarEmail(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
