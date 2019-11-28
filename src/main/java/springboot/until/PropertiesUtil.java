package springboot.until;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
	//创建动态资源加载器
	private static final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	//获取属性对象
	public static final  Properties prop = new Properties();
	//加载properties的路径
	private static final  String loadPropertiesPath = "classpath:*.properties";
	//加载yml的路径
	private static final  String loadYmlPath = "classpath:*.yml";
	static {
		loadProperties();
		loadYml();
	}

	/**
	 * 加载properties格式的文件
	 * @return
	 */
	public static Properties loadProperties(){
		InputStream in = null;
		try {
			Resource[] resources = 	resolver.getResources(loadPropertiesPath);
			synchronized (prop){
				for(Resource resource:resources){
					in = resource.getInputStream();
					//获取编码格式
					String code = codeString(in);
					prop.load(new InputStreamReader(in, code));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != in) {
				IOUtils.closeQuietly(in);
			}
		}
		return prop;
	}
	/**
	 * 加载yml格式的文件
	 * @return
	 */
	public static void loadYml() {
		InputStream in = null;
		YAMLParser parser = null;
		try {
			final String DOT = ".";
			YAMLFactory yamlFactory = new YAMLFactory();
			Resource[] resources = 	resolver.getResources(loadYmlPath);
			synchronized (prop){
				for(Resource resource:resources){
					parser = yamlFactory.createParser(resource.getInputStream());
					String key = "";
					String value = null;
					JsonToken token = parser.nextToken();
					while (token != null) {
						if (JsonToken.START_OBJECT.equals(token)) {
							// do nothing
						} else if (JsonToken.FIELD_NAME.equals(token)) {
							if (key.length() > 0) {
								key = key + DOT;
							}
							key = key + parser.getCurrentName();
							token = parser.nextToken();
							if (JsonToken.START_OBJECT.equals(token)) {
								continue;
							}
							value = parser.getText();
							String s = key + "=" + value;
							in = new ByteArrayInputStream(s.getBytes());
							//获取编码格式
							String code = codeString(in);
							prop.load(new InputStreamReader(in, code));
							int dotOffset = key.lastIndexOf(DOT);
							if (dotOffset > 0) {
								key = key.substring(0, dotOffset);
							}
						} else if (JsonToken.END_OBJECT.equals(token)) {
							int dotOffset = key.lastIndexOf(DOT);
							if (dotOffset > 0) {
								key = key.substring(0, dotOffset);
							}
						}
						token = parser.nextToken();
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(null != in) {
				IOUtils.closeQuietly(in);
			}
			IOUtils.closeQuietly(parser);
		}
	}
	/**
	 * 判断文件的编码格式
	 * @param  in 输入流
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public static String codeString(InputStream in) throws Exception{
		int p = (in.read() << 8) + in.read();
		String code = null;
		switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
		}
		return code;
	}
}
