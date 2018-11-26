/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Back_End;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import static javafx.scene.input.KeyCode.K;
import org.json.JSONObject;

public class Back_End2 {
    static String veiculo;
    static double velocidade;
    static String cidade;
    static String UF;
    static String aux = "";
    
    public Back_End2(String Veiculo, double velocidade, String cidade, String UF){
        try {
            setDados(Veiculo, velocidade, cidade, UF);
		Back_End2.call_me();
	} catch (Exception e) {
		e.printStackTrace();
	}
    }
    
    public static void setDados(String Veiculo, double velocidade, String cidade, String UF){
        Back_End2.veiculo = Veiculo;
        Back_End2.velocidade = velocidade;
        Back_End2.cidade = cidade;
        Back_End2.UF = UF;
    }
	 public static void call_me() throws Exception {
	    URL url = new URL("http://httpbin.org/post");
	    Map params = new LinkedHashMap<>();
	    params.put("veiculo", veiculo);
	    params.put("velocidade", velocidade+" km/h");
	    params.put("cidade", cidade + " - "+UF);
	    StringBuilder postData = new StringBuilder();
        for (Iterator it = params.entrySet().iterator(); it.hasNext();) {
            Map.Entry param = (Map.Entry) it.next();
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey().toString(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	    conn.setDoOutput(true);
	    conn.getOutputStream().write(postDataBytes);
	    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    StringBuilder sb = new StringBuilder();
	    for (int c; (c = in.read()) >= 0;)
	        sb.append((char)c);
	    String response = sb.toString();
	    System.out.println(response);
	    JSONObject myResponse = new JSONObject(response.toString());
//	    System.out.println("result after Reading JSON Response");
	    System.out.println("origin- "+myResponse.getString("origin"));
	    System.out.println("url- "+myResponse.getString("url"));
	    JSONObject form_data = myResponse.getJSONObject("form");
            
	    aux += "\nVeículo- "+form_data.getString("veiculo");
	    aux += "\nLocalização - "+form_data.getString("cidade");
	    aux += "\nVelocidade - "+form_data.getString("velocidade");
            aux += "\n";
	}
         public String getResp(){
             return aux;
         }
}