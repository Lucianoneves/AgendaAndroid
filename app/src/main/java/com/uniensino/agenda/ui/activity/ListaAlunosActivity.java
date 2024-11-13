package com.uniensino.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniensino.agenda.R;
import com.uniensino.agenda.dao.AlunoDAO;
import com.uniensino.agenda.model.Aluno;

import java.io.Serializable;

public class ListaAlunosActivity extends AppCompatActivity {

    private final AlunoDAO dao = new AlunoDAO();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle("Lista de Alunos");

        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class));
            }
        });

        configuraLista();
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_de_alunos_listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.todos());
        listaDeAlunos.setAdapter(adapter);
        registerForContextMenu(listaDeAlunos);

        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno aluno = adapter.getItem(posicao);

                if (aluno != null) {
                    Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
                    vaiParaFormulario.putExtra("aluno", aluno);
                    vaiParaFormulario.putExtra("posicao", posicao);
                    startActivity(vaiParaFormulario);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoEscolhido = adapter.getItem(info.position);

        if (item.getItemId() == R.id.activity_lista_alunos_menu_remover) {
            dao.remove(alunoEscolhido);
            adapter.remove(alunoEscolhido);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
