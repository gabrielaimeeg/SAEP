package br.ufg.inf.es.saep.repositories;

import br.ufg.inf.es.saep.java.Radoc;

public class RadocRepository implements IRepository<Radoc> {

    /**
     * Um RADOC representa um documento que armazena os Relatos
     * de um docente, durante um período de tempo(um ano base).
     */


    /**
     * Recupera RADOC pelo identificador.
     *
     * @param id O identificador único do RADOC.
     * @return O RADOC correspondente.
     */
    @Override
    public Radoc GetById(int id) {
        return null;
    }

    /**
     * Atualiza dados referentes ao RADOC.
     *
     * @param radoc objeto representante de um radoc
     */
    @Override
    public void update(Radoc radoc) {

    }

    /**
     * Persiste um novo documento RADOC.
     *
     * @param radoc  objeto representante de um radoc
     */
    @Override
    public void create(Radoc radoc) {

    }

    /**
     * Deleta uma instância de RADOC
     *
     * @param radoc  objeto representante de um radoc
     */
    @Override
    public void delete(Radoc radoc) {

    }

}
