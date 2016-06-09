package com.example.rafa.pang2.Util;


public class Reloj extends Thread {

    public int total;
    public int segundos = 0;
    public int minutos =0;

    public Reloj(int total) {
        this.total = total;
    }

    @Override
    public void run() {
        while (total <= 0) {
            minutos = (total/60);
            segundos = (total - (minutos * 60));
            total++;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}