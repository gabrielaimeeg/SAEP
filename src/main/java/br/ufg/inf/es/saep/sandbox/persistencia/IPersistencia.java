package br.ufg.inf.es.saep.sandbox.persistencia;

import org.bson.Document;

import java.util.List;

public interface IPersistencia {
    void iniciaConexaoBD();

    void persisteJSON(String collection, String json);

    Document buscaJSON(String collection, String id);

    void atualizaJSON(String collection, String identificadorObjeto, String valorIdentificador, String json);

    void deletaJSON(String collection, String id);

    void limpaBase(String collection);

    List<String> pegaIdsCollection(String collection, String id);

}
