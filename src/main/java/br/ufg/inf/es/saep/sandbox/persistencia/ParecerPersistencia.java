package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Alteracao;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ParecerPersistencia implements ParecerRepository {
    public void adicionaAlteracao(String parecer, Alteracao alteracao) {

    }

    public void persisteParecer(Parecer parecer) {

    }

    public void atualizaFundamentacao(String fundamentacao) {

    }

    public Parecer byId(String id) {
        return null;
    }

    public void remove(String id) {

    }

    private void iniciaConexaoBD(){
        MongoClient mongoClient = new MongoClient();

    }
}
