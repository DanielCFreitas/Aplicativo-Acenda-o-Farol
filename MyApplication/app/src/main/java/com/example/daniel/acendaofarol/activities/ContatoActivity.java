package com.example.daniel.acendaofarol.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.acendaofarol.R;

public class ContatoActivity extends AppCompatActivity {

    Button btnEnviar;
    EditText edtAssunto;
    EditText edtMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        edtAssunto = (EditText) findViewById(R.id.edtAssunto);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);

        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assunto = edtAssunto.getText().toString().trim();
                String mensagem = edtMensagem.getText().toString().trim();

                if (assunto.equals(null) || assunto.equals("")){
                    edtAssunto.setError("Digite o assunto");
                } else if (mensagem.equals(null) || mensagem.equals("")){
                    edtMensagem.setError("Digite a mensagem");
                }

                enviarEmail(assunto, mensagem);
            }
        });
    }

    // Método que envia o e-mail
    public void enviarEmail(String assunto, String mensagem){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"daniel.exatas@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, assunto);
        i.putExtra(Intent.EXTRA_TEXT, mensagem);
        try {
            startActivity(Intent.createChooser(i, "Enviando e-mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContatoActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
