package cartera.campitos.org.carteraclientesandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {


private static String urlBase="http://192.168.1.72:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    Button botonpost= (Button) findViewById(R.id.botonPost);
        botonpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TareaAsincronicaPost tareaAsincronicaPost=new TareaAsincronicaPost();
                tareaAsincronicaPost.execute(null,null,null);
            }
        });

        //Boton get todos
        Button getTodos= (Button) findViewById(R.id.botonGetTodos);
        getTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TareaAsyncronicaGetPorId todos=new TareaAsyncronicaGetPorId();
                todos.execute(null,null,null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
     CLASE PARA HACER EL POST
     */

    class TareaAsincronicaPost extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            //no se ejecuta en el thread principal, aqui solicitamos el envio de la informacion
            try {
              String respuesta=  hacerPost(new Cliente());
                System.out.println("<<<<<<< enviado con exito"+respuesta);
            } catch (Exception e) {
               System.out.println("<<<<<<<<<< Algo malo sucedio "+e.getMessage());
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer i){
            //Se ejecuta en el thread principal, aqui ponemos las componentes donde se visualzia la informacion
            System.out.println("Ha escribir con ganas");
        }

        public String hacerPost(Cliente cliente)throws Exception{
          //Creamos un cliente de prueba
            Cliente c=new Cliente();
            Direccion direccion=new Direccion();
            direccion.setEstado("Indiana");
            direccion.setMunicipio("Hobart");
            c.setNombre("Campitos ley");
            c.setEdad(42);
            c.setSueldo(70000);
            c.setDireccion(direccion);
            //Ajustamos el content type de acuerdo al metodo de servidor
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.setContentType(new MediaType("application","json"));
            //Creamos la entidad a enviar
            HttpEntity<Cliente> clienteHttpEntity=new HttpEntity<Cliente>(c,httpHeaders);

            //Creamos una instancia de RestTemplate
            RestTemplate restTemplate=new RestTemplate();

            //Agregamos los convertidores de jackson
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

           //Hacemos el post y obtenemos nuestra respuesta
            ResponseEntity<String> responseEntity=restTemplate.exchange(urlBase+"/cliente", HttpMethod.POST,clienteHttpEntity,String.class);
            String respuesta=responseEntity.getBody();
            return respuesta;
        }
    }

    class TareaAsincronicaGetTodos extends AsyncTask<String, Integer, Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
          //No se ejecuta en el thread principal

            return 0;
        }
        @Override
        public void onPostExecute(Integer i){
            try{
                //Aqui interaccionamos con las componentes visuales, ya que se ejecuta en el thread principal
        List<Cliente> clientes=  hacerGetTodos();


                  System.out.println("Se ha activado el GET TODOS CORRECTAMENTE:");
            }catch(Exception e){
             System.out.println("algo anduvo mal <<<<<"+e.getMessage());
            }
        }

        public List<Cliente> hacerGetTodos() throws Exception{
            String leido="nada se leyo  :(";

            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));

            HttpEntity<?> requestEntity=new HttpEntity<Object>(requestHeaders);

ObjectMapper mapper=new ObjectMapper();

            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            ResponseEntity<String> responseEntity=restTemplate.exchange(urlBase+"/cliente", HttpMethod.GET,requestEntity, String.class);
            leido=responseEntity.getBody();


            ArrayList<Cliente> clientes=mapper.readValue(leido, new TypeReference<ArrayList<Cliente>>(){});

   System.out.println("mallloo"+clientes.get(0).getFecha().getHourOfDay());


            return clientes;
        }
    }
    class TareaAsyncronicaGetPorId extends AsyncTask<String, Integer, Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            return 0;
        }

        @Override
        public void onPostExecute(Integer i){
            try{
                //Aqui interaccionamos con las componentes visuales, ya que se ejecuta en el thread principal
                Cliente clientes=  hacerGetPorId("555ba2cec830d61e87bd4856");


                System.out.println("Se ha activado el GET por id correctamente:");
            }catch(Exception e){
                System.out.println("algo anduvo mal <<<<<"+e.getMessage());
            }
        }
        public Cliente hacerGetPorId(String id) throws Exception{
            String leido="nada se leyo  :(";

            HttpHeaders requestHeaders=new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));

            HttpEntity<?> requestEntity=new HttpEntity<Object>(requestHeaders);

            ObjectMapper mapper=new ObjectMapper();

            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            ResponseEntity<String> responseEntity=restTemplate.exchange(urlBase+"/cliente/"+id, HttpMethod.GET,requestEntity, String.class);
            leido=responseEntity.getBody();


            Cliente cliente=mapper.readValue(leido, new TypeReference<Cliente>(){});

            System.out.println("SE activo el get por id:"+cliente.getFecha().getHourOfDay());


            return cliente;
        }

    }
}

