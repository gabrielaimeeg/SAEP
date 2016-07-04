package test.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.es.saep.sandbox.persistencia.ParecerPersistencia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParecerPersistenciaTest {
    ParecerPersistencia parecerPersistencia;

    @Before
    public void limpaBase() {
        parecerPersistencia = new ParecerPersistencia();
        parecerPersistencia.limpaBase("parecerCollection");
    }

    @Test
    public void testaAdicionarNota() {
        ParecerPersistencia parecerPersistencia = new ParecerPersistencia();

        List<String> listaRadocId = new ArrayList<>();
        listaRadocId.add("1");
        listaRadocId.add("2");
        listaRadocId.add("3");

        List<Pontuacao> listaPontuacoes = new ArrayList<>();
        listaPontuacoes.add(new Pontuacao("nomePontuacao1", new Valor("valor1")));
        listaPontuacoes.add(new Pontuacao("nomePontuacao2", new Valor("valor2")));

        List<Nota> listaNota = new ArrayList<>();

        Map<String, Valor> valores1 = new HashMap<>();
        valores1.put("Publicacao livro", new Valor(10));
        valores1.put("Publicacao livro", new Valor("Editora"));
        valores1.put("Publicacao livro", new Valor(3));

        Map<String, Valor> valores2 = new HashMap<>();
        valores1.put("Publicacao livro", new Valor(2));
        valores1.put("Publicacao livro", new Valor(7));

        Relato relato1 = new Relato("tipo1", valores1);
        Relato relato2 = new Relato("tipo2", valores2);

        listaNota.add(new Nota(relato1, relato2, "Porque eu quis."));

        Parecer parecer = new Parecer(
                "1",
                "CONSUNI 2013/2",
                listaRadocId,
                listaPontuacoes,
                "fundamentacao",
                listaNota
        );

        parecerPersistencia.persisteParecer(parecer);


        Map<String, Valor> valores3 = new HashMap<>();
        valores1.put("Publicacao artigo", new Valor(3));
        parecerPersistencia.adicionaNota("1", new Nota(new Relato("tip3", valores3), new Relato("tip3", valores3), "testano"));

       // Assert.assertEquals(parecerPersistencia.byId("1"), parecer);

    }

    @Test
    public void testaRemoveNota() {

    }

    @Test
    public void testaPersisteParecer() {

    }

    @Test
    public void testaById() {

    }

    @Test
    public void testaRemoveParecer() {

    }

    @Test
    public void testaRadocById() {

    }

    @Test
    public void testaPersisteRadoc() {

    }

    @Test
    public void testaRemoveRadoc() {

    }


}
