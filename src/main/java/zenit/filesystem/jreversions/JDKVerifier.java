package zenit.filesystem.jreversions;

import java.io.File;

import zenit.Zenit;

public class JDKVerifier {
	
	public static boolean validJDK(File JDK) {
		
		File java = new File(getExecutablePath(JDK.getPath(), "java.exe"));
		File javac = new File(getExecutablePath(JDK.getPath(), "javac.exe"));
		
		return (java != null && javac != null && java.exists() && javac.exists());
	}
	
	public static String getExecutablePath(String JDKPath, String tool) {
		String OS = Zenit.OS;
		String path = null;

		if (OS.equals("Mac OS X")) {
			path = JDKPath + File.separator + "Contents" + File.separator + "Home" + File.separator + "bin" + File.separator + tool;
		} else if (OS.contains("Windows")) {
			path = JDKPath + File.separator + "bin" + File.separator + tool;
		} else if (OS.equals("Linux")) {
			path = JDKPath + File.separator + "bin" + File.separator + tool;
		}
		return path;
	}
}
