package com.masnegocio.semilla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.masnegocio.semilla.models.User;
import com.masnegocio.semilla.utils.Singleton;

import java.util.ArrayList;


public class LoginActivity extends Activity {

    private ArrayList<User> users;

    private AutoCompleteTextView username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        users = new ArrayList<>();

        users.add(createUser("RCONCHAMN","ADMINISTRADOR"));
        users.add(createUser("ACASTILLOMN","RH"));
        users.add(createUser("RGONZALEZMN","IMPLEMENTADOR"));

        if(Singleton.getInstance().getUser()!=null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        username = (AutoCompleteTextView)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(User user : users){
                    if(user.getName().equals(username.getText().toString())){
                        Singleton.getInstance().setUser(user);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

    }

    private User createUser(String name, String... roles){

        User user = new User();
        user.setName(name);

        for(String rol : roles){
           user.addRol(rol);
        }

        return user;

    }
}
