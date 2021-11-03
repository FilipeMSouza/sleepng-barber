package assets;

public class Customer {
    public void enter() {

        System.out.println("Cliente entra na barbearia" + Thread.currentThread().getName());
    }

    public void getHairCut() {
        System.out.println("Cliente cortando o cabelo " + Thread.currentThread().getName());
    }

    public void leave() {

        System.out.println("Cliente deixa a barbearia. \nMutio obirgado, Volte sempre!" + Thread.currentThread().getName());
    }
}
