import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import assets.*;

public class Barbershop {

    private final int limit;
    public int customers;
    private final Barber barber;

    private final Semaphore customerReady;
    private final Semaphore barberReady;

    private final Semaphore customerDone;
    private final Semaphore barberDone;

    private final ReentrantLock mutex;

    public Barbershop(int limit) {
        this.limit = limit;
        customers = 0;

        customerReady = new Semaphore(0);
        barberReady = new Semaphore(0);

        customerDone = new Semaphore(0);
        barberDone = new Semaphore(0);

        mutex = new ReentrantLock();

        barber = new Barber();
    }

    public Void startService() {

        // wait for a customer to arrive
        try {
            customerReady.acquire();
        } catch (InterruptedException e) {
            System.out.println("Barbeiro interrompido " + e.getMessage());
        }

        // signal that barber is ready
        barberReady.release();

        // give a haircut
        barber.cutHair();


        // wait for a customer to approve done
        try {
            customerDone.acquire();
        } catch (InterruptedException e) {
            System.out.println("Barbeiro interrompido " + e.getMessage());
        }
        // signal the customer that barber is done
        barberDone.release();
        System.out.println("Corte Terminado");
        return null;
    }

    public Void receiveNewCustomer() {

        Customer customer = new Customer();

        customer.enter();

        mutex.lock();
        if (customers == limit) {
            mutex.unlock();
            customer.leave();
            return null;
        }
        customers += 1;
        mutex.unlock();

        customerReady.release();

        // wait for the barber to be available
        try {
            barberReady.acquire();
        } catch (InterruptedException e) {
            System.out.println("Cleinte em espera " + e.getMessage());
        }

        // get the haircut
        customer.getHairCut();

        // signal that customer is done
        customerDone.release();

        // wait for barber to approve done
        try {
            barberDone.acquire();
        } catch (InterruptedException e) {
            System.out.println("Cleinte em espera " + e.getMessage());
        }

        mutex.lock();
        customers -= 1;
        mutex.unlock();

        return null;
    }

}