package com.mycompany.sistemmanajemendatamenurestoran;

import controller.RestoranController;
import view.RestoranView;

public class SistemManajemenDataMenuRestoran {

    public static void main(String[] args) {

        RestoranView view = new RestoranView();

        RestoranController controller =
                new RestoranController(view);

        controller.jalankanProgram();
    }
}
