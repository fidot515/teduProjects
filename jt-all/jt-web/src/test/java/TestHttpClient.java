import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	
	@Test
	public void test01() throws ClientProtocolException, IOException{
		//1.获取HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//2.定义网址
		String url  = "https://item.jd.com/188550.html";
		//3.定义请求方式 GET/POST
		HttpGet httpGet = new HttpGet(url);
		HttpPost httpPost = new HttpPost(url);
		//4.发送请求
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		//5.判断返回值数据是否正确
		if(httpResponse.getStatusLine().getStatusCode() == 200){
			System.out.println("表示请求有效的");
			//获取返回值实体内容
			String result = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(result);
		}
	}
}
