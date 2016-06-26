package br.ufg.inf.es.saep.java;

import java.util.Date;
import java.util.List;

public class Processo {
    int codigoUnico;
    String tipoProcesso;
    int matriculaDocente;
    Date dataIncio;
    Date dataFim;
    List<Radoc> listaRelatorios;
}
