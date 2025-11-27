package dao;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import entity.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonManager{
    private static final String FILE_PATH = "medicament_data.json";
    private static Gson gson;

    static{
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting(); // pour avoir un json lisible
        builder.registerTypeAdapter(LocalDateAdapter.class, new LocalDateAdapter());

        builder.registerTypeAdapter(Medicament.class, new MedicamentDeserialiser());
        gson = builder.create();
    }


    public static void save(List<Medicament> medicaments){
        try (Writer writer = new FileWriter(FILE_PATH)){
            gson.toJson(medicaments, writer);
            System.out.println("Donnees sauvergardees dans " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Medicament> load(){
        File file = new File(FILE_PATH);
        if (!file.exists()){
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(FILE_PATH)){
            Type listType = new TypeToken<List<Medicament>>(){}.getType();
            List<Medicament> medicaments = gson.fromJson(reader,listType);
            return medicaments != null ? medicaments : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static class MedicamentDeserialiser implements JsonDeserializer<Medicament>{
        @Override
        public Medicament deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement typeElement = jsonObject.get("type");

            if (typeElement == null){
                throw new JsonParseException("Champ type n'existe pas");
            }

            String type = typeElement.getAsString();

            switch (type){
                case "SIRUP": return context.deserialize(jsonObject, Sirup.class);
                case "PILL": return context.deserialize(jsonObject, Pill.class);
                case "INJECTION": return context.deserialize(jsonObject, Injection.class);
                case "POMADE": return context.deserialize(jsonObject, Pomade.class);
                default: throw new JsonParseException("Type de medicament n'existe pas");
            }
        }
    }
}
