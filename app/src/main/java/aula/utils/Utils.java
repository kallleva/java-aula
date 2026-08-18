package aula.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    static public String generateId() {
        return java.util.UUID.randomUUID().toString();
    }

    static NumberFormat formatandoNumeros = new DecimalFormat("#,##0.00");
    static SimpleDateFormat formatandoData = new SimpleDateFormat("dd/MM/yyyy");

    static public String formatarData(Date data) {
        return formatandoData.format(data);
    }

    static public String formatarNumero(double numero) {
        return formatandoNumeros.format(numero);
    }

}