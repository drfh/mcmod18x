package ruby.bamboo.core.init;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
	private ClassLoader classLoader;

	public ClassFinder() {
		classLoader = Thread.currentThread().getContextClassLoader();
	}

	public List<Class<?>> search(String arg) throws Exception {
		return this.findClasses(arg);
	}

	private ClassFinder(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	private String fileNameToClassName(String name) {
		return name.substring(0, name.length() - ".class".length());
	}

	private String resourceNameToClassName(String resourceName) {
		return fileNameToClassName(resourceName).replace('/', '.');
	}

	private boolean isClassFile(String fileName) {
		return fileName.endsWith(".class");
	}

	private String packageNameToResourceName(String packageName) {
		return packageName.replace('.', '/');
	}

	private List<Class<?>> findClasses(String rootPackageName) throws Exception {
		String resourceName = packageNameToResourceName(rootPackageName);
		URL url = classLoader.getResource(resourceName);

		if (url == null) {
			return new ArrayList<Class<?>>();
		}

		String protocol = url.getProtocol();
		if ("file".equals(protocol)) {
			return findClassesWithFile(rootPackageName, new File(url.getFile()));
		} else if ("jar".equals(protocol)) {
			return findClassesWithJarFile(rootPackageName, url);
		}

		throw new IllegalArgumentException("Unsupported Class Load Protodol[" + protocol + "]");
	}

	private List<Class<?>> findClassesWithFile(String packageName, File dir) throws Exception {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		for (String path : dir.list()) {
			File entry = new File(dir, path);
			if (entry.isFile() && isClassFile(entry.getName())) {
				classes.add(classLoader.loadClass(packageName + "." + fileNameToClassName(entry.getName())));
			} else if (entry.isDirectory()) {
				classes.addAll(findClassesWithFile(packageName + "." + entry.getName(), entry));
			}
		}

		return classes;
	}

	private List<Class<?>> findClassesWithJarFile(String rootPackageName, URL jarFileUrl) throws Exception {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		JarURLConnection jarUrlConnection = (JarURLConnection) jarFileUrl.openConnection();
		JarFile jarFile = null;

		try {
			jarFile = jarUrlConnection.getJarFile();
			Enumeration<JarEntry> jarEnum = jarFile.entries();

			String packageNameAsResourceName = packageNameToResourceName(rootPackageName);

			while (jarEnum.hasMoreElements()) {
				JarEntry jarEntry = jarEnum.nextElement();
				if (jarEntry.getName().startsWith(packageNameAsResourceName) && isClassFile(jarEntry.getName())) {
					classes.add(classLoader.loadClass(resourceNameToClassName(jarEntry.getName())));
				}
			}
		} finally {
			if (jarFile != null) {
				jarFile.close();
			}
		}

		return classes;
	}
}
