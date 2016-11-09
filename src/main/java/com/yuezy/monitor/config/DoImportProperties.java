package com.yuezy.monitor.config;


import com.yuezy.monitor.config.anno.ImportProperties;
import com.yuezy.monitor.util.ClassWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Properties;

public class DoImportProperties {

	private static final Logger logger = LoggerFactory.getLogger(DoImportProperties.class);

	public static void doImport(Object obj, Class<?> clazz) {
		ImportProperties importProperties = clazz.getAnnotation(ImportProperties.class);
		String propertiesFile = importProperties.properties();
		String prefix = importProperties.prefix();
		if(prefix != "" && prefix != null && !prefix.isEmpty()){
			prefix = prefix + ".";
		}
		try {
			File base = new File(DoImportProperties.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getParentFile();
			File configFile= new File(base, propertiesFile);
			InputStream ins = new FileInputStream(configFile);
			Properties props = new Properties();
			//props.load(DoImportProperties.class.getClass().getResourceAsStream(propertiesFile));
			props.load(ins);
			for (Method method : clazz.getMethods()) {
				String methodName = method.getName();
				if (methodName.startsWith("set") && methodName.length() > 3) {
					Class<?>[] params = method.getParameterTypes();
					if(params == null || params.length != 1) continue;
					String propsValue = (String)props.get(prefix + StringUtils.uncapitalize(methodName.substring(3)));
					if(propsValue == null || StringUtils.isEmpty(propsValue)) continue;
					Class<?> paramType = params[0];
					Class<?> wrappedParamType = ClassWrapper.wrap(paramType);
					Constructor<?> wrappedTypeCons = wrappedParamType.getConstructor(String.class);
					Object convertedArg = wrappedTypeCons.newInstance(propsValue);
					method.invoke(obj, convertedArg);
				}
			}
			ins.close();
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		} catch (IllegalAccessException e) {
			logger.error(e.toString());
		} catch (IllegalArgumentException e) {
			logger.error(e.toString());
		} catch (InvocationTargetException e) {
			logger.error(e.toString());
		} catch (NoSuchMethodException e) {
			logger.error(e.toString());
		} catch (SecurityException e) {
			logger.error(e.toString());
		} catch (InstantiationException e) {
			logger.error(e.toString());
		} catch (URISyntaxException e) {
			logger.error(e.toString());
		}
	}
}