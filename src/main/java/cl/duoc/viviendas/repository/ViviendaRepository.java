package cl.duoc.viviendas.repository;

import cl.duoc.viviendas.model.Vivienda;
import cl.duoc.viviendas.model.Casa;
import cl.duoc.viviendas.model.Departamento;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViviendaRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String ARCHIVO_CASAS = "casas.json";
    private final String ARCHIVO_DEPARTAMENTOS = "departamentos.json";

    public void guardarViviendas(List<Vivienda> viviendas) {

        List<Casa> casas = new ArrayList<>();
        List<Departamento> departamentos = new ArrayList<>();

        for (Vivienda vivienda : viviendas) {
            if (vivienda instanceof Casa) {
                casas.add((Casa) vivienda);
            } else if (vivienda instanceof Departamento) {
                departamentos.add((Departamento) vivienda);
            }
        }

        try (FileWriter writer = new FileWriter(ARCHIVO_CASAS)) {
            gson.toJson(casas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(ARCHIVO_DEPARTAMENTOS)) {
            gson.toJson(departamentos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Vivienda> cargarViviendas() {

        List<Vivienda> viviendas = new ArrayList<>();

        Type tipoCasas = new TypeToken<List<Casa>>() {}.getType();
        Type tipoDepartamentos = new TypeToken<List<Departamento>>() {}.getType();

        try (FileReader reader = new FileReader(ARCHIVO_CASAS)) {
            viviendas.addAll(gson.fromJson(reader, tipoCasas));
        } catch (IOException e) {
            // Si el archivo no existe, comienza vacío.
        }

        try (FileReader reader = new FileReader(ARCHIVO_DEPARTAMENTOS)) {
            viviendas.addAll(gson.fromJson(reader, tipoDepartamentos));
        } catch (IOException e) {
            // Si el archivo no existe, comienza vacío.
        }

        return viviendas;
    }
}