
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.Random;
import javax.swing.SwingUtilities;

public class PokemonNombre {

    private static final Logger LOGGER = ConfiguracionLogger.getLogger();
    private ArrayList<String> pokemon;
    private static final String PROPERTIES_FILE = "endpoints.properties";
    private static Properties properties;
    private String pkmName;
    private String pkmUrl;
    private ArrayList<String> allPokemonNames;

    static {
        properties = new Properties();
        try ( FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.severe("Error al cargar el archivo: " + e.getMessage());
        }
    }

    public static String getTypeUrl() {
        return properties.getProperty("tipo.url");
    }

    public void dataPokemon() {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            URI uri = URI.create(pkmUrl + pkmName);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElement = JsonParser.parseString(response.body());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String nombre = jsonObject.get("name").getAsString();
            String ilustracion = jsonObject.getAsJsonObject("sprites").get("front_default").getAsString();
            String tipo = jsonObject.getAsJsonArray("types")
                    .get(0).getAsJsonObject().getAsJsonObject("type").get("name").getAsString();

            String habilidad = "";
            int numero = jsonObject.get("id").getAsInt();
            double peso = jsonObject.get("weight").getAsDouble();
            double altura = jsonObject.get("height").getAsDouble();
            String urlHabilidad = "";
            String descripcionHabilidad = "";

            if (jsonObject.has("abilities") && jsonObject.getAsJsonArray("abilities").size() > 0) {
                JsonObject abilities = jsonObject.getAsJsonArray("abilities").get(0).getAsJsonObject();
                habilidad = abilities.getAsJsonObject("ability").get("name").getAsString();
                urlHabilidad = abilities.getAsJsonObject("ability").get("url").getAsString();
            }

            if (!urlHabilidad.isEmpty()) {
                URI uriHabilidad = URI.create(urlHabilidad);
                HttpRequest requestHabilidad = HttpRequest.newBuilder()
                        .uri(uriHabilidad)
                        .build();

                HttpResponse<String> responseHabilidad = httpClient.send(requestHabilidad, HttpResponse.BodyHandlers.ofString());
                JsonElement jsonElementHabilidad = JsonParser.parseString(responseHabilidad.body());
                JsonObject jsonHabilidad = jsonElementHabilidad.getAsJsonObject();
                descripcionHabilidad = obtenerDescripcion(jsonHabilidad);
            }

            JsonArray movesArray = jsonObject.getAsJsonArray("moves");
            JsonObject randomMove = obtenerAtaqueRandom(movesArray);
            String ataque = randomMove.getAsJsonObject("move").get("name").getAsString();

            String descripcionAtaque = "";
            descripcionAtaque = obtenerDescripcionAtaque(randomMove.getAsJsonObject("move").get("url").getAsString());


            pokemon.add(nombre);
            pokemon.add(ilustracion);
            pokemon.add(tipo);
            pokemon.add(habilidad);
            pokemon.add(descripcionHabilidad);
            pokemon.add(ataque);
            pokemon.add(descripcionAtaque);
            String pokemonType = pokemon.get(2);
            String weakness = obtenerDebilidad(pokemonType);
            pokemon.add(weakness);

            pokemon.add(String.valueOf(numero));
            pokemon.add(String.valueOf(peso));
            pokemon.add(String.valueOf(altura));
            
            String especieURL = jsonObject.getAsJsonObject("species").get("url").getAsString();

            URI uriEspecie = URI.create(especieURL);
            HttpRequest requestEspecie = HttpRequest.newBuilder()
                    .uri(uriEspecie)
                    .build();
            HttpResponse<String> responseEspecie = httpClient.send(requestEspecie, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElementEspecie = JsonParser.parseString(responseEspecie.body());
            JsonObject jsonEspecie = jsonElementEspecie.getAsJsonObject();

            JsonArray descriptions = jsonEspecie.getAsJsonArray("flavor_text_entries");
            String descripcionPokedex = obtenerDescripcionPokedex(descriptions);
            
            pokemon.add(descripcionPokedex);
            
        } catch (NullPointerException ex) {
            LOGGER.severe("Error al obtener las URLs: " + ex.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Sucedio un error al acceder a la API: " + e.getMessage());
        }
    }

    public PokemonNombre(String pokemonName, String pokemonurl) {
        pkmName = pokemonName;
        pkmUrl = pokemonurl;
        pokemon = new ArrayList<>();
    }

    private static JsonObject obtenerAtaqueRandom(JsonArray movesArray) {
        Random random = new Random();
        int randomAtaque = random.nextInt(movesArray.size());
        return movesArray.get(randomAtaque).getAsJsonObject();
    }

    private static String obtenerDescripcionAtaque(String moveUrl) {
        try {
            URI uri = URI.create(moveUrl);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElement = JsonParser.parseString(response.body());
            if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("effect_entries")) {
                JsonArray effectEntries = jsonElement.getAsJsonObject().getAsJsonArray("effect_entries");
                if (!effectEntries.isJsonNull() && effectEntries.size() > 0) {
                    return effectEntries.get(0).getAsJsonObject().get("short_effect").getAsString();
                }
            }
            return "No se pudo obtener la descripción del ataque.";
        } catch (IOException | InterruptedException e) {
            LOGGER.severe("Error al realizar la solicitud HTTP: " + e.getMessage());
            return "";
        } catch (JsonParseException | IllegalStateException e) {
            LOGGER.severe("Error al procesar la respuesta JSON." + e.getMessage());
            return "";
        } catch (Exception e) {
            LOGGER.severe("Ocurrió un error inesperado." + e.getMessage());
            return "";
        }
    }

    private static String obtenerDescripcion(JsonObject jsonHabilidad) {
        String descripcionEnIngles = "";

        try {
            if (jsonHabilidad.has("effect_entries") && jsonHabilidad.getAsJsonArray("effect_entries").size() > 0) {
                JsonArray effectEntries = jsonHabilidad.getAsJsonArray("effect_entries");

                for (JsonElement entry : effectEntries) {
                    JsonObject entryObject = entry.getAsJsonObject();
                    JsonObject language = entryObject.getAsJsonObject("language");

                    if (language.has("name") && language.get("name").getAsString().equals("en")) {
                        descripcionEnIngles = entryObject.get("effect").getAsString();
                        break;
                    }
                }
            }
        }  catch (Exception e) {
            LOGGER.severe("Error al querer obtener la información: " + e.getMessage());
        }
        return descripcionEnIngles;
    }
    private static String obtenerDescripcionPokedex(JsonArray descriptions) {
        // Iterar a través de las descripciones y devolver la primera en inglés (puedes ajustar según tus necesidades)
        for (JsonElement entry : descriptions) {
            JsonObject description = entry.getAsJsonObject();
            String language = description.getAsJsonObject("language").get("name").getAsString();
            if (language.equals("en")) {
                return description.get("flavor_text").getAsString();
            }
        }
        return "No hay descripción de la Pokédex disponible.";
    }

    private static String sendHttpRequest(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try ( BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            }
        } catch (IOException e) {
            LOGGER.severe("Error al acceder a la API: " + e.getMessage());
            return "";
        }
    }

    public String obtenerDebilidad(String type) throws Exception {
        try {
            String typeEndpoint = getTypeUrl() + type + "/";
            String typeResponse = sendHttpRequest(typeEndpoint);
            JsonObject typeJson = JsonParser.parseString(typeResponse).getAsJsonObject();
            JsonObject damageRelations = typeJson.getAsJsonObject("damage_relations");
            JsonArray doubleDamageFrom = damageRelations.getAsJsonArray("double_damage_from");

            StringBuilder weakness = new StringBuilder();
            for (int i = 0; i < doubleDamageFrom.size(); i++) {
                JsonObject damageType = doubleDamageFrom.get(i).getAsJsonObject();
                weakness.append(damageType.get("name").getAsString());
                if (i < doubleDamageFrom.size() - 1) {
                    weakness.append(", ");
                }
            }
            return weakness.toString();
        } catch (IOException e) {
            LOGGER.severe("Error al obtener debilidades del tipo: " + e.getMessage());
            return "";
        }
    }
    public static ArrayList<String> obtenerNombresPokemon() {
        ArrayList<String> pokemonNames = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            URI uri = URI.create("https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElement = JsonParser.parseString(response.body());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (JsonElement element : results) {
                String name = element.getAsJsonObject().get("name").getAsString();
                pokemonNames.add(name);
            }

        } catch (Exception e) {
            // Manejo de excepciones generales
            System.err.println("Ocurrió un error: " + e.getMessage());
        }

        return pokemonNames;
    }

    public ArrayList<String> getPokemon() {
        dataPokemon();
        return pokemon;
    }

}
