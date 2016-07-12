package br.ufg.inf.es.saep.sandbox.persistencia;

import org.bson.Document;

import java.util.List;

public interface IPersistencia {
    void iniciaConexaoBD();

    void persiste(String collection, String json);

    Document busca(String collection, String tipoIdentificador, String valorIdentificador);

    void atualiza(String collection, String identificadorObjeto, String valorIdentificador, String json);

    void deleta(String collection, String tipoIdentificador, String valorIdentificador);

    void limpaBase(String collection);

    void atualizaDocumentUsandoFiltro(String collection, Document parecer, Document filtro);

    List<String> listaValoresDeCollection(String collection, String valorIdentificador);

    Iterable<Document> encontraTodosDocumentsPorFiltro(String collection, String identificadorValor, String identificadorNome);

}
