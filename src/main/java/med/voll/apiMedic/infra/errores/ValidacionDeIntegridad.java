package med.voll.apiMedic.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {         // La clase Throwable responde ante errores y excepciones,
                                                                // y RuntimeException solo responde ante excepcion
    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
