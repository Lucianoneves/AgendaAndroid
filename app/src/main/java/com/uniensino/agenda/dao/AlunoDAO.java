package com.uniensino.agenda.dao;

import com.uniensino.agenda.model.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private static final List<Aluno> alunos = new ArrayList<>();

    public void salva(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        alunos.remove(aluno);
    }

    public void edita(int posicao, Aluno alunoEditado) {
        alunos.set(posicao, alunoEditado);
    }

    public int posicaoDoAluno(Aluno aluno) {
        return alunos.indexOf(aluno);
    }
}
