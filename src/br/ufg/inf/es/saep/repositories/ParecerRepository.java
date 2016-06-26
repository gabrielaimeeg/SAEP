package br.ufg.inf.es.saep.repositories;

import br.ufg.inf.es.saep.java.Parecer;

public class ParecerRepository implements IRepository<Parecer> {

    /**
     * Um parecer é o resultado de da avaliação
     * dos relatos de um RADOC, baseado
     * em uma Norma. O parecer é feito pela Comissão
     * de Avaliação Docente (CAD), após a análise dos
     * relatos, e armazenado para futuras consultas.
     */


    /**
     * Recupera um Parecer pelo seu identificador.
     *
     * @param id O identificador único do Parecer.
     * @return O Parecer correspondente.
     */
    @Override
    public Parecer GetById(int id) {
        return null;
    }


    /**
     * Atualiza dados de um Parecer.
     *
     * @param parecer
     */
    @Override
    public void update(Parecer parecer) {

    }

    /**
     * Cria um novo Parecer.
     *
     * @param parecer
     */
    @Override
    public void create(Parecer parecer) {

    }

    /**
     * Deleta um Parecer.
     *
     * @param parecer
     */
    @Override
    public void delete(Parecer parecer) {

    }
}
