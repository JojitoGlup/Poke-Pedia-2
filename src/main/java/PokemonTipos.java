
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class PokemonTipos{

    private static final Logger LOGGER = ConfiguracionLogger.getLogger();

    private static final String PROPERTIES_FILE = "endpoints.properties";
    private static Properties properties;
    private String opcion;

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

    public static String getAllTypeUrl() {
        return properties.getProperty("todosLosTipos.url");
    }

    public static String getPokemonUrl() {
        return properties.getProperty("pokemon.url");
    }

    public PokemonTipos(String eleccion) {
        opcion = eleccion;
    }
    public PokemonTipos() {
        
    }

    public ArrayList<String> infoPokemon(){
        ArrayList<String> pokemon = null;
        try {
            String pokemonurl = getPokemonUrl();
            PokemonNombre pokemonApi = new PokemonNombre(opcion, pokemonurl);
            pokemon = pokemonApi.getPokemon();

        } catch (NullPointerException ex) {
            LOGGER.severe("Error al obtener las URLs: " + ex.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Sucedio un error: " + e.getMessage());
        }
        return pokemon;
    }
    
    public String[] listaPokemon() throws IOException {
        String typeEndpoint = getTypeUrl() + opcion + "/";
        String typeResponse = sendHttpRequest(typeEndpoint);
        JsonObject typeJson = JsonParser.parseString(typeResponse).getAsJsonObject();
        JsonArray pokemonList = typeJson.getAsJsonArray("pokemon");
        String[] pokemonLista = new String[pokemonList.size()];
        for (int i = 0; i < pokemonList.size(); i++) {
            JsonObject pokemon = pokemonList.get(i).getAsJsonObject();
            pokemonLista[i] = pokemon.getAsJsonObject("pokemon").get("name").getAsString();
        }
        return pokemonLista;
    }

    public String[] conectar() {
        String[] types = null;
        try {
            String allTypesEndpoint = getAllTypeUrl();
            String allTypesResponse = sendHttpRequest(allTypesEndpoint);
            JsonObject allTypesJson = JsonParser.parseString(allTypesResponse).getAsJsonObject();
            JsonArray typesList = allTypesJson.getAsJsonArray("results");
            types = new String[typesList.size()];
            for (int i = 0; i < typesList.size(); i++) {
                JsonObject type = typesList.get(i).getAsJsonObject();
                types[i] = type.get("name").getAsString();
            }
        } catch (NullPointerException ex) {
            LOGGER.severe("Error al obtener las URLs: " + ex.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Sucedio un error al acceder a la API: " + e.getMessage());
        }
        return types;
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

}
