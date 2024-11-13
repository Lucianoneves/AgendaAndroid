package com.uniensino.agenda.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.uniensino.agenda.R;
import com.uniensino.agenda.dao.AlunoDAO;
import com.uniensino.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();
    private Aluno aluno;
    private int posicao = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_fone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);

        Intent dados = getIntent();
        if (dados.hasExtra("aluno")) {
            aluno = (Aluno) dados.getSerializableExtra("aluno");
            posicao = dados.getIntExtra("posicao", -1);

            if (aluno != null && posicao != -1) {
                campoNome.setText(aluno.getNome());
                campoTelefone.setText(aluno.getTelefone());
                campoEmail.setText(aluno.getEmail());
            }
        }

        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = campoNome.getText().toString();
                String telefone = campoTelefone.getText().toString();
                String email = campoEmail.getText().toString();

                if (aluno == null) {
                    aluno = new Aluno(nome, telefone, email);
                    dao.salva(aluno);
                } else {
                    Aluno alunoEditado = new Aluno(nome, telefone, email);
                    dao.edita(posicao, alunoEditado);
                }
                finish();
            }
        });
    }
}
