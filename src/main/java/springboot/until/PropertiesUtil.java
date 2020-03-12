package springboot.until;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
@Component
public class PropertiesUtil implements EmbeddedValueResolverAware {
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
		System.out.println(prop);
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
					prop.load(new InputStreamReader(in, "utf-8"));
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
							prop.load(new InputStreamReader(in, "utf-8"));
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
				org.apache.tomcat.util.http.fileupload.IOUtils.closeQuietly(in);
			}
			org.apache.tomcat.util.http.fileupload.IOUtils.closeQuietly(parser);
		}
	}
	public static Properties loadHadoop(){
		Properties prop = new Properties();
		InputStream in = null;
		try {
			Resource resource = new ClassPathResource("hadoop.properties");
			in = resource.getInputStream();
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	/**
	 * 获取资源文件内容
	 *
	 * @param fileName 文件名字（resource下的文件名字）
	 * @return
	 */
	public static Properties loadProperties(String fileName){
		Properties prop = new Properties();
		InputStream in = null;
		try {
			Resource resource = new ClassPathResource(fileName);
			in = resource.getInputStream();
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
	private StringValueResolver stringValueResolver;
	@Override
	public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
		this.stringValueResolver = stringValueResolver;
	}
	public String getString (String key){
		StringBuilder name = new StringBuilder("${").append(key).append("}");
		return stringValueResolver.resolveStringValue(name.toString());
	}
}
