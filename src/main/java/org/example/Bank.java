package org.example;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts;
    private Lock lock = new ReentrantLock();

    public Bank(int n, int initialBalance){
        accounts = new int[n];
        Arrays.fill(accounts, initialBalance);
        ntransacts = 0;
    }

    public void transfer(int from, int to, int amount) {
        lock.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }
        finally {
            lock.unlock();
        }
    }

    public void test(){
        lock.lock();
        try {
            int sum = 0;
            for (int account : accounts) sum += account;
            System.out.println("Transactions:" + ntransacts
                    + " Sum: " + sum);
        }
        finally {
            lock.unlock();
        }
    }

    public int size(){
        return accounts.length;
    }
}
