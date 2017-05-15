package ar.edu.utn.dds.modelo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;



public interface Operando {
public int calcular();





public static void main(String[] args) throws IOException {
	
	// lector.leerArchivo();

	ArrayList<Cuenta> cuentas = new ArrayList<>();
	Cuenta cuentaA = new Cuenta("cuentaA", 1, "12/10/1976");
	Cuenta cuentaB = new Cuenta("cuentaB", 12, "12/10/1686");
	Cuenta cuentaC = new Cuenta("cuentaC", 12, "12/10/1696");
	Cuenta cuentaD = new Cuenta("cuentaD", 13, "12/10/1676");
	Cuenta cuentaE = new Cuenta("cuentaA", 10, "12/10/2016");
	Cuenta cuentaF = new Cuenta("cuentaA", 1, "12/10/1986");
	cuentas.add(cuentaA);
	cuentas.add(cuentaB);
	cuentas.add(cuentaC);
	cuentas.add(cuentaD);
	cuentas.add(cuentaE);
	cuentas.add(cuentaF);
	NodoNumero numero=new NodoNumero();
	numero.setNumero(5);
	
	Division division=new Division();
	
	NodoIndicador k=new NodoIndicador();
	k.setOperador(division);
	k.setOperando1(cuentaC);
	k.setOperando2(numero);
	
	/*Indicador h=new Indicador(division, cuentaC, k );
	Indicador i= new Indicador(suma, h, numero);*/
	System.out.println(k.calcular());
	
}}
