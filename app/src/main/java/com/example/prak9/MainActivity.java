package com.example.prak9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextInputEditText fileName = findViewById(R.id.fileName); //имя файла
        TextInputEditText fileContent = findViewById(R.id.fileContent); //контент
        Button saveButton = findViewById(R.id.saveButton); //сохранить
        Button readButton = findViewById(R.id.readButton); //читать
        Button deleteButton = findViewById(R.id.deleteButton); //удалить
        Button putButton = findViewById(R.id.putButton); //+
        TextView fileContentField = findViewById(R.id.fileContentField); //показ содержимого

//сохранить
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString().matches("") ||
                        fileContent.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(),
                            "Какое-то из полей пустое", Toast.LENGTH_SHORT).show();
                    return;
                }
                File storageDir =
                        Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOCUMENTS);
                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }
                File file = new File(storageDir, fileName.getText().toString());
                try {
                    if (!file.exists()) {
                        boolean created = file.createNewFile();
                        if (created) {
                            FileWriter writer = new FileWriter(file);
                            writer.append(fileContent.getText().toString());
                            writer.flush();
                            writer.close();
                        }
                    } else {
                        FileWriter writer = new FileWriter(file);
                        writer.append(fileContent.getText().toString());
                        writer.flush();
                        writer.close();
                    }
                    Toast.makeText(getApplicationContext(),
                            "Сохранено", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//читать
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(),
                            "Имя файла пустое", Toast.LENGTH_SHORT).show();
                    return;
                }
                File storageDir =
                        Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOCUMENTS);
                File file = new File(storageDir, fileName.getText().toString());
                if (file.exists()) {
                    StringBuilder text = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new
                                FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            text.append('\n');
                        }
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();}
                    String fileData = text.toString();
                    fileContentField.setText(fileData);
                    Toast.makeText(getApplicationContext(),
                            "Прочитано", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Файла не существует", Toast.LENGTH_SHORT).show();
                }
            }
        });

//удалить
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(),
                            "Имя файла пустое", Toast.LENGTH_SHORT).show();
                    return;}
                File storageDir =
                        Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOCUMENTS);
                File file = new File(storageDir, fileName.getText().toString());
                if (file.exists()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Вы точно хотите удалить файл?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        Toast.makeText(getApplicationContext(),
                                                "Файл удален", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Не удалось удалить файл",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Отмена",
                                    new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .create()
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Файл не существует", Toast.LENGTH_SHORT).show();
                }
            }
        });

//+(изменить)
        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString().matches("") ||
                        fileContent.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(),
                            "Какое-то из полей пустое", Toast.LENGTH_SHORT).show();
                    return;
                }
                File storageDir =
                        Environment.getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_DOCUMENTS);
                File file = new File(storageDir, fileName.getText().toString());
                try {
                    if (!file.exists()) {
                        Toast.makeText(getApplicationContext(),
                                "Сначала создайте файл", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        FileWriter writer = new FileWriter(file, true);
                        writer.append(fileContent.getText().toString());
                        writer.flush();
                        writer.close();
                    }
                    Toast.makeText(getApplicationContext(),
                            "Изменено", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}