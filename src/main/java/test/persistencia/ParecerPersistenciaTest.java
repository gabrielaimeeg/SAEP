package test.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.es.saep.sandbox.persistencia.ParecerPersistencia;
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

//
//        Parecer parecer = new Parecer(
//                "1",
//                "CONSUNI 2013/2",
//                listaRadocId,
//                listaPontuacoes,
//                "fundamentacao",
//                listaNota
//        );

//        parecerPersistencia.persisteParecer(parecer);
//
//
//        Map<String, Valor> valores3 = new HashMap<>();
//        valores1.put("Publicacao artigo", new Valor(3));
//        parecerPersistencia.adicionaNota("1", new Nota(new Relato("tip3", valores3), new Relato("tip3", valores3), "testano"));

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

//    public void criaParecer() {
//        criaListaRadocID("1", "2", "3");
//        criaPontuacao("nomePontuacao1", "valor1", "nomePontuacao2", "valor2");
//        criaRelato("Publicacao de Artigo",
//                )
//
//    }

    private List<String> criaListaRadocID(String... valores) {
        List<String> listaRadocId = new ArrayList<>();

        for (String i : valores) {
            listaRadocId.add(i);
        }

        return listaRadocId;
    }

    private List<Pontuacao> criaPontuacao(String... valores) {

        if (valores.length % 2 == 0) {
            List<Pontuacao> listaPontuacoes = new ArrayList<>();

            for (int i = 0; i < valores.length - 1; i++) {
                listaPontuacoes.add(new Pontuacao(valores[i], new Valor(valores[i + 1])));
                i++;
            }

            return listaPontuacoes;
        } else {
            return null;
        }
    }

//    private Nota criaNota() {
//
//    }
//
//
//    private Valor criaValor() {
//        List<Nota> listaNota = new ArrayList<>();
//        listaNota.add(new Nota(criaRelato(), criaRelato(), "Porque eu quis."));
//    }

    private Relato criaRelato(String tipoRelato, Valor valor) {
        Map<String, Valor> valores = new HashMap<>();
        valores.put(tipoRelato, valor);
        return new Relato(tipoRelato, valores);

    }

}
