package org.example.repository; // ajuste o pacote conforme sua estrutura

import jakarta.annotation.PostConstruct;
import org.example.enums.ClasseAventureiro;
import org.example.enums.EspecieCompanheiro;
import org.example.model.Aventureiro;
import org.example.model.Companheiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AventureiroRepository {
    private final List<Aventureiro> db = new ArrayList<>();
    private Long idCounter = 1L;

    @PostConstruct
    public void init() {
        db.add(criar("Onyxia", ClasseAventureiro.MAGO, 60, true, new Companheiro("Shadow", EspecieCompanheiro.CORUJA, 89)));
        db.add(criar("Ysera", ClasseAventureiro.GUERREIRO, 18, true, new Companheiro("Ignis", EspecieCompanheiro.LOBO, 94)));
        db.add(criar("Nefarian", ClasseAventureiro.MAGO, 47, true, new Companheiro("Bolt", EspecieCompanheiro.CORUJA, 43)));
        db.add(criar("Gelbin", ClasseAventureiro.LADINO, 37, false, null));
        db.add(criar("Moira", ClasseAventureiro.ARQUEIRO, 3, false, new Companheiro("Trovão", EspecieCompanheiro.GOLEM, 96)));
        db.add(criar("Brann", ClasseAventureiro.MAGO, 54, true, new Companheiro("Frost", EspecieCompanheiro.DRAGAO_MINIATURA, 88)));
        db.add(criar("Kil'jaeden", ClasseAventureiro.LADINO, 28, false, null));
        db.add(criar("Chen", ClasseAventureiro.MAGO, 12, false, new Companheiro("Pedregulho", EspecieCompanheiro.GOLEM, 74)));
        db.add(criar("Kaelen", ClasseAventureiro.LADINO, 41, false, new Companheiro("Sol", EspecieCompanheiro.CORUJA, 84)));
        db.add(criar("Lyra", ClasseAventureiro.CLERIGO, 32, false, null));
        db.add(criar("Cho'gall", ClasseAventureiro.MAGO, 33, false, new Companheiro("Fagulha", EspecieCompanheiro.DRAGAO_MINIATURA, 73)));
        db.add(criar("Fairbanks", ClasseAventureiro.GUERREIRO, 19, false, new Companheiro("Estrela", EspecieCompanheiro.LOBO, 27)));
        db.add(criar("Bolvar", ClasseAventureiro.ARQUEIRO, 54, true, null));
        db.add(criar("Saurfang", ClasseAventureiro.LADINO, 58, true, null));
        db.add(criar("Velen", ClasseAventureiro.GUERREIRO, 48, true, new Companheiro("Chama", EspecieCompanheiro.DRAGAO_MINIATURA, 69)));
        db.add(criar("Maraad", ClasseAventureiro.LADINO, 31, true, null));
        db.add(criar("Eitrigg", ClasseAventureiro.ARQUEIRO, 59, true, null));
        db.add(criar("Tyrande", ClasseAventureiro.ARQUEIRO, 33, false, null));
        db.add(criar("Ner'zhul", ClasseAventureiro.CLERIGO, 52, true, null));
        db.add(criar("Aegwynn", ClasseAventureiro.GUERREIRO, 56, true, new Companheiro("Luz", EspecieCompanheiro.LOBO, 46)));
        db.add(criar("Muradin", ClasseAventureiro.GUERREIRO, 16, true, new Companheiro("Rocha", EspecieCompanheiro.LOBO, 91)));
        db.add(criar("Malygos", ClasseAventureiro.CLERIGO, 44, true, null));
        db.add(criar("Garrosh", ClasseAventureiro.MAGO, 50, false, new Companheiro("Mordida", EspecieCompanheiro.CORUJA, 34)));
        db.add(criar("Falstad", ClasseAventureiro.ARQUEIRO, 41, true, null));
        db.add(criar("Nathanos", ClasseAventureiro.MAGO, 16, true, new Companheiro("Max", EspecieCompanheiro.LOBO, 69)));
        db.add(criar("Danath", ClasseAventureiro.LADINO, 28, true, null));
        db.add(criar("Fendral", ClasseAventureiro.CLERIGO, 17, false, new Companheiro("Brisa", EspecieCompanheiro.CORUJA, 53)));
        db.add(criar("Vereesa", ClasseAventureiro.CLERIGO, 39, true, new Companheiro("Garra", EspecieCompanheiro.LOBO, 59)));
        db.add(criar("Cairne", ClasseAventureiro.MAGO, 16, false, new Companheiro("Titã", EspecieCompanheiro.GOLEM, 62)));
        db.add(criar("Khadgar", ClasseAventureiro.MAGO, 2, false, null));
        db.add(criar("Malfurion", ClasseAventureiro.ARQUEIRO, 12, true, new Companheiro("Raio", EspecieCompanheiro.DRAGAO_MINIATURA, 45)));
        db.add(criar("Voss", ClasseAventureiro.MAGO, 26, true, new Companheiro("Vento", EspecieCompanheiro.CORUJA, 55)));
        db.add(criar("Tess", ClasseAventureiro.CLERIGO, 53, false, null));
        db.add(criar("Hamuul", ClasseAventureiro.ARQUEIRO, 29, false, null));
        db.add(criar("Orgrim", ClasseAventureiro.CLERIGO, 12, false, new Companheiro("Muralha", EspecieCompanheiro.GOLEM, 36)));
        db.add(criar("Maiev", ClasseAventureiro.GUERREIRO, 9, true, new Companheiro("Sentinela", EspecieCompanheiro.GOLEM, 45)));
        db.add(criar("Rokhan", ClasseAventureiro.GUERREIRO, 36, true, new Companheiro("Gelo", EspecieCompanheiro.LOBO, 57)));
        db.add(criar("Grommash", ClasseAventureiro.GUERREIRO, 48, false, new Companheiro("Fúria", EspecieCompanheiro.LOBO, 65)));
        db.add(criar("Thrall", ClasseAventureiro.MAGO, 39, true, null));
        db.add(criar("Rhonin", ClasseAventureiro.CLERIGO, 41, false, new Companheiro("Cinzas", EspecieCompanheiro.LOBO, 97)));
        db.add(criar("Kael'thas", ClasseAventureiro.CLERIGO, 41, true, new Companheiro("Brasa", EspecieCompanheiro.DRAGAO_MINIATURA, 82)));
        db.add(criar("Nobundo", ClasseAventureiro.MAGO, 43, true, new Companheiro("Luna", EspecieCompanheiro.DRAGAO_MINIATURA, 33)));
        db.add(criar("TaranZhu", ClasseAventureiro.ARQUEIRO, 14, true, null));
        db.add(criar("Sindragosa", ClasseAventureiro.ARQUEIRO, 18, false, null));
        db.add(criar("Anduin", ClasseAventureiro.MAGO, 34, true, new Companheiro("Luminoso", EspecieCompanheiro.CORUJA, 35)));
        db.add(criar("Akama", ClasseAventureiro.GUERREIRO, 29, false, null));
        db.add(criar("Faranell", ClasseAventureiro.MAGO, 12, true, new Companheiro("Aço", EspecieCompanheiro.GOLEM, 92)));
        db.add(criar("Baine", ClasseAventureiro.LADINO, 58, false, new Companheiro("Tronco", EspecieCompanheiro.GOLEM, 75)));
        db.add(criar("Llane", ClasseAventureiro.LADINO, 46, true, null));
        db.add(criar("Broxigar", ClasseAventureiro.ARQUEIRO, 26, true, null));
        db.add(criar("Calia", ClasseAventureiro.LADINO, 3, false, null));
        db.add(criar("Whitemane", ClasseAventureiro.LADINO, 37, true, new Companheiro("Fantasma", EspecieCompanheiro.CORUJA, 95)));
        db.add(criar("Medivh", ClasseAventureiro.GUERREIRO, 44, true, null));
        db.add(criar("Krasus", ClasseAventureiro.MAGO, 8, true, null));
        db.add(criar("Garona", ClasseAventureiro.LADINO, 37, false, null));
        db.add(criar("Alexandros", ClasseAventureiro.ARQUEIRO, 39, false, new Companheiro("Cinzento", EspecieCompanheiro.CORUJA, 24)));
        db.add(criar("Deathwing", ClasseAventureiro.GUERREIRO, 37, true, new Companheiro("Magma", EspecieCompanheiro.GOLEM, 64)));
        db.add(criar("Illidan", ClasseAventureiro.CLERIGO, 45, true, new Companheiro("Noturno", EspecieCompanheiro.CORUJA, 23)));
        db.add(criar("Turalyon", ClasseAventureiro.CLERIGO, 47, false, new Companheiro("Rex", EspecieCompanheiro.GOLEM, 25)));
        db.add(criar("Liam", ClasseAventureiro.LADINO, 47, true, new Companheiro("Agudo", EspecieCompanheiro.GOLEM, 37)));
        db.add(criar("Teron", ClasseAventureiro.ARQUEIRO, 48, true, new Companheiro("Sopro", EspecieCompanheiro.DRAGAO_MINIATURA, 42)));
        db.add(criar("Gul'dan", ClasseAventureiro.ARQUEIRO, 16, true, null));
        db.add(criar("Koltira", ClasseAventureiro.CLERIGO, 37, false, null));
        db.add(criar("Gallywix", ClasseAventureiro.MAGO, 54, false, null));
        db.add(criar("Grom", ClasseAventureiro.MAGO, 31, true, new Companheiro("Escuro", EspecieCompanheiro.CORUJA, 67)));
        db.add(criar("Thassarian", ClasseAventureiro.ARQUEIRO, 47, true, new Companheiro("Dente", EspecieCompanheiro.DRAGAO_MINIATURA, 84)));
        db.add(criar("Darion", ClasseAventureiro.GUERREIRO, 2, false, new Companheiro("Rajada", EspecieCompanheiro.DRAGAO_MINIATURA, 37)));
        db.add(criar("Varian", ClasseAventureiro.CLERIGO, 11, true, null));
        db.add(criar("Gazlowe", ClasseAventureiro.LADINO, 50, true, new Companheiro("Engrenagem", EspecieCompanheiro.LOBO, 82)));
        db.add(criar("Chromie", ClasseAventureiro.CLERIGO, 28, true, new Companheiro("Tempo", EspecieCompanheiro.CORUJA, 36)));
        db.add(criar("Yrel", ClasseAventureiro.LADINO, 10, false, null));
        db.add(criar("Tirion", ClasseAventureiro.CLERIGO, 26, true, null));
        db.add(criar("Wrathion", ClasseAventureiro.GUERREIRO, 16, false, null));
        db.add(criar("Vol'jin", ClasseAventureiro.GUERREIRO, 14, true, new Companheiro("Espírito", EspecieCompanheiro.DRAGAO_MINIATURA, 22)));
        db.add(criar("Ebonhorn", ClasseAventureiro.CLERIGO, 25, false, new Companheiro("Obsidiana", EspecieCompanheiro.GOLEM, 57)));
        db.add(criar("Sylvanas", ClasseAventureiro.ARQUEIRO, 21, true, new Companheiro("Sombra", EspecieCompanheiro.CORUJA, 80)));
        db.add(criar("Nozdormu", ClasseAventureiro.GUERREIRO, 34, true, null));
        db.add(criar("Sabellian", ClasseAventureiro.MAGO, 8, true, new Companheiro("Poeira", EspecieCompanheiro.GOLEM, 74)));
        db.add(criar("Archimonde", ClasseAventureiro.ARQUEIRO, 57, false, new Companheiro("Pedra", EspecieCompanheiro.LOBO, 58)));
        db.add(criar("Magni", ClasseAventureiro.GUERREIRO, 38, false, null));
        db.add(criar("Blackhand", ClasseAventureiro.ARQUEIRO, 31, false, null));
        db.add(criar("Kael", ClasseAventureiro.MAGO, 34, false, new Companheiro("Fogo", EspecieCompanheiro.DRAGAO_MINIATURA, 97)));
        db.add(criar("Neltharion", ClasseAventureiro.LADINO, 10, true, new Companheiro("Onyx", EspecieCompanheiro.CORUJA, 37)));
        db.add(criar("Renault", ClasseAventureiro.MAGO, 30, false, new Companheiro("Centelha", EspecieCompanheiro.DRAGAO_MINIATURA, 92)));
        db.add(criar("Alleria", ClasseAventureiro.CLERIGO, 23, false, null));
        db.add(criar("LorewalkerCho", ClasseAventureiro.CLERIGO, 42, true, new Companheiro("Pergaminho", EspecieCompanheiro.GOLEM, 44)));
        db.add(criar("Kurdan", ClasseAventureiro.CLERIGO, 7, false, new Companheiro("Martelo", EspecieCompanheiro.GOLEM, 20)));
        db.add(criar("Lothar", ClasseAventureiro.ARQUEIRO, 22, true, new Companheiro("Lâmina", EspecieCompanheiro.DRAGAO_MINIATURA, 41)));
        db.add(criar("Lilian", ClasseAventureiro.CLERIGO, 20, true, null));
        db.add(criar("Darius", ClasseAventureiro.MAGO, 22, true, new Companheiro("Nevada", EspecieCompanheiro.LOBO, 30)));
        db.add(criar("Putress", ClasseAventureiro.ARQUEIRO, 17, false, null));
        db.add(criar("Durotan", ClasseAventureiro.GUERREIRO, 31, true, null));
        db.add(criar("Alexstrasza", ClasseAventureiro.CLERIGO, 38, true, null));
        db.add(criar("LiLi", ClasseAventureiro.CLERIGO, 31, true, null));
        db.add(criar("Lo'Gosh", ClasseAventureiro.MAGO, 28, true, new Companheiro("Fera", EspecieCompanheiro.DRAGAO_MINIATURA, 72)));
        db.add(criar("Kargath", ClasseAventureiro.LADINO, 34, true, new Companheiro("Ferro", EspecieCompanheiro.GOLEM, 85)));
        db.add(criar("Lor'themar", ClasseAventureiro.GUERREIRO, 8, true, new Companheiro("Penugem", EspecieCompanheiro.CORUJA, 70)));
        db.add(criar("Broll", ClasseAventureiro.LADINO, 58, true, new Companheiro("Urso", EspecieCompanheiro.LOBO, 87)));
        db.add(criar("Genn", ClasseAventureiro.ARQUEIRO, 7, true, new Companheiro("Uivo", EspecieCompanheiro.LOBO, 48)));
        db.add(criar("Kalecgos", ClasseAventureiro.MAGO, 55, true, new Companheiro("Azul", EspecieCompanheiro.DRAGAO_MINIATURA, 80)));
    }

    private Aventureiro criar(String nome, ClasseAventureiro classe, Integer nivel, Boolean ativo, Companheiro companheiro) {
        Aventureiro a = new Aventureiro();
        a.setId(idCounter++);
        a.setNome(nome);
        a.setClasse(classe);
        a.setNivel(nivel);
        a.setAtivo(ativo);
        a.setCompanheiro(companheiro);
        return a;
    }

    public Aventureiro save(Aventureiro aventureiro) {
        if (aventureiro.getId() == null) {
            aventureiro.setId(idCounter++);
            db.add(aventureiro);
        }
        return aventureiro;
    }

    public Optional<Aventureiro> findById(Long id) {
        return db.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<Aventureiro> findAllFiltered(ClasseAventureiro classe, Boolean ativo, Integer nivelMinimo) {
        return db.stream()
                .filter(a -> (classe == null || a.getClasse() == classe))
                .filter(a -> (ativo == null || a.getAtivo().equals(ativo)))
                .filter(a -> (nivelMinimo == null || a.getNivel() >= nivelMinimo))
                .sorted(Comparator.comparing(Aventureiro::getId))
                .collect(Collectors.toList());
    }
}