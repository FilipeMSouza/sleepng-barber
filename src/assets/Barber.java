package assets;

public class Barber {

    public void cutHair() {
        System.out.println("barbeiro ocupado: Cortando cabelo do cliente  " + Thread.currentThread().getName());
    }
}

