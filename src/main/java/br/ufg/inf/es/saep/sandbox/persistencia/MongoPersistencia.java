package br.ufg.inf.es.saep.sandbox.persistencia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoPersistencia implements IPersistencia {
    String nomeBanco = "saep";
    MongoDatabase mongoDatabase;

    private static MongoPersistencia instance;

    public static synchronized MongoPersistencia getInstance() {
        if (instance == null)
            instance = new MongoPersistencia();

        return instance;
    }

    @Override
    public void iniciaConexaoBD() {
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(nomeBanco);
    }

    public void limpaBase(String collection) {
        mongoDatabase.getCollection(collection).drop();
    }

    @Override
    public void persisteJSON(String collection, String json) {
        Document document = Document.parse(json);
        mongoDatabase.getCollection(collection).insertOne(document);
    }

    @Override
    public Document buscaJSON(String collection, String id) {
        return mongoDatabase.getCollection(collection).find(eq("id", id)).first();
    }

    @Override
    public void atualizaJSON(String collection, String identificadorObjeto, String valorIdentificador, String json) {
        mongoDatabase.getCollection(collection).replaceOne(
                eq(identificadorObjeto, valorIdentificador),
                Document.parse(json));
    }

    public void deletaJSON(String collection, String id) {
        mongoDatabase.getCollection(collection).deleteOne(eq("id", id));
    }

    public List<String> pegaIdsCollection(String collection, String id) {
        List<String> listaIds = new ArrayList<String>();

        for (Document resolucao : mongoDatabase.getCollection(collection).find()) {
            listaIds.add(resolucao.getString(id));
        }
        return listaIds;
    }

}
