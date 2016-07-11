package br.ufg.inf.es.saep.sandbox.persistencia;

import org.bson.Document;

import java.util.List;

public interface IPersistencia {
    void iniciaConexaoBD();

    void persisteJSON(String collection, String json);

    Document buscaJSON(String collection, String tipoIdentificador, String valorIdentificador);

    void atualizaJSON(String collection, String identificadorObjeto, String valorIdentificador, String json);

    void deletaJSON(String collection, String tipoIdentificador, String valorIdentificador);

    void limpaBase(String collection);

    void atualizaDocumentUsandoFiltro(String collection, Document parecer, Document filtro);

    List<String> pegaIdsCollection(String collection, String valorIdentificador);

}
