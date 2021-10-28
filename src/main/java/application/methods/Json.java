package application.methods;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json
{
    public static void main(String[] args)
    {
        HashMap<String, Object> hashmap = new HashMap<String, Object>();

        hashmap.put("id", 11);
        hashmap.put("firstName", "Lokesh");

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            //Convert Map to JSON
            String json = mapper.writeValueAsString(hashmap);

            //Print JSON output
            System.out.println(json);
        }
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}