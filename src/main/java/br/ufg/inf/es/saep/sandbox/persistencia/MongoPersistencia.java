package br.ufg.inf.es.saep.sandbox.persistencia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MongoPersistencia implements IPersistencia {
    String nomeBanco = "saep";
    MongoDatabase mongoDatabase;

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
        mongoDatabase.getCollection(collection).updateOne(
                eq(identificadorObjeto, valorIdentificador),
                Document.parse(json));
    }

    public void deletaJSON(String collection, String id) {
        mongoDatabase.getCollection(collection).deleteOne(eq("id", id));
    }

}
